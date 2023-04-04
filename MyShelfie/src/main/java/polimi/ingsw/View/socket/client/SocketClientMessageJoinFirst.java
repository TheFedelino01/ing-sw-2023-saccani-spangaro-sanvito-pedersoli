package polimi.ingsw.View.socket.client;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageJoinFirst extends SocketClientMessage implements Serializable {
    GameListener lis;
    String nick;

    public SocketClientMessageJoinFirst(GameListener lis, String nick) {
        this.lis = lis;
        this.nick = nick;
    }

    @Override
    public GameControllerInterface execute(MainControllerInterface mainController) throws RemoteException {
        return mainController.joinFirstAvailableGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
