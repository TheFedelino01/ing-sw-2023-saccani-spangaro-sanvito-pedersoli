package polimi.ingsw.networking.socket.client.gameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

/**
 * SocketClientMessageNewChatMessage class.
 * Extends SocketClientGenericMessage and is used to send a new chat message from the client to the server.
 */
public class SocketClientMessageNewChatMessage extends SocketClientGenericMessage {
    private Message msg;

    /**
     * Constructor of the class.
     * @param msg the chat message to be sent
     */
    public SocketClientMessageNewChatMessage(Message msg) {
        this.msg = msg;
        this.nick = msg.getSender().getNickname();
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
        gameController.sentMessage(msg);
    }
}