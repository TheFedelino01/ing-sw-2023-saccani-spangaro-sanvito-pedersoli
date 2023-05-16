package polimi.ingsw.view.networking.socket.client.MainControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.view.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageReconnect extends SocketClientGenericMessage {

    private int idGame;

    public SocketClientMessageReconnect(String nick, int idGame) {
        this.idGame = idGame;
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.reconnect(lis, nick, idGame);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
