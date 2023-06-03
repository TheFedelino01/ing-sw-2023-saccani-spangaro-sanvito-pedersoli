package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgLastCircle class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that it is the last circle of the game.
 */
public class msgLastCircle extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgLastCircle(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.lastCircle(gamemodel);
    }
}
