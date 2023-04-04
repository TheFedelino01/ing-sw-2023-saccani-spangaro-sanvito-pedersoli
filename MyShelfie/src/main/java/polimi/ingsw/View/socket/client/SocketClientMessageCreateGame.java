package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageCreateGame implements SocketClientMessage, Serializable {

    GameListener lis;
    String nick;

    public SocketClientMessageCreateGame(GameListener lis, String nick) {
        this.nick = nick;
        this.lis = lis;
    }

    @Override
    public void execute(MainControllerInterface mainController) throws RemoteException {
        mainController.createGame(lis, nick);
    }
}
