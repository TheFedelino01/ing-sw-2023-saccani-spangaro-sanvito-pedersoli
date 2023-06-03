package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;

import java.rmi.RemoteException;

/**
 * msgJoinUnableGameFull class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player was unable to join the game because it is full.
 */
public class msgJoinUnableGameFull extends SocketServerGenericMessage {
    private Player p;
    private GameModelImmutable gamemodel;

    /**
     * Constructor of the class.
     * @param p the player who was unable to join the game
     * @param gamemodel the immutable game model
     */
    public msgJoinUnableGameFull(Player p, GameModelImmutable gamemodel) {
        this.p = p;
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        // lis.JoinUnableGameFull(p,gamemodel);
    }
}
