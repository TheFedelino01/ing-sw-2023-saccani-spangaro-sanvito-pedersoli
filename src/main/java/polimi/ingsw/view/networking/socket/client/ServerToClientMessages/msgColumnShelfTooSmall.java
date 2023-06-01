package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgColumnShelfTooSmall class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that the column shelf is too small in the game.
 */
public class msgColumnShelfTooSmall extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private int column;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     * @param column the column index of the shelf that is too small
     */
    public msgColumnShelfTooSmall(GameModelImmutable gamemodel, int column) {
        this.model = gamemodel;
        this.column=column;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.columnShelfTooSmall(model,column);
    }
}
