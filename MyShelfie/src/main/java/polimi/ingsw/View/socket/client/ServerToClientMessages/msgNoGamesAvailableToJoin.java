package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgNoGamesAvailableToJoin extends SocketServerGenericMessage{

    public msgNoGamesAvailableToJoin() {

    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.noGamesAvailableToJoin();
    }
}
