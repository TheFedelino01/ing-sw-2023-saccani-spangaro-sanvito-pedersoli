package polimi.ingsw.View.socket.client.GameControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;


public class SocketClientMessageNewChatMessage extends SocketClientGenericMessage {
    private Message msg;

    public SocketClientMessageNewChatMessage(Message msg) {
        this.msg = msg;
        this.nick = msg.getSender().getNickname();
        this.isMessageForMainController = false;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.sentMessage(msg);
    }
}