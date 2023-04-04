package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageJoinFirst implements SocketClientMessage, Serializable {
    GameListener lis;
    String nick;

    public SocketClientMessageJoinFirst(GameListener lis, String nick) {
        this.lis = lis;
        this.nick = nick;
    }

    @Override
    public void execute(MainControllerInterface mainController) throws RemoteException {
        mainController.joinFirstAvailableGame(lis, nick);
    }
}
