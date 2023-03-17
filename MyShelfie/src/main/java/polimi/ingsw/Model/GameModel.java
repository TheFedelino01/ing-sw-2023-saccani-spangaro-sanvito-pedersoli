package polimi.ingsw.Model;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.*;

import java.util.*;

public class GameModel {
    private List<Player> players;
    private List<CardCommon> commonCards;
    private Integer gameId;
    private Playground pg;

    private Integer currentPlaying;

    private Chat chat;

    private GameStatus status;

    private Random random = new Random();


    private List<GameListener> listeners;

    public GameModel() {
        players = new ArrayList<Player>();
        commonCards = new ArrayList<CardCommon>();

        gameId = random.nextInt(10000000);

        pg = new Playground();
        currentPlaying = -1;
        chat = new Chat();
        status = GameStatus.WAIT;

        chat = new Chat();

        listeners=new ArrayList<GameListener>();
    }

    public GameModel(List<Player> players, List<CardCommon> commonCards, Integer gameId, Playground pg) {
        this.players = players;
        this.commonCards = commonCards;
        this.gameId = gameId;
        this.pg = pg;
    }

    public int getNumOfPlayers() {
        return players.size();
    }

    public void addPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        //Verifico per prima cosa che il player non e' gia' presente
        //poi se non vado in overflow
        if (players.stream()
                .filter(x -> x.equals(p))
                .count() == 0) {
            if (players.size() + 1 <= DefaultValue.MaxNumOfPlayer) {
                players.add(p);
            } else {
                notify_JoinUnableGameFull();
                throw new MaxPlayersInException();
            }
        } else {
            notify_JoinUnableNicknameAlreadyIn(p.getNickname());
            throw new PlayerAlreadyInException();
        }

    }


    public Player getPlayer(int i) {
        return players.get(i);
    }


    public void playerIsReadyToStart(Player p){
        p.setReadyToStart();
        notify_PlayerIsReadyToStart(p.getNickname());
    }

    public boolean arePlayersReadyToStartAndEnough(){
        //Se tutti i giocatori sono pronti a giocare, inizia il game
        if(players.stream().filter(x-> x.getReadyToStart()==true).count()==players.size() && players.size()>= DefaultValue.minNumOfPlayer){
            return true;
        }
        return false;
    }



    public int getNumOfCommonCards() {
        return commonCards.size();
    }


    public void addCommonCard(CardCommon c) throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //Si verifica per prima cosa se la carta e' gia' presente
        //se non e' gia' presente, verifico se si va in overflow

        if (commonCards.stream().filter(x -> x.isSameType(c)).count() == 0) {
            if (commonCards.size() + 1 <= DefaultValue.NumOfCommonCards) {
                commonCards.add(c);
            } else {
                throw new MaxCommonCardsAddedException();
            }
        } else {
            throw new CommonCardAlreadyInException();
        }

    }

    public void setGoalCard(int indexPlayer, CardGoal c) throws SecretGoalAlreadyGivenException {
        if(indexPlayer<players.size() && indexPlayer>=0) {
            //Assegno la carta goal solo se non ce l'ha nessun altro player
            if (players.stream().filter(x -> (x.getSecretGoal().isSameType(c))).count() == 0) {
                players.get(indexPlayer).setSecretGoal(c);
            } else {
                throw new SecretGoalAlreadyGivenException();
            }
        }else{
            throw new IndexPlayerOutOfBoundException();
        }

    }

    public CardCommon getCommonCard(int i){
        return commonCards.get(i);
    }

    public CardGoal getGoalCard(int indexPlayer){
        return players.get(indexPlayer).getSecretGoal();
    }


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Playground getPg() {
        return pg;
    }

    public void setPg(Playground pg) {
        this.pg = pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public void setCurrentPlaying(Integer currentPlaying) {
        this.currentPlaying = currentPlaying;
    }

    public Chat getChat() {
        return chat;
    }

    public void sendMessage(Player p, String txt){
        if(players.stream().filter(x-> x.equals(p)).count()==1){
            chat.addMsg(p,txt);
            notify_SentMessage(chat.getLastMessage());
        }else{
            throw new ActionPerformedByAPlayerNotPlayingException();
        }

    }



    public GameStatus getStatus() {
        return status;
    }

    public Map<Player,CardGoal> getGoalCards(){
        Map ris = new HashMap<Player,CardGoal>();

        for(Player p: players){
            ris.put(p,p.getSecretGoal());
        }
        return ris;
    }
    public boolean doAllPlayersHaveGoalCard(){
        for(Player p: players){
            if(p.getSecretGoal().getGoalType() == CardGoalType.NOT_SET)
                return false;
        }
        return true;
    }



    public void setStatus(GameStatus status)  {
        //Se voglio settare a Running il game, ci devono essere almeno 'DefaultValue.minNumOfPlayer' players
        if(status==GameStatus.RUNNING &&
                (players.size()<DefaultValue.minNumOfPlayer
                || getNumOfCommonCards()!=DefaultValue.NumOfCommonCards
                        || !doAllPlayersHaveGoalCard())
                        || currentPlaying==-1){
            throw new NotReadyToRunException();
        }else {
            this.status = status;

            if(status==GameStatus.RUNNING)
                notify_GameStarted();
            else if(status==GameStatus.STOPPED)
                notify_GameEnded();
        }
    }

    public List<Tile> grabTailFromPlayground(Player p, int x, int y, Direction direction, int num){
        List<Tile> ris = pg.grabTailFromPlayground(x,y,direction,num);
        p.setInHandTail(ris);
        notify_grabbedTail();
        return ris;
    }
    public void positionTailOnShelf(Player p, int collum, TileType tipo){
        Tile t = popInHandTilePlayer(p,tipo);
        if(t!=null){
            p.getShelf().position(collum,tipo);
            notify_positionedTail();
        }else{
            throw new PositioningATailNotGrabbedException();
        }

    }
    private Tile popInHandTilePlayer(Player p, TileType tipo){
        for(int i=0; i< p.getInHandTail().size();i++){
            if(p.getInHandTail().get(i).isSameType(tipo)){
                return p.getInHandTail().remove(i);
            }
        }
        return null;//Il player non ha questa Tile tra quelle estratte
    }

    public void nextTurn(){
        if(status==GameStatus.RUNNING)
            currentPlaying = (currentPlaying+1)%players.size();
        else
            throw new GameNotStartedException();
    }

    public void addListener(GameListener obj){
        listeners.add(obj);
    }

    private void notify_JoinUnableGameFull(){
        for(GameListener l : listeners)
            l.JoinUnableGameFull(this);
    }
    private void notify_JoinUnableNicknameAlreadyIn(String nick){
        for(GameListener l : listeners)
            l.JoinUnableNicknameAlreadyIn(nick);
    }
    private void notify_PlayerIsReadyToStart(String nick){
        for(GameListener l : listeners)
            l.PlayerIsReadyToStart(nick);
    }

    private void notify_GameStarted(){
        for(GameListener l : listeners)
            l.GameStarted(this);
    }
    private void notify_GameEnded(){
        for(GameListener l : listeners)
            l.GameEnded(this);
    }
    private void notify_SentMessage(Message msg){
        for(GameListener l : listeners)
            l.SentMessage(msg);
    }
    private void notify_grabbedTail(){
        for(GameListener l : listeners)
            l.grabbedTail(this);
    }
    private void notify_positionedTail(){
        for(GameListener l : listeners)
            l.positionedTail(this);
    }



}
