package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    GameController gameController;

    List<Player> plist = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();
        plist.add(new Player("1"));
        plist.add(new Player("2"));
        plist.add(new Player("3"));
        plist.add(new Player("4"));
        for (Player player : plist) {
            gameController.addPlayer(player);
        }
    }

    @Test
    @DisplayName("Test Player nickname")
    void Nickname() {
        Player player = new Player("Test");
        assertEquals("Test", player.getNickname());

        Player player2 = new Player("Test");
        assertEquals("Test", player2.getNickname());

        //TODO Dovrebbe restituire errore, perchè il nickname è già stato usato, ma nella classe Player non ho nessun riferimento agli altri Player, chi lo gestiste?
    }

    @Test
    @DisplayName("Test Player shelf")
    void Shelf() {
        Shelf shelf = new Shelf();
        shelf.setShelf(new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf]);
        plist.get(0).setShelf(shelf);
        assertEquals(shelf.getShelf(), plist.get(0).getShelf().getShelf());
    }


    @Test
    @DisplayName("Test Player secretGoal")
    void SecretGoal() {
        for (int i = 0; i < gameController.getNumOfPlayers(); i++) {
            plist.get(i).setSecretGoal(new CardGoal(CardGoalType.values()[i]));
            assertEquals(CardGoalType.values()[i], plist.get(i).getSecretGoal().getGoalType());
        }
    }


    @Test
    @DisplayName("Test Player readyToStart")
    void ReadyToStart() {
        Player player = new Player("Test");
        assertFalse(player.getReadyToStart());
        player.setReadyToStart();
        assertTrue(player.getReadyToStart());
    }
    @Test
    @DisplayName("Test Player addedPointNotify")
    void notifyTest(){
        GameModelImmutable gameModelImmutable = new GameModelImmutable();
        Player player = new Player("Test");
        CardGoalType cardGoalType = CardGoalType.GOAL1;
        player.addPoint(new Point(1,cardGoalType),gameModelImmutable);
    }

}