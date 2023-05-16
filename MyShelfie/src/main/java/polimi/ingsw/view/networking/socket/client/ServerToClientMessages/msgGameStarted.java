package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgGameStarted extends SocketServerGenericMessage {

    private GameModelImmutable model;

    public msgGameStarted() {

    }

    public msgGameStarted(GameModelImmutable model) {
        this.model = model;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.gameStarted(model);
    }
}
