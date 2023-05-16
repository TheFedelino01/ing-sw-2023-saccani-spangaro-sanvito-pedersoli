package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.io.IOException;

public class msgPlayerIsReadyToStart extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private String nick;

    public msgPlayerIsReadyToStart(GameModelImmutable model, String nick) {
        this.model = model;
        this.nick = nick;
    }

    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerIsReadyToStart(model, nick);
    }
}
