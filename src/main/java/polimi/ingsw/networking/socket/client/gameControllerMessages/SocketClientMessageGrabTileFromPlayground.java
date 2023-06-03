package polimi.ingsw.networking.socket.client.gameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

/**
 * SocketClientMessageGrabTileFromPlayground class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * requesting to grab a tile from the playground.
 */
public class SocketClientMessageGrabTileFromPlayground extends SocketClientGenericMessage {
    private int x, y, num;
    private Direction direction;

    /**
     * Constructor of the class.
     * @param nick the player's nickname
     * @param x the x-coordinate of the tile in the playground
     * @param y the y-coordinate of the tile in the playground
     * @param direction the direction in which the tile should be grabbed
     * @param num the number of the tile to be grabbed
     */
    public SocketClientMessageGrabTileFromPlayground(String nick, int x, int y, Direction direction, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.direction = direction;
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
        gameController.grabTileFromPlayground(nick, x, y, direction, num);
    }

}
