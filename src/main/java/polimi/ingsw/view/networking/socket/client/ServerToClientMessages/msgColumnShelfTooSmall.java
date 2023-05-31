package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

public class msgColumnShelfTooSmall extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private int column;

    public msgColumnShelfTooSmall(GameModelImmutable gamemodel, int column) {
        this.model = gamemodel;
        this.column=column;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.columnShelfTooSmall(model,column);
    }
}
