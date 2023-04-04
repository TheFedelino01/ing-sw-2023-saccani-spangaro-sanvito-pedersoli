package polimi.ingsw.View.socket.client.MainControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageJoinFirst extends SocketClientGenericMessage {
    String nick;

    public SocketClientMessageJoinFirst(String nick) {
        this.nick = nick;
        this.isMessageForMainController=true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis,MainControllerInterface mainController) throws RemoteException {
        return mainController.joinFirstAvailableGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
