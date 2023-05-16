package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonHorizontalTest {
    List<CommonCard> model = new ArrayList<>();

    /**
     * Legend:<br>
     * N means that the tile has not been set<br>
     * C means that the tile is a CAT one (and so on, so T for TROPHY, ecc)<br>
     * R/X means that the tile is a random one
     */
    @BeforeEach
    void setUp() {
        for (CardCommonType t : CardCommonType.values())
            model.add(CommonCardFactory.getCommonCard(t));
    }

    /**
     * C C C C C<br>
     * B B B B B<br>
     * T T T T T<br>
     * C C C C C<br>
     * X X X X X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("First test Horizontal 0 ")
    public void firstTestHorizontal0() {

        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i == 0 || i == 3) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (i == 1) {
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (i == 2) {
                    test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                }
            }
        }
        assertTrue(model.get(7).verify(test));
    }

    /**
     * N N N N N<br>
     * X X X X X<br>
     * C C C C C<br>
     * T T T T T<br>
     * C C C C C<br>
     * B B B B B<br>
     */
    @Test
    @DisplayName("Second test Horizontal 0 ")
    public void secondTestHorizontal0() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 2 || r == 4) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (r == 5) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (r == 3) {
                    test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (r == 1) {
                    test.setSingleTile(new Tile(TileType.randomTile()), r, c);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertTrue(model.get(7).verify(test));
    }

    /**
     * C B F T A<br>
     * X X X X X<br>
     * C B F T A<br>
     * X X X X X<br>
     * X X X X X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test Horizontal 1")
    public void testHorizontal1() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0 || r == 2) {
                    switch (c) {
                        case (0) -> test.setSingleTile(new Tile(TileType.USED), r, c);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), r, c);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), r, c);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), r, c);
                        default -> {
                        }
                    }
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), r, c);
                }
                test.setFreeSpace(test.getFreeSpace() - 1);
            }
        }
        assertTrue(model.get(9).verify(test));
    }

    /**
     * N N N N N<br>
     * N N N N N<br>
     * X X X X X<br>
     * C B F T A<br>
     * C B F T A<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Second test Horizontal 1")
    public void secondTestHorizontal1() {

        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 3 || r == 4) {
                    switch (c) {
                        case (0) -> test.setSingleTile(new Tile(TileType.USED), r, c);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), r, c);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), r, c);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), r, c);
                        default -> {
                        }
                    }
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else if (r < 3) {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), r, c);
                    test.setFreeSpace(test.getFreeSpace() - 1);
                }
            }
        }
        assertTrue(model.get(9).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0 || j == 2) {
                    switch (i) {
                        case (0) -> test.setSingleTile(new Tile(TileType.CAT), i, j);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), i, j);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), i, j);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), i, j);
                        case (5) -> test.setSingleTile(new Tile(TileType.PLANT), i, j);
                        default -> {
                        }
                    }
                    test.setFreeSpace(test.getFreeSpace() - 1);
                } else {
                    test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
                }
            }
        }
        assertFalse(model.get(7).verify(test));
        assertFalse(model.get(9).verify(test));
    }

    @Test
    @DisplayName("Test with empty shelf")
    public void testEmptyShelf() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(7).verify(test));
        assertFalse(model.get(9).verify(test));
    }

}
