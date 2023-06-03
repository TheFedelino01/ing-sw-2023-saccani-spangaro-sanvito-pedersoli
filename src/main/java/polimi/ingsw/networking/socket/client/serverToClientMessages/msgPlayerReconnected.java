package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.io.IOException;

/**
 * msgPlayerReconnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has reconnected to the game.
 */
public class msgPlayerReconnected extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;
    private String nickPlayerReconnected;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     * @param nickPlayerReconnected the nickname of the player who reconnected
     */
    public msgPlayerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) {
        this.gamemodel = gamemodel;
        this.nickPlayerReconnected = nickPlayerReconnected;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerReconnected(gamemodel, nickPlayerReconnected);
    }
}
