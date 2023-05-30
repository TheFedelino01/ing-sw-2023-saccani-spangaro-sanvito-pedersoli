package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.io.IOException;

public class msgPlayerReconnected extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;
    private String nickPlayerReconnected;

    public msgPlayerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) {
        this.gamemodel = gamemodel;
        this.nickPlayerReconnected = nickPlayerReconnected;
    }

    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerReconnected(gamemodel, nickPlayerReconnected);
    }
}
