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

public class CommonVertexesTest {

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
    C X X X C<br>
    X X X X X<br>
    X X X X X<br>
    X X X X X<br>
    X X X X X<br>
    C X X X C<br>
     */
    @Test
    @DisplayName("Test vertexes")
    public void testVertexes() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) ||
                        (i == DefaultValue.NumOfRowsShelf - 1 && j == DefaultValue.NumOfColumnsShelf - 1) ||
                        (i == 0 && j == DefaultValue.NumOfColumnsShelf - 1) ||
                        (i == DefaultValue.NumOfRowsShelf - 1 && j == 0))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);

                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
            }
        }
        assertTrue(model.get(1).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) ||
                        (i == DefaultValue.NumOfRowsShelf - 1 && j == DefaultValue.NumOfColumnsShelf - 1) ||
                        (i == 0 && j == DefaultValue.NumOfColumnsShelf - 1))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
            }
        }
        assertFalse(model.get(1).verify(test));
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
        assertFalse(model.get(1).verify(test));
    }
}
