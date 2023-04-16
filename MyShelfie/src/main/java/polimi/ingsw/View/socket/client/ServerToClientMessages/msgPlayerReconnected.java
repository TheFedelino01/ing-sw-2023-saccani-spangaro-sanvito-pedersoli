package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.io.IOException;

public class msgPlayerReconnected extends SocketServerGenericMessage{
    private GameModelImmutable gamemodel;

    public msgPlayerReconnected(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerReconnected(gamemodel);
    }
}
