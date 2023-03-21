package polimi.ingsw.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.PositioningATailNotGrabbedException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    GameController gameController;

    List<Player> plist = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        plist.add(new Player("1"));
        plist.add(new Player("2"));
        plist.add(new Player("3"));
        plist.add(new Player("4"));

    }

    @Test
    @DisplayName("Join Player and Start")
    void joinPlayersAndStart() {

        assertTrue(gameController.addPlayer(plist.get(0)), "Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(1)), "Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(2)), "Player not added but needed to");

        assertFalse(gameController.addPlayer(plist.get(0)), "Player added but was already in");
        assertFalse(gameController.addPlayer(plist.get(1)), "Player added but was already in");
        assertFalse(gameController.addPlayer(plist.get(2)), "Player added but was already in");


        assertFalse(gameController.playerIsReadyToStart(plist.get(0)), "Game started but not everyone was ready");
        assertFalse(gameController.playerIsReadyToStart(plist.get(1)), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(2)), "Game not started but everyone was ready to start");

        assertTrue(gameController.getCurrentPlaying() >= 0 && gameController.getCurrentPlaying() < gameController.getNumOfPlayers(), "Turn index overflow");

        List<CardCommon> risCommon = gameController.getAllCommonCards();


        boolean ris = true;
        for (int i = 0; i < risCommon.size(); i++) {
            for (int j = i + 1; j < risCommon.size(); j++) {
                if (risCommon.get(i).equals(risCommon.get(j))) ris = false;
            }
        }
        assertTrue(ris, "In Cards Common extracted there are duplicates");

        for (int i = 0; i < gameController.getNumOfPlayers(); i++) {
            for (int j = i + 1; j < gameController.getNumOfPlayers(); j++) {
                if (gameController.getGoalCard(i).equals(gameController.getGoalCard(j))) ris = false;
            }
        }
        assertTrue(ris, "In Cards Common extracted there are duplicates");

    }

    @Test
    @DisplayName("Grab and Position Tile in a running game")
    void grabAndPositionTile() {

        assertTrue(gameController.addPlayer(plist.get(0)), "Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(1)), "Player not added but needed to");

        assertFalse(gameController.playerIsReadyToStart(plist.get(0)), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(1)), "Game not started but everyone was ready to start");


        List<CardCommon> risCommon = gameController.getAllCommonCards();

        int currentPlayer = gameController.getCurrentPlaying();

        List<Tile> grabbed = gameController.grabTailFromPlayground(plist.get(currentPlayer), 0, 0, Direction.DOWN, 1);


        for (int i = 0; i < grabbed.size(); i++) {
            assertTrue(plist.get(currentPlayer).getInHandTail().get(i).isSameType(grabbed.get(i).getType()), "Mismatch between Tile grabbed from playground and Tile in hand");

        }

        assertThrows(PositioningATailNotGrabbedException.class, () -> gameController.positionTailOnShelf(plist.get(currentPlayer), 0, TileType.NOT_USED), "Wanted to position a Tail not grabbed");


        for (int i = 0; i < grabbed.size(); i++) {
            Tile firstTileInHand = plist.get(currentPlayer).getInHandTail().get(0);
            assertTrue(firstTileInHand.isSameType(grabbed.get(i).getType()), "Mismatch between Tile grabbed from playground and Tile in hand");

            Shelf shelf = plist.get(currentPlayer).getShelf();
            shelf.setShelf(new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf]);
            gameController.positionTailOnShelf(plist.get(currentPlayer), 0, grabbed.get(i).getType());

            //plist.get(currentPlayer).getShelf().get(0,0); manca implementazione inserimento in shelf
        }


    }


    @Test
    @DisplayName("Check Common Cards and Point assignment")
    public void testCheckCommonCards() {

        gameController.addPlayer(plist.get(0));
        gameController.addPlayer(plist.get(1));
        gameController.playerIsReadyToStart(plist.get(0));
        gameController.playerIsReadyToStart(plist.get(1));


        gameController.whoIsPlaying().addPoint(gameController.getAllCommonCards().get(0).getPoints().peek());

        gameController.getAllCommonCards().get(0).getPoints().remove();

        assertThrows(IllegalArgumentException.class, () -> gameController.whoIsPlaying().addPoint(gameController.getAllCommonCards().get(0).getPoints().peek()), "Added another point of the same card common.. You can't");




    }


    @Test
    @DisplayName("Check Goal Cards")
    public void testGoalCards() {

        Map<Player, CardGoal> ris = gameController.getAllGoalCards();
        //Add a new Goal card
        ris.put(plist.get(0), new CardGoal(CardGoalType.GOAL1));

        ris.keySet().forEach(player -> {
            if (player.equals(plist.get(0))) {
                assertEquals(ris.get(player).getGoalType(), CardGoalType.GOAL1, "The new card is not in the list");
            } else {
                assertEquals(ris.get(player).getGoalType(), CardGoalType.GOAL2, "The new card is not in the list");
            }
        });
        //TODO Implementare controllo assegnazione punteggio
    }


    @Test
    @DisplayName("Next turn")
    public void testNextTurn() {
        assertTrue(gameController.addPlayer(plist.get(0)), "Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(1)), "Player not added but needed to");

        assertFalse(gameController.playerIsReadyToStart(plist.get(0)), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(1)), "Game not started but everyone was ready to start");


        int currentPlayer = gameController.getCurrentPlaying();
        if(currentPlayer == 0) {
            assertEquals(currentPlayer, 0, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getCurrentPlaying();
            assertEquals(currentPlayer, 1, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getCurrentPlaying();
            assertEquals(currentPlayer, 0, "The current player is not correct");
        }
        else {
            assertEquals(currentPlayer, 1, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getCurrentPlaying();
            assertEquals(currentPlayer, 0, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getCurrentPlaying();
            assertEquals(currentPlayer, 1, "The current player is not correct");
        }
    }



}