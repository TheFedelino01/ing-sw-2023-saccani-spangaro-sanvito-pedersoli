package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgLastCircle extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    public msgLastCircle(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.lastCircle(gamemodel);
    }
}
