package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;

public class msgGenericErrorWhenEntryingGame extends SocketServerGenericMessage{

    private String why;
    public msgGenericErrorWhenEntryingGame(String why) {
        this.why=why;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.genericErrorWhenEnteringGame(why);
    }
}
