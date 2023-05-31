package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;

import java.io.IOException;
import java.io.Serializable;

public abstract class SocketServerGenericMessage implements Serializable {

    public abstract void execute(GameListener lis) throws IOException, InterruptedException;

}
