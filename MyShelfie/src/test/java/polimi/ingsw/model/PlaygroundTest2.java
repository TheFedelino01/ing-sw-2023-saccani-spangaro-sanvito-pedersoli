package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.TileGrabbedNotCorrectException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaygroundTest2 {
    Playground p;

    @BeforeEach
    void setUp() {
        p = new Playground(4);
        p.initialisePlayground();
    }

    @Test
    @DisplayName("Grab a tile")
    void testGrab() throws TileGrabbedNotCorrectException {
        assertThrows(TileGrabbedNotCorrectException.class, () -> {
            p.grabTile(0, 0, Direction.UP, 1);
        });
    }

    @Test
    @DisplayName("All tile have free side1")
    void testFreeSide1() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 4, 4);
        p.setSingleTile(TileType.TROPHY, 0, 0);
        p.setSingleTile(TileType.TROPHY, 0, 4);

        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side2")
    void testFreeSide2() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 3, 6);
        p.setSingleTile(TileType.TROPHY, 5, 1);
        p.setSingleTile(TileType.TROPHY, 7, 0);

        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side3")
    void testFreeSide3() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 8, 8);
        p.setSingleTile(TileType.TROPHY, 5, 1);
        p.setSingleTile(TileType.TROPHY, 7, 3);

        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side4")
    void testFreeSide4() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 3, 6);
        p.setSingleTile(TileType.TROPHY, 5, 1);
        p.setSingleTile(TileType.TROPHY, 8, 8);


        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side5")
    void testFreeSide5() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 0, 8);
        p.setSingleTile(TileType.TROPHY, 5, 1);
        p.setSingleTile(TileType.TROPHY, 8, 8);


        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side6")
    void testFreeSide6() {
        Playground p = new Playground(4);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 8, 1);
        p.setSingleTile(TileType.TROPHY, 0, 0);
        p.setSingleTile(TileType.TROPHY, 3, 6);
        if (!p.allTileHaveAllFreeSide())
            assert (p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side7")
    void testFreeSide7() {
        Playground p = new Playground(4);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 0, 1);
        p.setSingleTile(TileType.TROPHY, 0, 0);
        p.setSingleTile(TileType.TROPHY, 3, 6);

        if (p.allTileHaveAllFreeSide())
            assert (!p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side8")
    void testFreeSide8() {
        Playground p = new Playground(4);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 7, 1);
        p.setSingleTile(TileType.TROPHY, 7, 0);
        p.setSingleTile(TileType.TROPHY, 3, 6);

        if (p.allTileHaveAllFreeSide())
            assert (!p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side9")
    void testFreeSide9() {
        Playground p = new Playground(4);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 8, 8);
        p.setSingleTile(TileType.TROPHY, 8, 7);
        p.setSingleTile(TileType.TROPHY, 3, 6);

        if (p.allTileHaveAllFreeSide())
            assert (!p.allTileHaveAllFreeSide());
    }

    @Test
    @DisplayName("All tile have free side10")
    void testFreeSide10() {
        Playground p = new Playground(4);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 3, 1);
        p.setSingleTile(TileType.TROPHY, 3, 5);
        p.setSingleTile(TileType.TROPHY, 3, 6);
        if (p.allTileHaveAllFreeSide())
            assert (!p.allTileHaveAllFreeSide());
    }
}
