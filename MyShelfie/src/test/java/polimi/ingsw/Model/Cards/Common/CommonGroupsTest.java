package polimi.ingsw.Model.Cards.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Common.CommonCard;
import polimi.ingsw.Model.Common.CommonCardFactory;
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
    @DisplayName("Test Group 0")
    public void testGroup0() {
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
        count = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (count < 2) {
                    if (j == 0 && i < 2) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else if (j == 0 && i > 3) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {

                        test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                    }
                    count++;
                } else if (count < 4) {
                    if (j == 1 && i < 2) {
                        test.setSingleTile(new Tile(TileType.BOOK), i, j);
                    } else if (j == 1 && i > 3) {
                        test.setSingleTile(new Tile(TileType.BOOK), i, j);

                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                    }
                    count++;
                } else if (count < 6) {
                    if (j == 2 && i < 2) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else if (j == 2 && i > 3) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {

                        test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                    }
                    count++;
                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                }
            }
        }

        //check this algorithm
        assertTrue(model.get(0).verify(test));
    }

    @Test
    @DisplayName("Test Group 1")
    public void testGroup1() {
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
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j < 4 && j % 2 == 0 && i < 4) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                } else if (j < 4 && i < 4) {
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);

                } else {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), i, j);
                }
            }
        }
        assertTrue(model.get(2).verify(test));
    }

    @Test
    @DisplayName("Test Group 2")
    public void testGroup2() {
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
        assertTrue(model.get(3).verify(test));
    }

    @Test
    @DisplayName("Test Group 3")
    public void testGroup3() {
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
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (count < 8)
                    test.setSingleTile(new Tile(TileType.USED), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                count++;
            }
        }
        assertTrue(model.get(5).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0 && i == 2)
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else if (j == 1 && i == 3)
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                else
                    test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(0).verify(test));
        assertFalse(model.get(2).verify(test));
        assertFalse(model.get(3).verify(test));
        assertFalse(model.get(5).verify(test));

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
        assertFalse(model.get(0).verify(test));
        assertFalse(model.get(2).verify(test));
        assertFalse(model.get(3).verify(test));
        assertFalse(model.get(5).verify(test));
    }
}
