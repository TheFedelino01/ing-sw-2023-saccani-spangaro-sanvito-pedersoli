package polimi.ingsw.view.userView;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.view.userView.utilities.FileDisconnection;


public abstract class Flow implements GameListener {
    protected void resetGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    protected void saveGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), model.getGameId());
        }
    }


}