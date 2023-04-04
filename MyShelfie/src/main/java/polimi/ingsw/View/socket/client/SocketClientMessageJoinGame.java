package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageJoinGame extends SocketClientMessage implements Serializable {
    GameListener lis;
    String nick;
    int idGame;

    public SocketClientMessageJoinGame(GameListener lis, String nick, int idGame) {
        this.lis = lis;
        this.idGame = idGame;
        this.nick = nick;
    }

    @Override
    public GameControllerInterface execute(MainControllerInterface mainController) throws RemoteException {
        return mainController.joinGame(lis, nick, idGame);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
