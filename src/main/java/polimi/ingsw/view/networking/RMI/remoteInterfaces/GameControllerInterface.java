package polimi.ingsw.view.networking.RMI.remoteInterfaces;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains all the action a player can do in a single game */
public interface GameControllerInterface extends Remote {
    /**
     * This method is used to check if the player is ready to start
     * @param p the nickname of the player
     * @return true if the player is ready to start
     * @throws RemoteException if the connection fails
     */
    boolean playerIsReadyToStart(String p) throws RemoteException;

    /**
     * This method grabs a tile from the playground
     * @param p the nickname of the player
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     * @param direction the direction of the tile {@link Direction}
     * @param num the number of the tile
     * @throws RemoteException if the connection fails
     */
    void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) throws RemoteException;

    /**
     * This method position a grabbed tile on the shelf
     * @param p the nickname of the player
     * @param column the column of the shelf
     * @param type the type of the tile {@link TileType}
     * @throws RemoteException if the connection fails
     * @throws GameEndedException if the game is ended
     */
    void positionTileOnShelf(String p, int column, TileType type) throws RemoteException, GameEndedException;

    
    boolean isThisMyTurn(String nick) throws RemoteException;

    void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException;

    void heartbeat(String nick, GameListener me) throws RemoteException;

    void sentMessage(Message msg) throws RemoteException;

    int getGameId() throws RemoteException;

    int getNumOnlinePlayers() throws RemoteException;

    void leave(GameListener lis, String nick) throws RemoteException;
}
