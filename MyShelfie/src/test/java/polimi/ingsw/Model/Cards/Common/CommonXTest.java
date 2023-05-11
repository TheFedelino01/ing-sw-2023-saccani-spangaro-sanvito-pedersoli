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

public class CommonXTest {

    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    /*
    C X C X X
    X C X X X
    C X C X X
    X X X X X
    X X X X X
    X X X X X
     */
    @Test
    @DisplayName("First test X")
    public void firstTestX() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) ||
                        (i == 0 && j == 2) ||
                        (i == 2 && j == 0) ||
                        (i == 2 && j == 2) ||
                        (i == 1 && j == 1))
                    test.setSingleTile(new Tile(TileType.USED), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
            }
        }
        assertTrue(model.get(10).verify(test));
    }

    /*
    N N N N N
    N N N N N
    N N N N N
    X C X C X
    X X C X X
    X C X C X
     */
    @Test
    @DisplayName("Second test X")
    public void secondTestX() {
        Shelf test = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if ((r == 5 && c == 1) ||
                        (r == 5 && c == 3) ||
                        (r == 4 && c == 2) ||
                        (r == 3 && c == 3) ||
                        (r == 3 && c == 1))
                    test.setSingleTile(new Tile(TileType.USED), r, c);
                else if (r > 2)
                    test.setSingleTile(new Tile(TileType.randomTile()), r, c);
                else
                    test.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            }
        }
        assertTrue(model.get(10).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) ||
                        (i == 0 && j == 2) ||
                        (i == 2 && j == 0) ||
                        (i == 2 && j == 2))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(10).verify(test));
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
        assertFalse(model.get(10).verify(test));
    }
}
