package polimi.ingsw.View.networking.socket.client.MainControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.MainControllerInterface;

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