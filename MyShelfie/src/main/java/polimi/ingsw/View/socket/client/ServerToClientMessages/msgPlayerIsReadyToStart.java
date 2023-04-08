package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgPlayerIsReadyToStart extends SocketServerGenericMessage{
    private String nick;

    public msgPlayerIsReadyToStart(String nick) {
        this.nick = nick;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerIsReadyToStart(nick);
    }
}
