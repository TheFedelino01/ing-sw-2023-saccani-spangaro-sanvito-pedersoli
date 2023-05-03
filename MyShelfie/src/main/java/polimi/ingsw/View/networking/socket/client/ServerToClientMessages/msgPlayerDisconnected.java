package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgPlayerDisconnected extends SocketServerGenericMessage {
    private String nick;
    private GameModelImmutable gameModel;

    public msgPlayerDisconnected(GameModelImmutable gameModel,String nick) {
        this.nick = nick;
        this.gameModel=gameModel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerDisconnected(gameModel,nick);
    }
}
