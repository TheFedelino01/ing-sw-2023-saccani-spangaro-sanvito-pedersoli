package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.io.IOException;

/**
 * msgPlayerIsReadyToStart class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player is ready to start the game.
 */
public class msgPlayerIsReadyToStart extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private String nick;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param nick the nickname of the player who is ready to start
     */
    public msgPlayerIsReadyToStart(GameModelImmutable model, String nick) {
        this.model = model;
        this.nick = nick;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an I/O error
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerIsReadyToStart(model, nick);
    }
}
