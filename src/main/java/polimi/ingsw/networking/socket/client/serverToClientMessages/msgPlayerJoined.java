package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.io.IOException;

/**
 * msgPlayerJoined class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has joined the game.
 */
public class msgPlayerJoined extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgPlayerJoined(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an I/O error
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerJoined(gamemodel);
    }
}
