package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonDiagonalTest {

    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    //I'll be using the CAT tile as the one to assert its value
    /*

     */
    @Test
    @DisplayName("Test Diagonal cards from top left to bottom right")
    public void testDiagonalTB() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i >= j) {
                    if (i == j) {
                        test.setSingleTile(new Tile(TileType.USED), i, j);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }
        assertTrue(model.get(6).verify(test));
        assertTrue(model.get(11).verify(test));
    }


    @Test
    @DisplayName("Test Diagonal cards from bottom left to top right")
    public void testDiagonalBT(){
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
        assertTrue(model.get(6).verify(test));
        assertTrue(model.get(11).verify(test));
    }

    //I copied methods from other test classes to initialise the shelf,
    //so that it cannot verify the diagonal condition
    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
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
        assertFalse(model.get(6).verify(test));
        assertFalse(model.get(11).verify(test));
    }
        @Test
    @DisplayName("Test with empty shelf")
    public void testEmptyShelf(){
        Shelf test = new Shelf();
        for(int i = 0; i<DefaultValue.NumOfRowsShelf; i++){
            for(int j = 0; j<DefaultValue.NumOfColumnsShelf; j++){
                test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(6).verify(test));
        assertFalse(model.get(11).verify(test));
    }

}
