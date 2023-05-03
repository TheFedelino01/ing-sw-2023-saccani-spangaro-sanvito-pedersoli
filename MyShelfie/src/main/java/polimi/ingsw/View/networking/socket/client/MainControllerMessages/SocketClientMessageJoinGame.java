package polimi.ingsw.View.networking.socket.client.MainControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageJoinGame extends SocketClientGenericMessage {

    int idGame;

    public SocketClientMessageJoinGame(String nick, int idGame) {
        this.idGame = idGame;
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinGame(lis, nick, idGame);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
