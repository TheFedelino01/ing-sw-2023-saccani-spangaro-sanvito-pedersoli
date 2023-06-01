package polimi.ingsw.listener;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used to notify the client about the changes in the game
 */
public interface GameListener extends Remote {
    /**
     * This method is used to notify the client that a player has joined the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void playerJoined(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a player has left the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has left
     * @throws RemoteException if the reference could not be accessed
     */
    void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the game is full
     * @param p is the player that has tried to join the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a player has reconnected to the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nickPlayerReconnected is the nickname of the player that has reconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    /**
     * This method is used to notify the client that the game id does not exist
     * @param gameid is the id of the game
     * @throws RemoteException if the reference could not be accessed
     */
    void gameIdNotExists(int gameid) throws RemoteException;


    /**
     * This is a generic error that can happen when a player is entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the reference could not be accessed
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    /**
     * This method is used to notify that the player is ready to start the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that is ready to start
     * @throws IOException if the reference could not be accessed
     */
    void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException;

    /**
     * This method is used to notify the client that the common cards have been extracted
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException;

    
    void gameStarted(GameModelImmutable gamemodel) throws RemoteException;

    void gameEnded(GameModelImmutable gamemodel) throws RemoteException;

    void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException;

    void grabbedTile(GameModelImmutable gamemodel) throws RemoteException;

    void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException;

    void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException;

    void nextTurn(GameModelImmutable gamemodel) throws RemoteException;

    void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException;

    void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException;

    void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException;

    void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException;

    void lastCircle(GameModelImmutable gamemodel) throws RemoteException;

}
