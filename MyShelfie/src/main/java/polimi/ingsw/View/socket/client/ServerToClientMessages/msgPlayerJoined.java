package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgPlayerJoined extends SocketServerGenericMessage{
    private String nickNewPlayer;

    public msgPlayerJoined(String nickNewPlayer) {
        this.nickNewPlayer = nickNewPlayer;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerJoined(nickNewPlayer);
    }
}
