package polimi.ingsw.View.userView;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.userView.Events.EventElement;

import java.io.IOException;

public abstract class View implements GameListener {
    protected abstract void resetGameId(EventElement element);
    protected abstract void saveGameId(EventElement element);
}