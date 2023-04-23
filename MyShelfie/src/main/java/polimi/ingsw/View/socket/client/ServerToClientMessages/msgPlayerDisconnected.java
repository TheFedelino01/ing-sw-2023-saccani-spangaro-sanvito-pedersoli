package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgPlayerDisconnected extends SocketServerGenericMessage {
    private String nick;

    public msgPlayerDisconnected(String nick) {
        this.nick = nick;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerDisconnected(nick);
    }
}
