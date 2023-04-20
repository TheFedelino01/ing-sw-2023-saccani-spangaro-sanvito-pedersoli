package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgGrabbedTileNotCorrect extends SocketServerGenericMessage{
    private GameModelImmutable gamemodel;

    public msgGrabbedTileNotCorrect(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.grabbedTileNotCorrect(gamemodel);
    }
}
