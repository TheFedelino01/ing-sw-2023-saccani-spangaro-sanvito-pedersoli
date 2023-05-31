package polimi.ingsw.view.networking.socket.client.MainControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.view.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public class SocketClientMessageLeave extends SocketClientGenericMessage {

    int idGame;

    public SocketClientMessageLeave(String nick, int idGame) {
        this.idGame = idGame;
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.leaveGame(lis, nick, idGame);
    }

    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}