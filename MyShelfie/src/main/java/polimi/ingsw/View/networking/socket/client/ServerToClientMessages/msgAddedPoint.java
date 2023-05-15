package polimi.ingsw.View.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.rmi.RemoteException;

public class msgAddedPoint extends SocketServerGenericMessage {
    private Player p;
    private Point point;
    private GameModelImmutable gamemodel;

    public msgAddedPoint(Player p, Point point, GameModelImmutable gamemodel) {
        this.p = p;
        this.point = point;
        this.gamemodel=gamemodel;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.addedPoint(p, point,gamemodel);
    }
}
