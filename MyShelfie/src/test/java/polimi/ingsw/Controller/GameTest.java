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
    public void testGame() throws GameEndedException {
        int i = 0;
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        gameController.addPlayer(p1);
        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.addPlayer(p2);
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

            if (i == 5) {
                i = 0;
            }
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            i = i + 1;

        }
        System.out.println("The winner is: " + gameController.whoIsPlaying().getNickname());
    }
}
