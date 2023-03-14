package polimi.ingsw.Model.GameModelView;

import polimi.ingsw.Model.Cards.Common.CardCommon;
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
        return model.getPlayers();
    }

    public List<CardCommon> getCommonCards() {
        return model.getCommonCards();
    }

    public Integer getGameId() {
        return model.getGameId();
    }

    public Playground getPg() {
        return model.getPg();
    }


}
