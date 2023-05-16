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

public class CommonXTest {

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
    C X C X X<br>
    X C X X X<br>
    C X C X X<br>
    X X X X X<br>
    X X X X X<br>
    X X X X X<br>
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
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
            }
        }
        assertTrue(model.get(10).verify(test));
    }

    /**
    N N N N N<br>
    N N N N N<br>
    N N N N N<br>
    X C X C X<br>
    X X C X X<br>
    X C X C X<br>
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
