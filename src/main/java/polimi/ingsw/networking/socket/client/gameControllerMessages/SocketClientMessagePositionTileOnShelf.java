package polimi.ingsw.networking.socket.client.gameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

/**
 * SocketClientMessagePositionTileOnShelf class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the positioning of a tile on the player's shelf.
 */
public class SocketClientMessagePositionTileOnShelf extends SocketClientGenericMessage {
    private int column;
    private TileType type;

    /**
     * Constructor of the class.
     * @param nick the player's nickname
     * @param column the column index on the shelf where the tile should be positioned
     * @param type the type of tile to be positioned on the shelf
     */
    public SocketClientMessagePositionTileOnShelf(String nick, int column, TileType type) {
        this.column = column;
        this.type = type;
        this.nick = nick;
        this.isMessageForMainController = false;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller of the application
     * @return the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.positionTileOnShelf(nick, column, type);
    }
}
