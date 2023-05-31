package polimi.ingsw.view.networking.socket.client.MainControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.view.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageCreateGame extends SocketClientGenericMessage {


    public SocketClientMessageCreateGame(String nick) {
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, nick);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
