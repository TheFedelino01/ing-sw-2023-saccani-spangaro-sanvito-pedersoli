package polimi.ingsw.Model;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Goal.CardGoal;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTile;
    private List<Point> obtainedPoints;
    private boolean readyToStart=false;

    private List<GameListener> listeners;


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
        notify_addedPoint();
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

    public void addListener(GameListener obj){
        listeners.add(obj);
    }
    private void notify_addedPoint(){
        for(GameListener l : listeners)
            l.addedPoint(this);
    }
}
