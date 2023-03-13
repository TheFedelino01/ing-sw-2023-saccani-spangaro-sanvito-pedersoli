package Model.GameModelView;

import Model.Enumeration.CardCommon;
import Model.GameModel;
import Model.Player;
import Model.Playground;

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
