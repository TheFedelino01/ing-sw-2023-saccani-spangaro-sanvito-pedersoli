package polimi.ingsw.View.RMI.remoteInterfaces;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.Chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameControllerInterface extends Remote {
    boolean playerIsReadyToStart(String p) throws RemoteException;

    void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) throws RemoteException;

    void positionTileOnShelf(String p, int column, TileType type) throws RemoteException, GameEndedException;

    boolean isThisMyTurn(String nick) throws RemoteException;

    void setConnectionStatus(String nick, GameListener lisOfClient, boolean connected) throws RemoteException;

    void heartbeat(String nick, GameListener me) throws RemoteException;

    void sentMessage(Message msg) throws RemoteException;

    int getGameId() throws RemoteException;

    int getNumOnlinePlayers() throws RemoteException;


}
