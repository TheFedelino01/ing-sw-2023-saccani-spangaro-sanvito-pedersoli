package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgGenericErrorWhenEntryingGame extends SocketServerGenericMessage{

    private String why;
    public msgGenericErrorWhenEntryingGame(String why) {
        this.why=why;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.genericErrorWhenEntryingGame(why);
    }
}
