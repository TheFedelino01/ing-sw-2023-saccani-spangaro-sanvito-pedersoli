package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;

import java.io.IOException;
import java.io.Serializable;

public abstract class SocketServerGenericMessage implements Serializable {

    public abstract void execute(GameListener lis) throws IOException, InterruptedException;

}
