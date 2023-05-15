package polimi.ingsw.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.Exceptions.PlayerAlreadyInException;
import polimi.ingsw.Model.Exceptions.PositioningATileNotGrabbedException;

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

        gameController.addPlayer(plist.get(0));
        gameController.addPlayer(plist.get(1));
        gameController.addPlayer(plist.get(2));

        assertThrows(PlayerAlreadyInException.class, () -> gameController.addPlayer(plist.get(0)), "Player added but was already in");
        assertThrows(PlayerAlreadyInException.class, () -> gameController.addPlayer(plist.get(1)), "Player added but was already in");
        assertThrows(PlayerAlreadyInException.class, () -> gameController.addPlayer(plist.get(2)), "Player added but was already in");


        assertFalse(gameController.playerIsReadyToStart(plist.get(0).getNickname()), "Game started but not everyone was ready");
        assertFalse(gameController.playerIsReadyToStart(plist.get(1).getNickname()), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(2).getNickname()), "Game not started but everyone was ready to start");

        assertTrue(gameController.getIndexCurrentPlaying() >= 0 && gameController.getIndexCurrentPlaying() < gameController.getNumOfPlayers(), "Turn index overflow");

        List<CommonCard> risCommon = gameController.getAllCommonCards();


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

    @Disabled
    @Test
    @DisplayName("Grab and Position a Tile in a running game")
    void grabAndPositionATile() {

        gameController.addPlayer(plist.get(0));
        gameController.addPlayer(plist.get(1));

        assertFalse(gameController.playerIsReadyToStart(plist.get(0).getNickname()), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(1).getNickname()), "Game not started but everyone was ready to start");


        List<CommonCard> risCommon = gameController.getAllCommonCards();

        int currentPlayer = gameController.getIndexCurrentPlaying();

        gameController.grabTileFromPlayground(plist.get(currentPlayer).getNickname(), 1, 3, Direction.DOWN, 1);

        Tile grabbed = plist.get(currentPlayer).getInHandTile().get(0);

        assertThrows(PositioningATileNotGrabbedException.class, () -> gameController.positionTileOnShelf(plist.get(currentPlayer).getNickname(), 0, TileType.NOT_USED), "Wanted to position a Tail not grabbed");


        assertEquals(1, plist.get(currentPlayer).getInHandTile().size(), "Grabbed mismatch");
        try {
            gameController.positionTileOnShelf(plist.get(currentPlayer).getNickname(), 0, plist.get(currentPlayer).getInHandTile().get(0).getType());
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, plist.get(currentPlayer).getInHandTile().size(), "Positioned tile on shelf but player's hand not free");


        if (!plist.get(currentPlayer).getShelf().get(DefaultValue.NumOfRowsShelf - 1, 0).isSameType(grabbed.getType())) {
            assertEquals(0, plist.get(currentPlayer).getInHandTile().size(), "Positioned a wrong tile");
        }


    }


    @Disabled
    @Test
    @DisplayName("Check Common Cards and Point assignment")
    public void testCheckCommonCards() {

        gameController.addPlayer(plist.get(0));
        gameController.addPlayer(plist.get(1));
        gameController.playerIsReadyToStart(plist.get(0).getNickname());
        gameController.playerIsReadyToStart(plist.get(1).getNickname());


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
        for (int i = 0; i < gameController.getNumOfPlayers(); i++) {
            for (int j = i + 1; j < gameController.getNumOfPlayers(); j++) {
                assertNotEquals(gameController.getGoalCard(i), gameController.getGoalCard(j), "In Cards Goal extracted there are duplicates");
            }
        }

    }


    @Test
    @DisplayName("Next turn")
    public void testNextTurn() {
        gameController.addPlayer(plist.get(0));
        gameController.addPlayer(plist.get(1));

        assertFalse(gameController.playerIsReadyToStart(plist.get(0).getNickname()), "Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(1).getNickname()), "Game not started but everyone was ready to start");


        int currentPlayer = gameController.getIndexCurrentPlaying();
        if (currentPlayer == 0) {
            assertEquals(currentPlayer, 0, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getIndexCurrentPlaying();
            assertEquals(currentPlayer, 1, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getIndexCurrentPlaying();
            assertEquals(currentPlayer, 0, "The current player is not correct");
        } else {
            assertEquals(currentPlayer, 1, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getIndexCurrentPlaying();
            assertEquals(currentPlayer, 0, "The current player is not correct");

            gameController.nextTurn();
            currentPlayer = gameController.getIndexCurrentPlaying();
            assertEquals(currentPlayer, 1, "The current player is not correct");
        }
    }


    /**
     * Test whether the final game checks work or not
     * <br>
     * Player shelf:
     * C C C C C
     * C C B B B
     * T T C C C
     * F F F F F
     * P P T T T
     * T T T T T
     */
    @Test
    @DisplayName("Test final points")
    public void testFinalPoints() {
        GameController game = new GameController();
        Player p = new Player("test");
        Shelf playerShelf = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0)
                    playerShelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (r == DefaultValue.NumOfRowsShelf - 1)
                    playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 3)
                    playerShelf.setSingleTile(new Tile(TileType.FRAME), r, c);
                else if (r == 1 && c < 2)
                    playerShelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (r == 1)
                    playerShelf.setSingleTile(new Tile(TileType.BOOK), r, c);
                else if (r == 2 && c < 2)
                    playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 2)
                    playerShelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (c < 2){
                    playerShelf.setSingleTile(new Tile(TileType.PLANT), r, c);
                }else playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
            }
        }
        p.setShelf(playerShelf);
        game.addPlayer(p);
        game.checkFinal();
        assertEquals(25, game.getPlayers().get(0).getTotalPoints(), "Wrong checking algorithm!");
    }

    /**
     * Test whether the final game checks work or not
     * <br>
     * Player shelf:
     * N N N N N
     * C C B B B
     * T T C C C
     * F F F F F
     * P P T T T
     * T T T T T
     */
    @Test
    @DisplayName("Test final points 2")
    public void secondTestFinalPoints() {
        GameController game = new GameController();
        Player p = new Player("test");
        Shelf playerShelf = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0)
                    playerShelf.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                else if (r == DefaultValue.NumOfRowsShelf - 1)
                    playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 3)
                    playerShelf.setSingleTile(new Tile(TileType.FRAME), r, c);
                else if (r == 1 && c < 2)
                    playerShelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (r == 1)
                    playerShelf.setSingleTile(new Tile(TileType.BOOK), r, c);
                else if (r == 2 && c < 2)
                    playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 2)
                    playerShelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (c < 2){
                    playerShelf.setSingleTile(new Tile(TileType.PLANT), r, c);
                }else playerShelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
            }
        }
        p.setShelf(playerShelf);
        game.addPlayer(p);
        game.checkFinal();
        assertEquals(17, game.getPlayers().get(0).getTotalPoints(), "Wrong checking algorithm!");
    }


}