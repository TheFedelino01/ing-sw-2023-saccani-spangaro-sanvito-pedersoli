package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;

import java.rmi.RemoteException;

public class msgJoinUnableGameFull extends SocketServerGenericMessage {
    private Player p;
    private GameModelImmutable gamemodel;

    public msgJoinUnableGameFull(Player p, GameModelImmutable gamemodel) {
        this.p = p;
        this.gamemodel = gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        // lis.JoinUnableGameFull(p,gamemodel);
    }
}
