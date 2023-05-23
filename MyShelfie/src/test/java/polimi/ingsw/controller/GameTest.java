package polimi.ingsw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.view.userView.gui.IntRecord;

public class GameTest {
    GameController gameController;
    Direction[] directions = Direction.values();
    IntRecord[] matrix2D = new IntRecord[29];
    IntRecord[] matrix3D = new IntRecord[36];

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        matrix2D[0] = new IntRecord(1, 3);
        matrix2D[1] = new IntRecord(1, 4);
        matrix2D[2] = new IntRecord(2, 3);
        matrix2D[3] = new IntRecord(2, 4);
        matrix2D[4] = new IntRecord(2, 5);
        matrix2D[5] = new IntRecord(3, 2);
        matrix2D[6] = new IntRecord(3, 3);
        matrix2D[7] = new IntRecord(3, 4);
        matrix2D[8] = new IntRecord(3, 5);
        matrix2D[9] = new IntRecord(3, 6);
        matrix2D[10] = new IntRecord(4, 1);
        matrix2D[11] = new IntRecord(4, 2);
        matrix2D[12] = new IntRecord(4, 3);
        matrix2D[13] = new IntRecord(4, 4);
        matrix2D[14] = new IntRecord(4, 5);
        matrix2D[15] = new IntRecord(4, 6);
        matrix2D[16] = new IntRecord(4, 7);
        matrix2D[17] = new IntRecord(5, 1);
        matrix2D[18] = new IntRecord(5, 2);
        matrix2D[19] = new IntRecord(5, 3);
        matrix2D[20] = new IntRecord(5, 4);
        matrix2D[21] = new IntRecord(5, 5);
        matrix2D[22] = new IntRecord(5, 6);
        matrix2D[23] = new IntRecord(6, 3);
        matrix2D[24] = new IntRecord(6, 4);
        matrix2D[25] = new IntRecord(6, 5);
        matrix2D[26] = new IntRecord(7, 4);
        matrix2D[27] = new IntRecord(7, 5);
        matrix2D[28] = new IntRecord(3, 7);

        for (int i = 0; i < 28; i++) {
            matrix3D[i] = new IntRecord(matrix2D[i].row(), matrix2D[i].col());
        }

        matrix3D[28] = new IntRecord(0, 3);
        matrix3D[29] = new IntRecord(2, 6);
        matrix3D[30] = new IntRecord(3, 8);
        matrix3D[31] = new IntRecord(5, 0);
        matrix3D[32] = new IntRecord(6, 2);
        matrix3D[33] = new IntRecord(6, 6);
        matrix3D[34] = new IntRecord(8, 5);
    }

    @Test
    @DisplayName("Simulate a game with 2 players")
    public void testGame2Player() {
        int i = 0;
        int index = 0;
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
        assert (gameController.getStatus().equals(GameStatus.RUNNING));

        while (gameController.getStatus().equals(GameStatus.RUNNING) || gameController.getStatus().equals(GameStatus.LAST_CIRCLE)) {

            do {
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix2D[index].row(), matrix2D[index].col(), Direction.DOWN, 1);
                index++;
                if (index == 28) {
                    index = 0;
                }
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
        //Problema: un giocatore riempie la shelf e l'altro player rimane con un free space, e non esce mai dal last circle
        assert (p1.getShelf().getFreeSpace() == 0);
        assert (p2.getShelf().getFreeSpace() == 0);
        assert (gameController.getStatus().equals(GameStatus.ENDED));
    }

    @Test
    @DisplayName("Simulate a game with 3 players")
    public void testGame3Player() {
        int i = 0;
        int a = 0;
        int index = 0;
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
        while (gameController.getStatus() == GameStatus.RUNNING || gameController.getStatus() == GameStatus.LAST_CIRCLE) {
            do {

                    gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(),matrix3D[index].row(),matrix3D[index].col(), Direction.DOWN, 1);
                index++;
                if (index == 35) {
                    index = 0;
                }
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
        //Problema: un giocatore riempie la shelf e l'altro player rimane con un free space, e non esce mai dal last circle
        assert (p1.getShelf().getFreeSpace() == 0);
        assert (p2.getShelf().getFreeSpace() == 0);
        assert (p3.getShelf().getFreeSpace() == 0);
        assert (gameController.getStatus().equals(GameStatus.ENDED));
    }
}
