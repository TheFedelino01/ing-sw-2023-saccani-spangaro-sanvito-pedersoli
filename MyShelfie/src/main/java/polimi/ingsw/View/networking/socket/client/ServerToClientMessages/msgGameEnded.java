package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgGameEnded extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    public msgGameEnded(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.gameEnded(gamemodel);
    }
}
