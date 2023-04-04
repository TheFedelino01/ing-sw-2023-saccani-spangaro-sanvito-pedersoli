package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.io.Serializable;
import java.rmi.RemoteException;

//This class handles all the responses that the server RMI sends
public class GameListenersHandlerRMI implements GameListener, Serializable {

    private GameModel lastModelReceived=null;

    @Override
    public void playerJoined(String nickNewPlayer) {
        System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined!");
    }

    @Override
    public void JoinUnableGameFull(Player wantedToJoin, GameModel gamemodel) throws RemoteException {
        System.out.println(this.hashCode() + "> " + wantedToJoin+" tried to entry but the game is full!");
    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        System.out.println(this.hashCode() + "> " + wantedToJoin.getNickname() + " has already in");
    }

    @Override
    public void PlayerIsReadyToStart(String nick) {
        System.out.println(this.hashCode() + "> " + nick + " ready to start!");
    }

    @Override
    public void commonCardsExtracted(CommonCard card) throws RemoteException {
        System.out.println(this.hashCode() + "> " + card.getCommonType() + " card common extracted!");
    }

    @Override
    public void GameStarted(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying());
        setModel(gamemodel);
    }

    @Override
    public void GameEnded(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: "+gamemodel.getLeaderBoard().toString());
    }

    @Override
    public void SentMessage(Message msg) {
        System.out.println(this.hashCode() + "> new Message: \""+msg.toString()+"\"");
    }

    @Override
    public void grabbedTile(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles: "+gamemodel.getHandOfCurrentPlaying().toString());
        setModel(gamemodel);
    }

    @Override
    public void grabbedTileNotCorrect(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> a set of non grabbable tiles have been required");
    }

    @Override
    public void positionedTile(GameModel gamemodel, TileType type, int column) {
        System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
        setModel(gamemodel);
    }

    @Override
    public void nextTurn(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying());
    }

    @Override
    public void addedPoint(Player p, Point point) {
        System.out.println(this.hashCode() + "> Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo());
    }

    public GameModel getLastModelReceived(){
        return lastModelReceived;
    }

    public synchronized void setModel(GameModel m){
        lastModelReceived = m;
    }
}
