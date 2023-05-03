package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgCommonCardsExtracted extends SocketServerGenericMessage {
    private GameModelImmutable model;

    public msgCommonCardsExtracted(GameModelImmutable gamemodel) {
        this.model = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.commonCardsExtracted(model);
    }
}
