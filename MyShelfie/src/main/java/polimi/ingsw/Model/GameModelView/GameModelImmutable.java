package polimi.ingsw.Model.GameModelView;

import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Playground;

import java.util.List;

public class GameModelImmutable {
    private final GameModel model;

    public GameModelImmutable(GameModel model) {
        this.model = model;
    }

    public List<Player> getPlayers() {
        //return model.getPlayers();
        return null;
    }



    public Integer getGameId() {
        return model.getGameId();
    }

    public Playground getPg() {
        return model.getPg();
    }


}
