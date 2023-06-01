package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgPlayerDisconnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has been disconnected from the game.
 */
public class msgPlayerDisconnected extends SocketServerGenericMessage {
    private String nick;
    private GameModelImmutable gameModel;

    /**
     * Constructor of the class.
     * @param gameModel the immutable game model
     * @param nick the nickname of the disconnected player
     */
    public msgPlayerDisconnected(GameModelImmutable gameModel,String nick) {
        this.nick = nick;
        this.gameModel=gameModel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerDisconnected(gameModel,nick);
    }
}
