package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;

/**
 * msgGameIdNotExists class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that the specified game ID does not exist.
 */
public class msgGameIdNotExists extends SocketServerGenericMessage {
    private int gameid;

    /**
     * Constructor of the class.
     * @param gameid the ID of the non-existent game
     */
    public msgGameIdNotExists(int gameid) {
        this.gameid = gameid;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.gameIdNotExists(gameid);
    }
}
