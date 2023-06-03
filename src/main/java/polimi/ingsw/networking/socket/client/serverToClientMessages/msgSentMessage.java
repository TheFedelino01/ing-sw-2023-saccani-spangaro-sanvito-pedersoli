package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgSentMessage class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a chat message has been sent.
 */
public class msgSentMessage extends SocketServerGenericMessage {
    private Message msg;
    private GameModelImmutable gameModel;

    /**
     * Constructor of the class.
     * @param gameModel the immutable game model
     * @param msg the sent chat message
     */
    public msgSentMessage(GameModelImmutable gameModel, Message msg) {
        this.gameModel = gameModel;
        this.msg = msg;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.sentMessage(gameModel, msg);
    }
}
