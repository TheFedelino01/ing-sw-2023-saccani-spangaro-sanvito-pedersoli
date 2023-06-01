package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;

import java.io.IOException;
import java.io.Serializable;

/**
 * SocketServerGenericMessage class.
 * An abstract class that represents a generic message to be sent from the server to the client.
 */
public abstract class SocketServerGenericMessage implements Serializable {

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    public abstract void execute(GameListener lis) throws IOException, InterruptedException;

}
