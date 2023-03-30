package polimi.ingsw.View.RMI;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameControllerInterface extends Remote {
    public boolean playerIsReadyToStart(Player p) throws RemoteException;
    public void grabTileFromPlayground(Player p, int x, int y, Direction direction, int num) throws RemoteException;
    public void positionTileOnShelf(Player p, int column, TileType type) throws RemoteException;
}
