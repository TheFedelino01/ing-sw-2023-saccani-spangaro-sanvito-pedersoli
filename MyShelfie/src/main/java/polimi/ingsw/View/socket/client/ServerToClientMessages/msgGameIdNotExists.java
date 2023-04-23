package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;

public class msgGameIdNotExists extends SocketServerGenericMessage {
    private int gameid;

    public msgGameIdNotExists(int gameid) {
        this.gameid = gameid;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.gameIdNotExists(gameid);
    }
}
