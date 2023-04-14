package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgPositionedTile extends SocketServerGenericMessage{
    private GameModelImmutable gamemodel;
    private TileType type;
    private int column;

    public msgPositionedTile(GameModelImmutable gamemodel, TileType type, int column) {
        this.gamemodel = gamemodel;
        this.type = type;
        this.column = column;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.positionedTile(gamemodel,type,column);
    }
}
