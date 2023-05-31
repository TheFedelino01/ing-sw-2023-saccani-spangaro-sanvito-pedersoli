package polimi.ingsw.view.networking.RMI.remoteInterfaces;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameControllerInterface extends Remote {
    boolean playerIsReadyToStart(String p) throws RemoteException;

    void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) throws RemoteException;

    void positionTileOnShelf(String p, int column, TileType type) throws RemoteException, GameEndedException;

    boolean isThisMyTurn(String nick) throws RemoteException;

    void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException;

    void heartbeat(String nick, GameListener me) throws RemoteException;

    void sentMessage(Message msg) throws RemoteException;

    int getGameId() throws RemoteException;

    int getNumOnlinePlayers() throws RemoteException;

    void leave(GameListener lis, String nick) throws RemoteException;
}
