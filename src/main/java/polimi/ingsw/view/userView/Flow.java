package polimi.ingsw.view.userView;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.view.userView.utilities.FileDisconnection;


public abstract class Flow implements GameListener {
    /**
     * Resets the game id
     * @param fileDisconnection file to reset
     * @param model
     */
    protected void resetGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    /**
     * Saves latest game id
     * @param fileDisconnection file to write
     * @param model
     */
    protected void saveGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), model.getGameId());
        }
    }

    /**
     * Shows no connection error
     */
    public abstract void noConnectionError();



}