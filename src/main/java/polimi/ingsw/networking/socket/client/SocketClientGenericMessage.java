package polimi.ingsw.networking.socket.client;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * SocketClientGenericMessage class.
 * An abstract class that represents a generic message to be sent from the client to the server.
 */
public abstract class SocketClientGenericMessage implements Serializable {

    protected String nick;
    protected boolean isMessageForMainController;
    protected boolean isHeartbeat=false;

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller interface
     * @return the game controller interface
     * @throws RemoteException if there is a remote exception
     */
    public abstract GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException;

    /**
     * Executes the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is a remote exception
     * @throws GameEndedException if the game has ended
     */
    public abstract void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException;

    /**
     * Checks if the message is intended for the main controller.
     * @return true if the message is intended for the main controller, false otherwise
     */
    public boolean isMessageForMainController() {
        return isMessageForMainController;
    }

    /**
     * Sets whether the message is intended for the main controller.
     * @param messageForMainController true if the message is intended for the main controller, false otherwise
     */
    public void setMessageForMainController(boolean messageForMainController) {
        isMessageForMainController = messageForMainController;
    }

    /**
     * Returns the nickname associated with the message.
     * @return the nickname
     */
    public String getNick() {
        return nick;
    }

    /**
     * @return if it's a heartbeat message
     */
    public boolean isHeartbeat(){
        return isHeartbeat;
    }

}
