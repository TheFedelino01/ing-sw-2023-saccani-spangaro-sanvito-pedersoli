package polimi.ingsw.View;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;

import java.io.IOException;
import java.rmi.RemoteException;

public interface CommonClientActions {
    public void createGame(String nick) throws IOException;

    public void joinFirstAvailable(String nick) throws IOException;

    public void joinGame(String nick, int idGame) throws IOException;

    public void setAsReady();

    public boolean isMyTurn();

    public void grabTileFromPlayground(int x, int y, Direction direction, int num);

    public void positionTileOnShelf(int column, TileType type);


}
