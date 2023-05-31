package polimi.ingsw.view.userView;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClientActions {
    public void createGame(String nick) throws IOException, InterruptedException, NotBoundException;

    public void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException;

    public void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    public void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    public void leave(String nick, int idGame) throws IOException, NotBoundException;

    public void setAsReady() throws IOException;

    public boolean isMyTurn() throws RemoteException;

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException;

    public void positionTileOnShelf(int column, TileType type) throws IOException, GameEndedException;

    void sendMessage(Message msg) throws RemoteException;

    public void heartbeat() throws RemoteException;

}
