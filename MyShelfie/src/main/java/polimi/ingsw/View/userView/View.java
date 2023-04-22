package polimi.ingsw.View.userView;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;


public abstract class View implements GameListener {
    protected void resetGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (Player p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(),-1);
        }
    }
    protected void saveGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (Player p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(),model.getGameId());
        }
    }


}