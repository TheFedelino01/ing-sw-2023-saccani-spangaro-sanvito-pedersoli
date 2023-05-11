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

public class CommonVerticalTest {
    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    @Test
    @DisplayName("First test Vertical 0")
    public void firstTestVertical0() {
        /*
    C X B X T
    C X B X T
    C X B X T
    C X B X T
    C X B X T
    C X B X T
     */
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                } else if (j == 2) {

                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                } else if (j == 4) {
                    test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                }
            }
        }
        assertTrue(model.get(4).verify(test));
    }

    @Test
    @DisplayName("Second test Vertical 0")
    public void secondTestVertical0() {
        /*
    N C B N T
    N C B N T
    N C B X T
    N C B X T
    N C B X T
    N C B X T
     */
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c == 1) {
                    test.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if (c == 2) {
                    test.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else if (c == 4) {
                    test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                } else if (c == 0) {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                } else if (r > 1) {
                    test.setSingleTile(new Tile(TileType.randomTileCATeBOOK()), r, c);
                } else {
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                }
            }
        }
        assertTrue(model.get(4).verify(test));
    }

    @Test
    @DisplayName("First test Vertical 1")
    public void firstTestVertical1() {

        /*
        C X C X X
        B X B X X
        F X F X X
        T X T X X
        A X A X X
        P X P X X
         */
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0 || j == 2) {
                    switch (i) {
                        case (0) -> test.setSingleTile(new Tile(TileType.USED), i, j);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), i, j);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), i, j);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), i, j);
                        case (5) -> test.setSingleTile(new Tile(TileType.PLANT), i, j);
                        default -> {
                        }
                    }
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                }
            }
        }
        assertTrue(model.get(8).verify(test));
    }

    /*
        N N C C N
        N N B B N
        N X F F X
        N X T T X
        N X A A X
        N X P P X
         */
    @Test
    @DisplayName("Second test Vertical 1")
    public void secondTestVertical1() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (c == 3 || c == 2) {
                    switch (r) {
                        case (0) -> test.setSingleTile(new Tile(TileType.USED), r, c);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), r, c);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), r, c);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), r, c);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), r, c);
                        case (5) -> test.setSingleTile(new Tile(TileType.PLANT), r, c);
                        default -> {
                        }
                    }
                } else if (c > 0 && r > 1) {
                    test.setSingleTile(new Tile(TileType.randomTile()), r, c);
                } else test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertTrue(model.get(8).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i == 0 || i == 3) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                } else if (i == 1) {
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                } else if (i == 2) {
                    test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                } else {
                    test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
                }
            }
        }
        assertFalse(model.get(4).verify(test));
        assertFalse(model.get(8).verify(test));
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
        assertFalse(model.get(4).verify(test));
        assertFalse(model.get(8).verify(test));
    }


}
