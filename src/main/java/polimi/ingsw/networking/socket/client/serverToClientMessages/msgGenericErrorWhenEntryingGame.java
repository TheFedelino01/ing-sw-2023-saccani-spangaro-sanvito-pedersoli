package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;

/**
 * msgGenericErrorWhenEntryingGame class.
 * Extends SocketServerGenericMessage and is used to send a generic error message to the client
 * when entering a game.
 */
public class msgGenericErrorWhenEntryingGame extends SocketServerGenericMessage{

    private String why;

    /**
     * Constructor of the class.
     * @param why the reason for the error
     */
    public msgGenericErrorWhenEntryingGame(String why) {
        this.why=why;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.genericErrorWhenEnteringGame(why);
    }
}
