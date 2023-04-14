package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.io.IOException;
import java.rmi.RemoteException;

public class msgPlayerIsReadyToStart extends SocketServerGenericMessage{
    private GameModelImmutable model;
    private String nick;

    public msgPlayerIsReadyToStart(GameModelImmutable model, String nick) {
        this.model=model;
        this.nick = nick;
    }

    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerIsReadyToStart(model,nick);
    }
}
