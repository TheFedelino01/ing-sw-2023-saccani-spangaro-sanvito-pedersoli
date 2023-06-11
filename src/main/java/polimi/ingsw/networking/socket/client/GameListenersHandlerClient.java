package polimi.ingsw.networking.socket.client;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.flow.Flow;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenersHandlerClient implements GameListener, Serializable {


    private Flow flow;

    public GameListenersHandlerClient(Flow gui) {
        this.flow = gui;
    }

    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        flow.playerJoined(gamemodel);
    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        flow.playerLeft(gamemodel,nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        flow.joinUnableGameFull(wantedToJoin, gamemodel);
    }

    @Override
    public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {
        flow.playerReconnected(gamemodel, nickPlayerReconnected);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        flow.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        flow.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        flow.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {
        flow.playerIsReadyToStart(gamemodel, nick);
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        flow.commonCardsExtracted(gamemodel);
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        flow.gameStarted(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        flow.gameEnded(gamemodel);
    }

    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {
        flow.sentMessage(gameModel, msg);
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {
        flow.grabbedTile(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {
        flow.grabbedTileNotCorrect(gamemodel);
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {
        flow.positionedTile(gamemodel, type, column);
        //setModel(gamemodel);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        flow.nextTurn(gamemodel);
    }

    @Override
    public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {
        flow.addedPoint(p, point,gamemodel);
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        flow.playerDisconnected(gameModel,nick);
    }

    @Override
    public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {
        flow.columnShelfTooSmall(gameModel,column);
    }

    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        flow.onlyOnePlayerConnected(gameModel,secondsToWaitUntilGameEnded);
    }

    @Override
    public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {
        flow.lastCircle(gamemodel);
    }


}
