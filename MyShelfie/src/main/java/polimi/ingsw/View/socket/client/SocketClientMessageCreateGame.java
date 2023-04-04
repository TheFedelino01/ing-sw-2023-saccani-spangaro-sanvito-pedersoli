package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageCreateGame extends SocketClientMessage implements Serializable {

    GameListener lis;
    String nick;

    public SocketClientMessageCreateGame(GameListener lis, String nick) {
        this.nick = nick;
        this.lis = lis;
    }

    @Override
    public GameControllerInterface execute(MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
