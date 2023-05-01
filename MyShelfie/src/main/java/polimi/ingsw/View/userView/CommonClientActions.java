package polimi.ingsw.View.userView;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.io.IOException;

public interface CommonClientActions {
    public void createGame(String nick) throws IOException, InterruptedException;

    public void joinFirstAvailable(String nick) throws IOException, InterruptedException;

    public void joinGame(String nick, int idGame) throws IOException, InterruptedException;

    public void reconnect(String nick, int idGame) throws IOException, InterruptedException;


    public void setAsReady() throws IOException;

    public boolean isMyTurn();

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException;

    public void positionTileOnShelf(int column, TileType type) throws IOException;

    void sendMessage(Message msg);

    public void heartbeat();

}
