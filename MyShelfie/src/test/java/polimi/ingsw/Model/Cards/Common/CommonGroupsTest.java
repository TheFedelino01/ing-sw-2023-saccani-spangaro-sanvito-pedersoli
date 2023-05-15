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

public class CommonGroupsTest {

    List<CommonCard> model = new ArrayList<>();

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
            model.add(c.getCommonCard(t));
    }

    /**
     * case 1<br>
     * first common card<br>
     * <p>
     * C B C B X<br>
     * C B C B X<br>
     * X X X X X<br>
     * C B C B X<br>
     * C B C B X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test six groups 1")
    public void testSixGroups1() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((c == 0 && r < 2) || (c == 2 && r < 2) || (c == 0 && r > 2 && r < 5) || (c == 2 && r > 2 && r < 5)) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if ((c == 1 && r < 2) || (c == 3 && r < 2) || (c == 1 && r > 2 && r < 5) || (c == 3 && r > 2 && r < 5)) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                }
            }
        }

        //check this algorithm
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSixGroups))
                .toList().get(0).verify(test));
    }

    /**
     * case 1<br>
     * first common card<br>
     * <p>
     * N N N N N<br>
     * N B C B C<br>
     * X B C B C<br>
     * X X X X X<br>
     * C B C B X<br>
     * C B C B X<br>
     */
    @Test
    @DisplayName("Test six groups 2")
    public void testSixGroups2() {
        Shelf test = new Shelf();

        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((c == 0 && r > 3) || (c == 2 && r > 3) || (c == 2 && r > 0 && r < 3) || (c == 4 && r > 0 && r < 3)) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if ((c == 1 && r > 3) || (c == 3 && r > 3) || (c == 1 && r > 0 && r < 3) || (c == 3 && r > 0 && r < 3)) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else if (r == 3) {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                } else test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }

        //check this algorithm
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSixGroups))
                .toList().get(0).verify(test));
    }

    /**
     * four groups of four tiles of the same type (will be testing two columns in this test case)<br>
     * third common card<br>
     * <p>
     * C B C B X<br>
     * C B C B X<br>
     * C B C B X<br>
     * C B C B X<br>
     * X X X X X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test four groups 1")
    public void testFourGroups1() {


        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c < 4 && c % 2 == 0 && r < 4) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if (c < 4 && r < 4) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonFourGroups))
                .toList().get(0).verify(test));
    }

    /**
     * four groups of four tiles of the same type (will be testing two columns in this test case)<br>
     * third common card<br>
     * <p>
     * N N N N N<br>
     * N N N N N<br>
     * C B C B N<br>
     * C B C B N<br>
     * C B C B T<br>
     * C B C B T<br>
     */
    @Test
    @DisplayName("Test four groups 2")
    public void testFourGroups2() {


        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((c == 0 || c == 2) && r > 1) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if ((c == 1 || c == 3) && r > 1) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else if (c == DefaultValue.NumOfColumnsShelf - 1 && r > 4) {
                    test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                } else test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonFourGroups))
                .toList().get(0).verify(test));
    }

    /**
     * two separated groups of 2x2 tiles with the same type<br>
     * fourth common card<br>
     * <p>
     * C C X X X<br>
     * C C X X X<br>
     * X X X X X<br>
     * X X X B B<br>
     * X X X B B<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test squares 1")
    public void testSquares1() {

        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) || (i == 1 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1)) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                } else if ((i == 3 && j == 3) || (i == 4 && j == 3) || (i == 3 && j == 4) || (i == 4 && j == 4)) {
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    /**
     * two separated groups of 2x2 tiles with the same type<br>
     * fourth common card<br>
     * <p>
     * X C C X X<br>
     * X C C X X<br>
     * X X X X X<br>
     * X X B B X<br>
     * X X B B X<br>
     */
    @Test
    @DisplayName("Test squares 2")
    public void testSquares2() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((r == 0 && c == 1) || (r == 1 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 2)) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if ((r == 3 && c == 2) || (r == 4 && c == 2) || (r == 3 && c == 3) || (r == 4 && c == 3)) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    /**
     * two separated groups of 2x2 tiles with the same type<br>
     * fourth common card<br>
     * <p>
     * N C C X N<br>
     * N C C X N<br>
     * N X X X N<br>
     * N X B B N<br>
     * N X B B N<br>
     */
    @Test
    @DisplayName("Test squares with empty columns")
    public void testSquares3() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c == 0 || c == 4) {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                } else {
                    if ((r == 0 && c == 1) || (r == 1 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 2)) {
                        test.setSingleTile(new Tile(TileType.CAT), r, c);
                    } else if ((r == 3 && c == 2) || (r == 4 && c == 2) || (r == 3 && c == 3) || (r == 4 && c == 3)) {
                        test.setSingleTile(new Tile(TileType.BOOK), r, c);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                    }
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    /**
     * two adjacent groups of 2x2 tiles with the same type<br>
     * fourth common card<br>
     * <p>
     * X C C X X<br>
     * X C C X X<br>
     * X B B X X<br>
     * X B B X X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test squares 4")
    public void testSquares4() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((r == 0 && c == 1) || (r == 1 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 2)) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if ((r == 2 && c == 1) || (r == 3 && c == 1) || (r == 2 && c == 2) || (r == 3 && c == 2)) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                }
            }
        }
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    /**
     * 8 random occurrences of the same tile type<br>
     * sixth common card<br>
     * <p>
     * C C C C C<br>
     * C C C X X<br>
     * X X X X X<br>
     * X X X X X<br>
     * X X X X X<br>
     * X X X X X<br>
     */
    @Test
    @DisplayName("Test eight tiles 1")
    public void testEight1() {
        int count = 0;
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (count < 8)
                    test.setSingleTile(new Tile(TileType.USED), r, c);
                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), r, c);
                count++;
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonEight))
                .toList().get(0).verify(test));
    }

    /**
     * 8 random occurrences of the same tile type<br>
     * sixth common card<br>
     * <p>
     * N N N N N<br>
     * X X N N N<br>
     * X X C C X<br>
     * C X C C X<br>
     * X C X X X<br>
     * X X X C C<br>
     */
    @Test
    @DisplayName("Test eight tiles 2")
    public void testEight2() {


        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0) {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                } else {
                    if ((r == 3 && c == 0) || (r == 4 && c == 1) || (r == 2 && c == 2) || (r == 3 && c == 2) || (r == 2 && c == 3)
                        || (r == 3 && c == 3) || (r == 5 && c == 3) || (r == 5 && c == 4)) {
                        test.setSingleTile(new Tile(TileType.CAT), r, c);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), r, c);
                    }
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonEight))
                .toList().get(0).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c == 0 && r == 2)
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (c == 1 && r == 3)
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                else
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonFourGroups))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSixGroups))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonEight))
                .toList().get(0).verify(test));

    }

    @Test
    @DisplayName("Test with empty shelf")
    public void testEmptyShelf() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonFourGroups))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSixGroups))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
        assertFalse(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonEight))
                .toList().get(0).verify(test));
    }
}
