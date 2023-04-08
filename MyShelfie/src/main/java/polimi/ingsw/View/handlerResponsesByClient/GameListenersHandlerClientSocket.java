package polimi.ingsw.View.handlerResponsesByClient;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenersHandlerClientSocket implements GameListener, Serializable {

    private GameModelImmutable lastModelReceived=null;

    @Override
    public void playerJoined(String nickNewPlayer) {
        System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined! [by Socket]");
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        System.out.println(this.hashCode() + "> " + wantedToJoin+" tried to entry but the game is full! [by Socket]");
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        System.out.println(this.hashCode() + "> " + wantedToJoin.getNickname() + " has already in [by Socket]");
    }

    @Override
    public void playerIsReadyToStart(String nick) {
        System.out.println(this.hashCode() + "> " + nick + " ready to start! [by Socket]");
    }

    @Override
    public void commonCardsExtracted(CommonCard card) throws RemoteException {
        System.out.println(this.hashCode() + "> " + card.getCommonType() + " card common extracted! [by Socket]");
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) {
        System.out.println(this.hashCode() + "> Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
        setModel(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) {
        System.out.println(this.hashCode() + "> "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: todo [by Socket]");
    }

    @Override
    public void sentMessage(Message msg) {
        System.out.println(this.hashCode() + "> new Message: \""+msg.toString()+"\" [by Socket]");
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) {
        System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles: "+gamemodel.getHandOfCurrentPlaying().toString()+" [by Socket]");
        setModel(gamemodel);
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) {
        System.out.println(this.hashCode() + "> a set of non grabbable tiles have been required [by Socket]");
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) {
        System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf! [by Socket]");
        setModel(gamemodel);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) {
        System.out.println(this.hashCode() + "> Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
    }

    @Override
    public void addedPoint(Player p, Point point) {
        System.out.println(this.hashCode() + "> Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo()+" [by Socket]");
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        System.out.println(this.hashCode() + "> Player "+nick+" just disconnected [by Socket]");
    }

    public GameModelImmutable getLastModelReceived(){
        return lastModelReceived;
    }

    public synchronized void setModel(GameModelImmutable m){
        lastModelReceived = m;
    }
}
