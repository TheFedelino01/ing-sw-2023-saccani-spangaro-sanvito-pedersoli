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

    /**
     * This method is used to notify the client that the game has started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void gameStarted(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that the game has ended
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void gameEnded(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that a message has been sent {@link Message}
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param msg is the message that has been sent
     * @throws RemoteException if the reference could not be accessed
     */
    void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException;

    /**
     * This method is used to notify that a Tile has been grabbed
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void grabbedTile(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that a Tile has been grabbed but it is not correct
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that a Tile has been positioned
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param type is the type of the tile
     * @param column is the column where the tile has been positioned
     * @throws RemoteException if the reference could not be accessed
     */
    void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException;

    /**
     * This method is used to notify that the next turn triggered
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void nextTurn(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that points have been added
     * @param p is the player that has added the points
     * @param point is the number of points that have been added
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that a player has disconnected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException;

    void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException;

    void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException;

    void lastCircle(GameModelImmutable gamemodel) throws RemoteException;

}
