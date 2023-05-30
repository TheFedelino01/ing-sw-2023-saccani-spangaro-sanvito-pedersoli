package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.TileGrabbedNotCorrectException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundTest {

    Playground p2, p3, p4;

    private int data[][] = {
            {0, 0, 0, 3, 4, 0, 0, 0, 0},
            {0, 0, 0, 2, 2, 4, 0, 0, 0},
            {0, 0, 3, 2, 2, 2, 3, 0, 0},
            {0, 4, 2, 2, 2, 2, 2, 2, 3},
            {4, 2, 2, 2, 2, 2, 2, 2, 4},
            {3, 2, 2, 2, 2, 2, 2, 4, 0},
            {0, 0, 3, 2, 2, 2, 3, 0, 0},
            {0, 0, 0, 4, 2, 2, 0, 0, 0},
            {0, 0, 0, 0, 4, 3, 0, 0, 0}
    };

    @BeforeEach
    void setUp() {
        p2 = new Playground(2);
        p3 = new Playground(3);
        p4 = new Playground(4);

    }

    @Test
    @DisplayName("Test position fill")
    void testPlaygroundClass() {
        testTilePosition(2);
        testTilePosition(3);
        testTilePosition(4);

        int totalTileInBag = DefaultValue.NumOfTilesPerType * DefaultValue.NumOfTileTypes;
        if (p2.getNumOfTileinTheBag() != (totalTileInBag) - 29) {
            assertTrue(false, "Num of tile in bag wrong for 2 players");
        }
        if (p3.getNumOfTileinTheBag() != (totalTileInBag) - 29 - 8) {
            assertTrue(false, "Num of tile in bag wrong for 3 players");
        }
        if (p4.getNumOfTileinTheBag() != (totalTileInBag) - 29 - 8 - 8) {
            assertTrue(false, "Num of tile in bag wrong for 4 players");
        }

    }

    private void testTilePosition(int numPlayer) {
        Playground test = p2;
        p2.toString();
        switch (numPlayer) {
            case 2 -> test = p2;
            case 3 -> test = p3;
            case 4 -> test = p4;
            default -> assertTrue(false, "Num of player over");
        }
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                if (data[r][c] != 0 && data[r][c] <= numPlayer && (test.getTile(r, c).getType().equals(TileType.NOT_USED) || test.getTile(r, c).getType().equals(TileType.FINISHED_USING))) {
                    assertTrue(false, "Tile wrong position");
                }
                if (data[r][c] != 0 && data[r][c] > numPlayer && !(test.getTile(r, c).getType().equals(TileType.NOT_USED))) {
                    assertTrue(false, "Tile wrong position");
                }
            }
        }

        assertTrue(true);
    }

    @Test
    @DisplayName("Grab Tile")
    void testGrab() {
        //todo il test verifica solo il grab di 2 tile semplici
        List<Tile> ris = null;
        try {
            ris = p2.grabTile(1, 3, Direction.RIGHT, 2);
        } catch (TileGrabbedNotCorrectException e) {
            throw new RuntimeException(e);
        }

        if (!p2.getTile(1, 3).isSameType(TileType.FINISHED_USING) || !p2.getTile(1, 4).isSameType(TileType.FINISHED_USING)) {
            fail("Tile grabbed wrong");
        }
        if (ris.size() != 2) {
            fail("Tile grabbed wrong");
        }
    }

    @Test
    @DisplayName("Refill playground")
    void testRefill() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        //place some tiles so that the playground doesn't get refilled if not after
        // the grabbing of a tile
        ptest.setSingleTile(TileType.TROPHY, 1, 2);
        ptest.setSingleTile(TileType.TROPHY, 1, 3);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 4, 4);
        ptest.setSingleTile(TileType.TROPHY, 0, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 4);

        System.out.println(ptest.getPlayground());

        //empty the playground by grabbing all the tiles I've set
        ptest.grabTile(1, 2, Direction.RIGHT, 2);

        //if the grabTile works, the playground ptest should now be refilled, and different from a blank one
        blank.setEmptyPlayground();
        assertNotEquals(ptest, blank, "Playground is not being refilled");

        System.out.println(ptest.getPlayground());
    }

    @Test
    @DisplayName("Refill playground")
    void testRefill2() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        ptest.setSingleTile(TileType.TROPHY, 8, 2);
        ptest.setSingleTile(TileType.TROPHY, 0, 8);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 4, 4);
        ptest.setSingleTile(TileType.TROPHY, 7, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 4);

        System.out.println(ptest.getPlayground());

        assertNotEquals(ptest, blank, "Playground is not being refilled");
        assertEquals(ptest.allTileHaveAllFreeSide(), true, "Playground is not being refilled");
        System.out.println(ptest.getPlayground());
    }
    @Test
    @DisplayName("Refill playgroun3")
    void testRefill3() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        ptest.setSingleTile(TileType.TROPHY, 0, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 7);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 4, 4);
        ptest.setSingleTile(TileType.TROPHY, 7, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 4);

        System.out.println(ptest.getPlayground());

        assertNotEquals(ptest, blank, "Playground is not being refilled");
        assertEquals(ptest.allTileHaveAllFreeSide(), true, "Playground is not being refilled");
        System.out.println(ptest.getPlayground());
    }
    @Test
    @DisplayName("Refill playground4")
    void testRefill4() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        ptest.setSingleTile(TileType.TROPHY, 8, 2);
        ptest.setSingleTile(TileType.TROPHY, 0, 8);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 4, 4);
        ptest.setSingleTile(TileType.TROPHY, 7, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 0);

        System.out.println(ptest.getPlayground());

        assertNotEquals(ptest, blank, "Playground is not being refilled");
        assertEquals(ptest.allTileHaveAllFreeSide(), true, "Playground is not being refilled");
        System.out.println(ptest.getPlayground());
    }
    @Test
    @DisplayName("Refill playground 5")
    void testRefill5() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        ptest.setSingleTile(TileType.TROPHY, 7, 0);
        ptest.setSingleTile(TileType.TROPHY, 0, 0);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 8, 1);
        ptest.setSingleTile(TileType.TROPHY, 0, 0);
        ptest.setSingleTile(TileType.TROPHY, 3, 6);

        System.out.println(ptest.getPlayground());

        assertNotEquals(ptest, blank, "Playground is not being refilled");
        assertEquals(ptest.allTileHaveAllFreeSide(), true, "Playground is not being refilled");
        System.out.println(ptest.getPlayground());
    }
    @Test
    @DisplayName("Refill playground 6")
    void testRefill6() throws TileGrabbedNotCorrectException {
        Playground ptest = new Playground(2);
        Playground blank = new Playground(2);
        ptest.setEmptyPlayground();

        ptest.setSingleTile(TileType.TROPHY, 8, 8);
        ptest.setSingleTile(TileType.TROPHY, 0, 1);

        //random tiles that aren't touching
        ptest.setSingleTile(TileType.TROPHY, 8, 1);
        ptest.setSingleTile(TileType.TROPHY, 0, 0);
        ptest.setSingleTile(TileType.TROPHY, 3, 6);

        System.out.println(ptest.getPlayground());

        assertNotEquals(ptest, blank, "Playground is not being refilled");
        assertEquals(ptest.allTileHaveAllFreeSide(), false, "Playground is not being refilled");
        System.out.println(ptest.getPlayground());
    }
}