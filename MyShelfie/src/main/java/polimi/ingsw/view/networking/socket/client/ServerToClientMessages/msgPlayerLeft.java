package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgPlayerLeft extends SocketServerGenericMessage{
    private GameModelImmutable gamemodel;
    private String nick;

    public msgPlayerLeft(GameModelImmutable gamemodel,String nick) {
        this.gamemodel = gamemodel;
        this.nick=nick;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerLeft(gamemodel,nick);
    }
}
