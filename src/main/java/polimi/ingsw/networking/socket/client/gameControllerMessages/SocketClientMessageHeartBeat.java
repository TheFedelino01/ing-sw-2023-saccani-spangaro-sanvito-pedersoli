package polimi.ingsw.networking.socket.client.gameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageHeartBeat extends SocketClientGenericMessage {

    public SocketClientMessageHeartBeat(String nick) {
        this.nick = nick;
        this.isMessageForMainController = false;
        this.isHeartbeat=true;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        return;
    }
}
