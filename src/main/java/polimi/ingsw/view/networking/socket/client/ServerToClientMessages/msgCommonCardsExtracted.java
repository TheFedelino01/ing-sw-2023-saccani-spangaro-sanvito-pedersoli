package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgCommonCardsExtracted class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that common cards have been extracted in the game.
 */
public class msgCommonCardsExtracted extends SocketServerGenericMessage {
    private GameModelImmutable model;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgCommonCardsExtracted(GameModelImmutable gamemodel) {
        this.model = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.commonCardsExtracted(model);
    }
}
