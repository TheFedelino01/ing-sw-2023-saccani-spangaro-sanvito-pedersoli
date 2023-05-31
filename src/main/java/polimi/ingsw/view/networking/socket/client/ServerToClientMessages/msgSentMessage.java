package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

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
