package polimi.ingsw.Model.Cards.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonDiagonalTest {

    List<CommonCard> cards = new ArrayList<>();

    /**
     * Legend:<br>
     * N means that the tile has not been set<br>
     * C means that the tile is a CAT one (and so on, so T for TROPHY, ecc)<br>
     * R/X means that the tile is a random one
     */
    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            cards.add(c.getCommonCard(t));
    }

    /**
     * I'll be using the CAT tile as the one to assert its value<br>
     * C = CAT, N = NOT_USED, R = RANDOM_TILE<br>
     * C N N N N<br>
     * R C N N N<br>
     * R R C N N<br>
     * R R R C N<br>
     * R R R R C<br>
     * R R R R R<br>
     */
    @Test
    @DisplayName("Test Diagonal cards from top left to bottom right")
    public void testDiagonalTB() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i <= j) {
                    if (i == j) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }

        assertTrue(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
    }


    /**
     * C = CAT, N = NOT_USED, R = RANDOM_TILE<br>
     * N N N N N<br>
     * C N N N N<br>
     * R C N N N<br>
     * R R C N N<br>
     * R R R C N<br>
     * R R R R C<br>
     */
    @Test
    @DisplayName("Test Diagonal first row empty")
    public void testDiagonalTB2() {
        Shelf test = new Shelf();
        for (int i = 1; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i - 1 >= j) {
                    if (i - 1 == j) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }

        assertTrue(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
    }

    /**
     * C = CAT, N = NOT_USED, R = RANDOM_TILE<br>
     * N N N N C<br>
     * N N N C R<br>
     * N N C R R<br>
     * N C R R R<br>
     * C R R R R<br>
     * R R R R R<br>
     */
    @Test
    @DisplayName("Test Diagonal cards from bottom left to top right")
    public void testDiagonalBT() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i >= j) {
                    if (i == j) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }

        assertTrue(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
    }

    /**
     * C = CAT, N = NOT_USED, R = RANDOM_TILE<br>
     * N N N N N<br>
     * N N N N C<br>
     * N N N C R<br>
     * N N C R R<br>
     * N C R R R<br>
     * C R R R R<br>
     */
    @Test
    @DisplayName("Test Diagonal first row empty")
    public void testDiagonalBT2() {
        int last = DefaultValue.NumOfColumnsShelf + 1, temp;
        boolean check;
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            last--;
            check = true;
            for (int c = DefaultValue.NumOfColumnsShelf - 1; c > -1; c--) {
                temp = c;
                if (r > 0) {
                    if (last == temp) {
                        test.setSingleTile(new Tile(TileType.CAT), r, c);
                        check = false;
                    } else if (check) {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), r, c);
                    }
                }
            }
        }

        assertTrue(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
    }

    /**
     * I copied methods from other test classes to initialise the shelf,<br>
     * so that it cannot verify the diagonal condition<br>
     */
    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                switch (j) {
                    case 0 -> test.setSingleTile(new Tile(TileType.CAT), i, j);
                    case 1 -> test.setSingleTile(new Tile(TileType.BOOK), i, j);
                    case 2 -> test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                    case 3 -> test.setSingleTile(new Tile(TileType.ACTIVITY), i, j);
                    case 4 -> test.setSingleTile(new Tile(TileType.FRAME), i, j);
                }
            }
        }

        assertFalse(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
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

        assertFalse(cards.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonStair))
                .toList().get(0).verify(test));
    }

}
