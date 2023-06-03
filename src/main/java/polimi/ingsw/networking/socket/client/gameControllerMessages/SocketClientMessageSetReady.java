package polimi.ingsw.networking.socket.client.gameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessageSetReady class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating that a player is ready to start the game.
 */
public class SocketClientMessageSetReady extends SocketClientGenericMessage {

    /**
     * Constructor of the class.
     * @param nick the player's nickname
     */
    public SocketClientMessageSetReady(String nick) {
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
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        gameController.playerIsReadyToStart(this.nick);
    }
}
