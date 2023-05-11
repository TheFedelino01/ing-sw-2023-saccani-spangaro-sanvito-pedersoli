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

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    @Test
    @DisplayName("Test six groups")
    public void testSixGroups() {
        int count;
        Shelf test = new Shelf();
        //case 1
        //first common card
        /*
        C B C B X
        C B C B X
        X X X X X
        C B C B X
        C B C B X
        X X X X X
         */
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

    @Test
    @DisplayName("Test four groups")
    public void testFourGroups() {
        //four groups of four tiles of the same type (will be testing two columns in this test case)
        //third common card
        /*
        C B C B X
        C B C B X
        C B C B X
        C B C B X
        X X X X X
        X X X X X
         */

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

    @Test
    @DisplayName("Test squares 1")
    public void testSquares1() {
        //two separated groups of 2x2 tiles with the same type
        //fourth common card
        /*
        C C X X X
        C C X X X
        X X X X X
        X X X C C
        X X X C C
         */
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) || (i == 1 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1)) {
                    test.setSingleTile(new Tile(TileType.USED), i, j);
                } else if ((i == 3 && j == 3) || (i == 4 && j == 3) || (i == 3 && j == 4) || (i == 4 && j == 4)) {
                    test.setSingleTile(new Tile(TileType.USED), i, j);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    @Test
    @DisplayName("Test squares 2")
    public void testSquares2() {
        //two separated groups of 2x2 tiles with the same type
        //fourth common card
        /*
        X C C X X
        X C C X X
        X X X X X
        X X C C X
        X X C C X
         */
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((r == 0 && c == 1) || (r == 1 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 2)) {
                    test.setSingleTile(new Tile(TileType.USED), r, c);
                } else if ((r == 3 && c == 2) || (r == 4 && c == 2) || (r == 3 && c == 3) || (r == 4 && c == 3)) {
                    test.setSingleTile(new Tile(TileType.USED), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), r, c);
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    @Test
    @DisplayName("Test squares with empty columns")
    public void testSquares3() {
        //two separated groups of 2x2 tiles with the same type
        //fourth common card
        /*
        N C C X N
        N C C X N
        N X X X N
        N X C C N
        N X C C N
         */
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c == 0 || c == 4) {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                } else {
                    if ((r == 0 && c == 1) || (r == 1 && c == 1) || (r == 0 && c == 2) || (r == 1 && c == 2)) {
                        test.setSingleTile(new Tile(TileType.USED), r, c);
                    } else if ((r == 3 && c == 2) || (r == 4 && c == 2) || (r == 3 && c == 3) || (r == 4 && c == 3)) {
                        test.setSingleTile(new Tile(TileType.USED), r, c);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), r, c);
                    }
                }
            }
        }
        assertTrue(model.stream()
                .filter(x -> x.getCommonType().equals(CardCommonType.CommonSquares))
                .toList().get(0).verify(test));
    }

    @Test
    @DisplayName("Test eight tiles 1")
    public void testEight1() {
        //8 random occurrences of the same tile type
        //sixth common card
        /*
        C C C C C
        C C C X X
        X X X X X
        X X X X X
        X X X X X
        X X X X X
         */
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

    @Test
    @DisplayName("Test eight tiles 2")
    public void testEight2() {
        //8 random occurrences of the same tile type
        //sixth common card
        /*
        N N N N N
        X X N N N
        X X C C X
        C X C C X
        X C X X X
        X X X C C
         */

        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if(r==0){
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                }else{
                    if((r == 3 && c == 0)||(r == 4 && c == 1)||(r == 2 && c == 2)||(r == 3 && c == 2)||(r == 2 && c == 3)
                            ||(r == 3 && c == 3)||(r == 5 && c == 3)||(r == 5 && c == 4)){
                        test.setSingleTile(new Tile(TileType.CAT), r, c);
                    }else{
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
