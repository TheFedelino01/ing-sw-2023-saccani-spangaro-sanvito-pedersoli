package polimi.ingsw.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Goal.CardGoal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Serializable {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTile;
    private List<Point> obtainedPoints;
    private boolean readyToStart=false;
    private boolean connected=true;

    private transient List<GameListener> listeners;


    public Player(String nickname){
        this.nickname=nickname;
        shelf=new Shelf();
        secretGoal= new CardGoal();
        inHandTile = new ArrayList<>();
        obtainedPoints=new ArrayList<>();
        listeners= new ArrayList<>();
    }
    public Player(String nickname, Shelf shelf, CardGoal secretGoal, List<Tile> inHandTile, List<Point> obtainedPoints) {
        this.nickname = nickname;
        this.shelf = shelf;
        this.secretGoal = secretGoal;
        this.inHandTile = inHandTile;
        this.obtainedPoints = obtainedPoints;
        listeners= new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelfS(Shelf shelf){this.shelf=shelf; }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public CardGoal getSecretGoal() {
        return secretGoal;
    }

    public void setSecretGoal(CardGoal secretGoal) {
        this.secretGoal = secretGoal;
    }

    public List<Tile> getInHandTile() {
        return inHandTile;
    }

    public void setInHandTile(List<Tile> inHandTile) {
        if (inHandTile.size() > 3) {
            throw new IllegalArgumentException("You can't have more than 3 tiles in hand");
        }
        else {
            this.inHandTile = inHandTile;
        }
    }

    public int getLastGameId(){
        //game data related to the player is stored in a json file named after the nickname the player had in that game
        String gameId = null;
        String time = null;
        JSONParser parser = new JSONParser();
        try (InputStream is = Playground.class.getClassLoader().getResourceAsStream("polimi/ingsw/Json/"+ nickname + ".json");
            Reader reader = new InputStreamReader(Objects.requireNonNull(is, "Couldn't find json file"), StandardCharsets.UTF_8)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            gameId = (String) obj.get(DefaultValue.gameIdData);
            time = (String) obj.get(DefaultValue.gameIdTime);
        } catch (ParseException | FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert gameId != null;
        if(LocalDateTime.parse(time).isBefore(LocalDateTime.now().plusSeconds(DefaultValue.twelveHS)))
            return Integer.parseInt(gameId);
        else
            return -1;
    }

    @SuppressWarnings("unchecked")
    public void setLastGameId(int gameId){
        JSONObject data = new JSONObject();
        data.put(DefaultValue.gameIdData, gameId);
        data.put(DefaultValue.gameIdTime, LocalDateTime.now());
        File file = new File("polimi/ingsw/Json/"+ nickname + ".json");
        try{
            //if the file does not exist, create it
            file.createNewFile();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        try (FileWriter fileWriter = new FileWriter("polimi/ingsw/Json/"+ nickname + ".json")){
            fileWriter.write(data.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    public void addPoint(Point obtainedPoints) {
        for(Point p: this.obtainedPoints){
            if(p.getReferredTo().equals(obtainedPoints.getReferredTo())){
                throw new IllegalArgumentException("You can't have more than one point for the same card");
            }
        }
        //Nessun eccezione sollevata, aggiungo il punto al giocatore e notifico
        this.obtainedPoints.add(obtainedPoints);
        notify_addedPoint(obtainedPoints);
    }

    public int getTotalPoints(){
        return obtainedPoints.stream().map(Point::getPoint).reduce(0, Integer::sum);
    }

    public boolean getReadyToStart(){return readyToStart;}
    public void setReadyToStart(){
        readyToStart=true;
    }
    public boolean equals(Player p){
        return this.nickname.equals(p.nickname);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void addListener(GameListener obj){
        listeners.add(obj);
    }
    private void notify_addedPoint(Point point){
        for(GameListener l : listeners) {
            try {
                l.addedPoint(this,point);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeListener(GameListener lis) {
        listeners.remove(lis);
    }
}
