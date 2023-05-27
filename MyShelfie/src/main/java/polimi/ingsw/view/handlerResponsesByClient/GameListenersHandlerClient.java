package polimi.ingsw.view.handlerResponsesByClient;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.userView.Flow;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenersHandlerClient implements GameListener, Serializable {


    private Flow gui;

    public GameListenersHandlerClient(Flow gui) {
        this.gui = gui;
    }

    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        // System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined! [by Socket]");
        gui.playerJoined(gamemodel);
    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        gui.playerLeft(gamemodel,nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> " + wantedToJoin+" tried to entry but the game is full! [by Socket]");
        gui.joinUnableGameFull(wantedToJoin, gamemodel);
    }

    @Override
    public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {
        gui.playerReconnected(gamemodel, nickPlayerReconnected);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        // System.out.println(this.hashCode() + "> " + wantedToJoin.getNickname() + " has already in [by Socket]");
        System.out.println("Error");
        gui.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        gui.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        gui.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {
        //System.out.println(this.hashCode() + "> " + nick + " ready to start! [by Socket]");
        gui.playerIsReadyToStart(gamemodel, nick);
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> " + card.getCommonType() + " card common extracted! [by Socket]");
        gui.commonCardsExtracted(gamemodel);
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
        gui.gameStarted(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        /*System.out.println(this.hashCode() + "> "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: todo [by Socket]");*/
        gui.gameEnded(gamemodel);
    }

    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {
        //System.out.println(this.hashCode() + "> new Message: \""+msg.toString()+"\" [by Socket]");
        gui.sentMessage(gameModel, msg);
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles: "+gamemodel.getHandOfCurrentPlaying().toString()+" [by Socket]");
        gui.grabbedTile(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {
        // System.out.println(this.hashCode() + "> a set of non grabbable tiles have been required [by Socket]");
        gui.grabbedTileNotCorrect(gamemodel);
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf! [by Socket]");
        gui.positionedTile(gamemodel, type, column);
        //setModel(gamemodel);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
        gui.nextTurn(gamemodel);
    }

    @Override
    public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo()+" [by Socket]");
        gui.addedPoint(p, point,gamemodel);
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        // System.out.println(this.hashCode() + "> Player "+nick+" just disconnected [by Socket]");
        gui.playerDisconnected(gameModel,nick);
    }

    @Override
    public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {
        gui.columnShelfTooSmall(gameModel,column);
    }

    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        gui.onlyOnePlayerConnected(gameModel,secondsToWaitUntilGameEnded);
    }

    @Override
    public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {
        gui.lastCircle(gamemodel);
    }


}
