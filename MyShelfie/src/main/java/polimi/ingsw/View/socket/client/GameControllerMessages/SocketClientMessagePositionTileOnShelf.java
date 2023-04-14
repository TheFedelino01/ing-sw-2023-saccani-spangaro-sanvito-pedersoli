package polimi.ingsw.View.socket.client.GameControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessagePositionTileOnShelf extends SocketClientGenericMessage {
    private int column;
    private TileType type;

    public SocketClientMessagePositionTileOnShelf(String nick, int column, TileType type) {
        this.column=column;
        this.type=type;
        this.nick = nick;
        this.isMessageForMainController=false;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.positionTileOnShelf(nick,column,type);
    }
}
