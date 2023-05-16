package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.rmi.RemoteException;

public class msgPositionedTile extends SocketServerGenericMessage {
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
        lis.positionedTile(gamemodel, type, column);
    }
}
