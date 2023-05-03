package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

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
