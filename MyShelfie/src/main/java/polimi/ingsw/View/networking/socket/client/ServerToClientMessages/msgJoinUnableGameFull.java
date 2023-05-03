package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;

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
