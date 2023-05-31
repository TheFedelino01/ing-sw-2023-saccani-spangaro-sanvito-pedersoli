package polimi.ingsw.view.networking.socket.client.GameControllerMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.view.networking.socket.client.SocketClientGenericMessage;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public class SocketClientMessagePositionTileOnShelf extends SocketClientGenericMessage {
    private int column;
    private TileType type;

    public SocketClientMessagePositionTileOnShelf(String nick, int column, TileType type) {
        this.column = column;
        this.type = type;
        this.nick = nick;
        this.isMessageForMainController = false;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.positionTileOnShelf(nick, column, type);
    }
}
