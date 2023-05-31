package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

public class msgGrabbedTileNotCorrect extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    public msgGrabbedTileNotCorrect(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.grabbedTileNotCorrect(gamemodel);
    }
}
