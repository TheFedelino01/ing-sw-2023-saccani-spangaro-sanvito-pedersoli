package polimi.ingsw.model.gameModelView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.*;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.cards.common.CommonEight;
import polimi.ingsw.model.cards.common.CommonXCard;
import polimi.ingsw.model.chat.Chat;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.CommonCardAlreadyInException;
import polimi.ingsw.model.exceptions.MaxCommonCardsAddedException;

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
    @Test
    @DisplayName("Test for getNicknameCurrentPlaying")
    void testNickname(){
        GameModel model = new GameModel();
        Player p1 = new Player("pippo");
        Player p2 = new Player("pippo2");
        model.addPlayer(p1);
        model.addPlayer(p2);
        model.playerIsReadyToStart(p1);
        model.playerIsReadyToStart(p2);
        model.arePlayersReadyToStartAndEnough();
        model.setCurrentPlaying(0);
        GameModelImmutable gameModelImmutable = new GameModelImmutable(model);
        Assertions.assertEquals("pippo", gameModelImmutable.getNicknameCurrentPlaying());

    }


    @Test
    @DisplayName("Test for hand and winner")
    void testHandAndWinner() throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        GameModel model = new GameModel();
        Player p1 = new Player("pippo");
        Player p2 = new Player("pippo2");
        model.addPlayer(p1);
        model.addPlayer(p2);
        CommonXCard cc = new CommonXCard(CardCommonType.CommonX);
        model.addCommonCard(cc);
        model.playerIsReadyToStart(p1);
        model.playerIsReadyToStart(p2);
        model.arePlayersReadyToStartAndEnough();
        model.setCurrentPlaying(0);
        GameModelImmutable gameModelImmutable = new GameModelImmutable(model);
        Assertions.assertEquals("pippo", gameModelImmutable.getNicknameCurrentPlaying());
        Assertions.assertEquals(new ArrayList<>(), gameModelImmutable.getHandOfCurrentPlaying());
        Assertions.assertEquals(-1, gameModelImmutable.getIndexWonPlayer());
        Assertions.assertEquals(-1, gameModelImmutable.getFirstFinishedPlayer());
        gameModelImmutable.toStringListPlayers();
        Assertions.assertEquals(true, gameModelImmutable.isMyTurn("pippo"));
        gameModelImmutable.getWinner();
        Assertions.assertEquals(p1, gameModelImmutable.getEntityCurrentPlaying());
        Assertions.assertEquals(cc, gameModelImmutable.getLastCommonCard());
        Assertions.assertEquals(p1, gameModelImmutable.getPlayerEntity("pippo"));
        Assertions.assertEquals(p2,gameModelImmutable.getLastPlayer());

    }

}