package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageJoinGame implements SocketClientMessage, Serializable {
    GameListener lis;
    String nick;
    int idGame;

    public SocketClientMessageJoinGame(GameListener lis, String nick, int idGame) {
        this.lis = lis;
        this.idGame = idGame;
        this.nick = nick;
    }

    @Override
    public void execute(MainControllerInterface mainController) throws RemoteException {
        mainController.joinGame(lis, nick, idGame);
    }
}
