package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.io.IOException;

public class msgPlayerJoined extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    public msgPlayerJoined(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerJoined(gamemodel);
    }
}
