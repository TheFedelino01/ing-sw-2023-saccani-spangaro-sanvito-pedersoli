package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;

import java.rmi.RemoteException;

public class msgSentMessage extends SocketServerGenericMessage{
    private Message msg;

    public msgSentMessage(Message msg) {
        this.msg = msg;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.sentMessage(msg);
    }
}
