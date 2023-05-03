package polimi.ingsw.View.networking.socket.client.MainControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageJoinFirst extends SocketClientGenericMessage {

    public SocketClientMessageJoinFirst(String nick) {
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinFirstAvailableGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
