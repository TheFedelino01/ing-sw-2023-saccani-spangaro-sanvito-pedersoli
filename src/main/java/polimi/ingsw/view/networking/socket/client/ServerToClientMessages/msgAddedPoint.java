package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

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
