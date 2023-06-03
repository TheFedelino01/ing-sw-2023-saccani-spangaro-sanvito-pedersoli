package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgPositionedTile class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a tile has been positioned on a shelf.
 */
public class msgPositionedTile extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;
    private TileType type;
    private int column;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     * @param type the type of the positioned tile
     * @param column the column where the tile is positioned
     */
    public msgPositionedTile(GameModelImmutable gamemodel, TileType type, int column) {
        this.gamemodel = gamemodel;
        this.type = type;
        this.column = column;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.positionedTile(gamemodel, type, column);
    }
}
