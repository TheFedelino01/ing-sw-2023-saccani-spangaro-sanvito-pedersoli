package polimi.ingsw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.*;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.view.userView.gui.IntRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    GameController gameController;
    Direction[] directions = Direction.values();
    IntRecord[] matrix2D = new IntRecord[29];

    List<Shelf> shelves = new ArrayList<>();
    IntRecord[] matrix3D = new IntRecord[36];

    IntRecord[] matrix2DWith2Tiles = new IntRecord[29];

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
    @DisplayName("test 3 player game, where the first player finishes")
    public void testFirstPlayerFinishes() {
        int placeCol1 = 0, placeCol2 = 0, placeCol3 = 0, move = 0;
        shelves = createRandomShelves(3);
        IntRecord moves = setMoves(0);
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        gameController.addPlayer(p1);

        //Check if the player is correctly added to the game
        assertEquals(1, gameController.getPlayers().size());
        gameController.addPlayer(p2);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 2);
        gameController.addPlayer(p3);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 3);
        assertEquals(gameController.getStatus(), GameStatus.WAIT);

        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());

        //for each shelf remove some tiles
        removeTile(shelves.get(0), 0, 2);
        removeTile(shelves.get(1), 0, 10);
        removeTile(shelves.get(2), 0, 5);

        p1.setShelf(shelves.get(0));
        p2.setShelf(shelves.get(1));
        p3.setShelf(shelves.get(2));
        //Check that the game status is running, otherwise fail the test
        assertEquals(gameController.getStatus(), GameStatus.RUNNING);

        while (gameController.getStatus() == GameStatus.RUNNING || gameController.getStatus() == GameStatus.LAST_CIRCLE) {
            if (gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.FINISHED_USING) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.USED) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.NOT_USED)) {
                move++;
                moves = setMoves(move);

            }


            gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), moves.row(), moves.col(), Direction.DOWN, 1);
            //check if the tile is correctly added to the player's hand
            assertEquals(gameController.whoIsPlaying().getInHandTile().size(), 1);
            moves = new IntRecord(moves.row() + 1, moves.col());

            Player p = gameController.whoIsPlaying();
            int oldFreeSpace = p.getShelf().getFreeSpace();
            if (gameController.whoIsPlaying().equals(p1)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol1).isSameType(TileType.NOT_USED))
                    placeCol1++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol1, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p2)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol2).isSameType(TileType.NOT_USED))
                    placeCol2++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol2, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p3)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol3).isSameType(TileType.NOT_USED))
                    placeCol3++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol3, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            }
            int newFreeSpace = p.getShelf().getFreeSpace();
            //check if the tile is correctly added to the shelf
            assertEquals(newFreeSpace, oldFreeSpace - 1);
        }

        assertEquals(p1.getShelf().getFreeSpace(), 0);
        assertNotEquals(p2.getShelf().getFreeSpace(), 0);
        assertNotEquals(p3.getShelf().getFreeSpace(), 0);
        assertEquals(gameController.getStatus(), GameStatus.ENDED);
    }

    @Test
    @DisplayName("test 3 player game, where the second player finishes")
    public void testSecondPlayerFinishes() {
        int placeCol1 = 0, placeCol2 = 0, placeCol3 = 0, move = 0;
        shelves = createRandomShelves(3);
        IntRecord moves = setMoves(0);
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        gameController.addPlayer(p1);

        //Check if the player is correctly added to the game
        assertEquals(1, gameController.getPlayers().size());
        gameController.addPlayer(p2);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 2);
        gameController.addPlayer(p3);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 3);
        assertEquals(gameController.getStatus(), GameStatus.WAIT);

        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());

        //for each shelf remove some tiles
        // the first one remove 1
        // the second one remove 4
        // the third one remove 10
        removeTile(shelves.get(0), 0, 10);
        removeTile(shelves.get(1), 0, 2);
        removeTile(shelves.get(2), 0, 5);

        p1.setShelf(shelves.get(0));
        p2.setShelf(shelves.get(1));
        p3.setShelf(shelves.get(2));
        //Check that the game status is running, otherwise fail the test
        assertEquals(gameController.getStatus(), GameStatus.RUNNING);

        while (gameController.getStatus() == GameStatus.RUNNING || gameController.getStatus() == GameStatus.LAST_CIRCLE) {
            if (gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.FINISHED_USING) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.USED) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.NOT_USED)) {
                move++;
                moves = setMoves(move);

            }


            gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), moves.row(), moves.col(), Direction.DOWN, 1);
            //check if the tile is correctly added to the player's hand
            assertEquals(gameController.whoIsPlaying().getInHandTile().size(), 1);
            moves = new IntRecord(moves.row() + 1, moves.col());

            Player p = gameController.whoIsPlaying();
            int oldFreeSpace = p.getShelf().getFreeSpace();
            if (gameController.whoIsPlaying().equals(p1)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol1).isSameType(TileType.NOT_USED))
                    placeCol1++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol1, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p2)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol2).isSameType(TileType.NOT_USED))
                    placeCol2++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol2, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p3)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol3).isSameType(TileType.NOT_USED))
                    placeCol3++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol3, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            }
            int newFreeSpace = p.getShelf().getFreeSpace();
            //check if the tile is correctly added to the shelf
            assertEquals(newFreeSpace, oldFreeSpace - 1);
        }

        assertNotEquals(p1.getShelf().getFreeSpace(), 0);
        assertEquals(p2.getShelf().getFreeSpace(), 0);
        assertNotEquals(p3.getShelf().getFreeSpace(), 0);
        assertEquals(gameController.getStatus(), GameStatus.ENDED);
    }
    @Test
    @DisplayName("test 3 player game, where the third player finishes")
    public void testThirdPlayerFinishes() {
        int placeCol1 = 0, placeCol2 = 0, placeCol3 = 0, move = 0;
        shelves = createRandomShelves(3);
        IntRecord moves = setMoves(0);
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        gameController.addPlayer(p1);

        //Check if the player is correctly added to the game
        assertEquals(1, gameController.getPlayers().size());
        gameController.addPlayer(p2);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 2);
        gameController.addPlayer(p3);

        //Check if the player is correctly added to the game
        assertEquals(gameController.getPlayers().size(), 3);
        assertEquals(gameController.getStatus(), GameStatus.WAIT);

        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());

        //for each shelf remove some tiles
        // the first one remove 1
        // the second one remove 4
        // the third one remove 10
        removeTile(shelves.get(0), 0, 10);
        removeTile(shelves.get(1), 0, 5);
        removeTile(shelves.get(2), 0, 2);

        p1.setShelf(shelves.get(0));
        p2.setShelf(shelves.get(1));
        p3.setShelf(shelves.get(2));
        //Check that the game status is running, otherwise fail the test
        assertEquals(gameController.getStatus(), GameStatus.RUNNING);

        while (gameController.getStatus() == GameStatus.RUNNING || gameController.getStatus() == GameStatus.LAST_CIRCLE) {
            if (gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.FINISHED_USING) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.USED) ||
                gameController.getPlayground().getTile(moves.row(), moves.col()).isSameType(TileType.NOT_USED)) {
                move++;
                moves = setMoves(move);

            }


            gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), moves.row(), moves.col(), Direction.DOWN, 1);
            //check if the tile is correctly added to the player's hand
            assertEquals(gameController.whoIsPlaying().getInHandTile().size(), 1);
            moves = new IntRecord(moves.row() + 1, moves.col());

            Player p = gameController.whoIsPlaying();
            int oldFreeSpace = p.getShelf().getFreeSpace();
            if (gameController.whoIsPlaying().equals(p1)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol1).isSameType(TileType.NOT_USED))
                    placeCol1++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol1, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p2)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol2).isSameType(TileType.NOT_USED))
                    placeCol2++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol2, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            } else if (gameController.whoIsPlaying().equals(p3)) {
                if (!gameController.whoIsPlaying().getShelf().get(0, placeCol3).isSameType(TileType.NOT_USED))
                    placeCol3++;
                gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(),
                        placeCol3, gameController.whoIsPlaying().getInHandTile().get(0).getType());
            }
            int newFreeSpace = p.getShelf().getFreeSpace();
            //check if the tile is correctly added to the shelf
            assertEquals(newFreeSpace, oldFreeSpace - 1);
        }

        assertNotEquals(p1.getShelf().getFreeSpace(), 0);
        assertNotEquals(p2.getShelf().getFreeSpace(), 0);
        assertEquals(p3.getShelf().getFreeSpace(), 0);
        assertEquals(gameController.getStatus(), GameStatus.ENDED);
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

                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix3D[index].row(), matrix3D[index].col(), Direction.DOWN, 1);
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

    @Test
    @DisplayName("Simulate a game with 2 players picking 2 tiles")
    public void testGame2PlayerPicking2Tiles() {
        Player startingP;
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
        startingP = gameController.whoIsPlaying();
        while (gameController.getStatus().equals(GameStatus.RUNNING) || gameController.getStatus().equals(GameStatus.LAST_CIRCLE)) {

            do {
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix2D[index].row(), matrix2D[index].col(), Direction.DOWN, 2);
                if (gameController.whoIsPlaying().getInHandTile().size() == 0) {
                    gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix2D[index].row(), matrix2D[index].col(), Direction.DOWN, 1);
                }
                index++;
                if (index == 28) {
                    index = 0;
                }
            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);

            //check if the tile is correctly added to the player's hand
            assert (gameController.whoIsPlaying().getInHandTile().size() == 2 || gameController.whoIsPlaying().getInHandTile().size() == 1);
            if (i == 5) {
                i = 0;
            }
            Player p = gameController.whoIsPlaying();
            int freeSpace = p.getShelf().getFreeSpace();
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());

            i = i + 1;
        }
        //Problema: un giocatore riempie la shelf e l'altro player rimane con un free space, e non esce mai dal last circle
        assert (startingP.getShelf().getFreeSpace() == 0);
        assert (gameController.getStatus().equals(GameStatus.ENDED));
    }

    //Not Working
    @Disabled
    @Test
    @DisplayName("Simulate a game with 3 players picking 2 tiles")
    public void testGame3PlayerPicking2Tiles() {
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

                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix3D[index].row(), matrix3D[index].col(), Direction.DOWN, 2);
                if (gameController.whoIsPlaying().getInHandTile().size() == 0) {
                    gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix3D[index].row(), matrix3D[index].col(), Direction.DOWN, 1);
                }
                index++;
                if (index == 35) {
                    index = 0;
                }
            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);
            //check if the tile is correctly added to the player's hand

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
        }
        //Problema: un giocatore riempie la shelf e l'altro player rimane con un free space, e non esce mai dal last circle
        assert (gameController.getStatus().equals(GameStatus.ENDED));
    }

    private List<Shelf> createRandomShelves(int playerNumber) {
        List<Shelf> shelves1 = new ArrayList<>();
        for (int i = 0; i < playerNumber; i++) {
            Tile[][] t = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                    t[r][c] = new Tile(TileType.randomTile());
            shelves1.add(new Shelf(t, 0));
        }
        return shelves1;
    }

    private void removeTile(Shelf s, int col, int numToRemove) {
        int row = 0, cont = 0;
        while (cont < numToRemove) {
            if (row >= DefaultValue.NumOfRowsShelf) {
                row = 0;
                col++;
            }
            while (s.get(row, col).equals(new Tile(TileType.NOT_USED))) {
                row++;
                if (col >= DefaultValue.NumOfColumnsShelf)
                    return;
            }
            s.setSingleTile(new Tile(TileType.NOT_USED), row, col);
            s.setFreeSpace(s.getFreeSpace() + 1);
            row++;
            cont++;
        }
    }

    private IntRecord setMoves(int move) {
        //TODO: now only works with three players, will add
        // also two and four players
        switch (move) {
            case (0) -> {
                return new IntRecord(5, 0);
            }
            case (1) -> {
                return new IntRecord(4, 1);
            }
            case (2) -> {
                return new IntRecord(2, 2);
            }
            case (3) -> {
                return new IntRecord(0, 3);
            }
            case (4) -> {
                return new IntRecord(1, 4);
            }
            case (5) -> {
                return new IntRecord(2, 5);
            }
            case (6) -> {
                return new IntRecord(2, 6);
            }
            case (7) -> {
                return new IntRecord(3, 7);
            }
            case (8) -> {
                return new IntRecord(3, 8);
            }
            default -> {
                throw new RuntimeException("Game broke");
            }
        }
    }
}
