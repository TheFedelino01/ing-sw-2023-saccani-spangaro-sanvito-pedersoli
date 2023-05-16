package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

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
