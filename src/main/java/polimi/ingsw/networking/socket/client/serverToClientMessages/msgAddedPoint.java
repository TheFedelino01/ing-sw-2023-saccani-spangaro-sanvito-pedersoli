package polimi.ingsw.networking.socket.client.serverToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.rmi.RemoteException;

/**
 * msgAddedPoint class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a point has been added to the player.
 */
public class msgAddedPoint extends SocketServerGenericMessage {
    private Player p;
    private Point point;
    private GameModelImmutable gamemodel;

    /**
     * Constructor of the class.
     * @param p the player to whom points have been added
     * @param point the point that was added
     * @param gamemodel the immutable game model
     */
    public msgAddedPoint(Player p, Point point, GameModelImmutable gamemodel) {
        this.p = p;
        this.point = point;
        this.gamemodel=gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.addedPoint(p, point,gamemodel);
    }
}
