package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgOnlyOnePlayerConnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that only one player is currently connected to the game.
 */
public class msgOnlyOnePlayerConnected extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private int secondsToWaintUntilGameEnded;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     * @param secondsToWaintUntilGameEnded the number of seconds to wait until the game is ended
     */
    public msgOnlyOnePlayerConnected(GameModelImmutable gamemodel,int secondsToWaintUntilGameEnded) {
        this.model = gamemodel;
        this.secondsToWaintUntilGameEnded=secondsToWaintUntilGameEnded;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.onlyOnePlayerConnected(model,secondsToWaintUntilGameEnded);
    }
}
