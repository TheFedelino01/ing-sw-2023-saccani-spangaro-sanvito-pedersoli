package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

public class msgGrabbedTile extends SocketServerGenericMessage {

    private GameModelImmutable gamemodel;

    public msgGrabbedTile(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.grabbedTile(gamemodel);
    }
}
