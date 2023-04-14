package polimi.ingsw.View.socket.client.MainControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageCreateGame extends SocketClientGenericMessage {


    public SocketClientMessageCreateGame(String nick) {
        this.nick = nick;
        this.isMessageForMainController=true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis,MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
