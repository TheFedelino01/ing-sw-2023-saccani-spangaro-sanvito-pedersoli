package polimi.ingsw.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.Player;

import java.util.Random;

public class GameTest {
    GameController gameController;
    Direction[] directions = Direction.values();

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    @DisplayName("Simulate a game with 2 players")
    public void testGame2Player() throws GameEndedException {
        int i = 0;
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        gameController.addPlayer(p1);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 1);
        gameController.playerIsReadyToStart(p1.getNickname());
        //check if the player is ready
        gameController.addPlayer(p2);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 2);
        gameController.playerIsReadyToStart(p2.getNickname());


        //Check that the game status is running, otherwise fail the test
        assert (gameController.getStatus() == GameStatus.RUNNING);

        while (gameController.getStatus() == GameStatus.RUNNING) {
            do {
                Random random = new Random();
                int c = random.nextInt(9);
                int r = random.nextInt(9);
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), c, r, Direction.DOWN, 1);

            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);
            //check if the tile is correctly added to the player's hand
            assert (gameController.whoIsPlaying().getInHandTile().size() == 1);
            if (i == 5) {
                i = 0;
            }
            Player p = gameController.whoIsPlaying();
            int freeSpace = p.getShelf().getFreeSpace();
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            //check if the tile is correctly added to the shelf
            assert (p.getShelf().getFreeSpace() == freeSpace - 1);
            i = i + 1;
        }
    }

    @Test
    @DisplayName("Simulate a game with 3 players")
    public void testGame3Player() throws GameEndedException {
        int i = 0;
        int a = 0;
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        gameController.addPlayer(p1);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 1);
        gameController.addPlayer(p2);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 2);
        gameController.addPlayer(p3);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 3);
        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());
        //Check that the number of players is equal 3
        assert (gameController.getPlayers().size() == 3);
        //Check that the game status is running, otherwise fail the test
        assert (gameController.getStatus() == GameStatus.RUNNING);
        while (gameController.getStatus() == GameStatus.RUNNING) {
            do {
                Random random = new Random();
                int c = random.nextInt(9);
                int r = random.nextInt(9);
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), c, r, Direction.DOWN, 1);
            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);
            //check if the tile is correctly added to the player's hand
            assert (gameController.whoIsPlaying().getInHandTile().size() == 1);

            if (i == 5) {
                i = 0;
            }
            Player p = gameController.whoIsPlaying();
            int freeSpace = p.getShelf().getFreeSpace();
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());

            a = a + 1;
            if (a == 3) {
                i = i + 1;
                a = 0;
            }
            //check if the tile is correctly added to the shelf
            assert (p.getShelf().getFreeSpace() == freeSpace - 1);
        }
    }
}
