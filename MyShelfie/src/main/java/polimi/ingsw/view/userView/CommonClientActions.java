package polimi.ingsw.view.userView;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;

import java.io.IOException;

public interface CommonClientActions {
    public void createGame(String nick) throws IOException, InterruptedException;

    public void joinFirstAvailable(String nick) throws IOException, InterruptedException;

    public void joinGame(String nick, int idGame) throws IOException, InterruptedException;

    public void reconnect(String nick, int idGame) throws IOException, InterruptedException;

    public void leave(String nick, int idGame) throws IOException;

    public void setAsReady() throws IOException;

    public boolean isMyTurn();

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException;

    public void positionTileOnShelf(int column, TileType type) throws IOException;

    void sendMessage(Message msg);

    public void heartbeat();

}
