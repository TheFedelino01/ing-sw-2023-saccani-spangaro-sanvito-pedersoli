package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgSentMessage extends SocketServerGenericMessage {
    private Message msg;
    private GameModelImmutable gameModel;

    public msgSentMessage(GameModelImmutable gameModel, Message msg) {
        this.gameModel = gameModel;
        this.msg = msg;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.sentMessage(gameModel, msg);
    }
}
