package polimi.ingsw.listener;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {
    void playerJoined(GameModelImmutable gamemodel) throws RemoteException;
    void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException;

    void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException;

    void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException;

    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    void gameIdNotExists(int gameid) throws RemoteException;

    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException;

    void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException;

    void gameStarted(GameModelImmutable gamemodel) throws RemoteException;

    void gameEnded(GameModelImmutable gamemodel) throws RemoteException;

    void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException;

    void grabbedTile(GameModelImmutable gamemodel) throws RemoteException;

    void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException;

    void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException;

    void nextTurn(GameModelImmutable gamemodel) throws RemoteException;

    void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException;

    void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException;

}
