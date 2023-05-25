package polimi.ingsw.model.gameModelView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.*;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.chat.Chat;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameModelImmutableTest {
    @Test
    public void testGameImmutableConstructor() {
        GameModel model = new GameModel();
        GameModelImmutable gameModelImmutable = new GameModelImmutable(model);
        Assertions.assertEquals(model.getPlayers(), gameModelImmutable.getPlayers());
        Assertions.assertEquals(model.getCommonCards(), gameModelImmutable.getCommonCards());
        Assertions.assertEquals(model.getGameId(), gameModelImmutable.getGameId());
        Assertions.assertEquals(model.getPg(), gameModelImmutable.getPg());
        Assertions.assertEquals(model.getCurrentPlaying(), gameModelImmutable.getCurrentPlaying());
        Assertions.assertEquals(model.getChat(), gameModelImmutable.getChat());
        Assertions.assertEquals(model.getStatus(), gameModelImmutable.getStatus());
        Assertions.assertEquals(model.getLeaderBoard(), gameModelImmutable.getLeaderBoard());

    }

    @Test
    public void testGameVoidConstructor(){
        GameModelImmutable gameModelImmutable = new GameModelImmutable();
        Assertions.assertEquals(new ArrayList<>(), gameModelImmutable.getPlayers());
        Assertions.assertEquals(new ArrayList<>(), gameModelImmutable.getCommonCards());
        Assertions.assertEquals(-1, gameModelImmutable.getGameId());
        gameModelImmutable.toStringListPlayers();
    }




}