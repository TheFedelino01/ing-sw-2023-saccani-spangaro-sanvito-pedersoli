package polimi.ingsw.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.TileType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShelfTest {


    @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf() {
        Shelf shelf = new Shelf();
        assertEquals(6, shelf.getShelf().length);
        assertEquals(5, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());
    }

    @Test
    @DisplayName("Test set shelf")
    void setShelf() {
        Shelf shelf = new Shelf();
        Tile[][] tiles = new Tile[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(TileType.NOT_USED);
            }
        }
        shelf.setShelf(tiles);
        assertEquals(tiles, shelf.getShelf());
    }



    @Test
    @DisplayName("Test Shelf get")
    void get() {
        Shelf shelf = new Shelf();
        Tile[][] tiles = new Tile[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(TileType.NOT_USED);
            }
        }
        shelf.setShelf(tiles);
        assertEquals(tiles[0][0], shelf.get(0, 0));
    }

    @Test
    @DisplayName("Test Shelf positioning")
    void position() {
        Shelf shelf = new Shelf();
        shelf.setShelf(new Tile[6][5]);
        shelf.position(0, TileType.CAT);
        assertEquals(TileType.CAT, shelf.get(DefaultValue.NumOfRowsShelf-1, 0).getType());


        shelf.position(0, TileType.FRAME);
        assertEquals(TileType.FRAME, shelf.get(DefaultValue.NumOfRowsShelf-2, 0).getType());

        shelf.position(0, TileType.TROPHY);
        assertEquals(TileType.TROPHY, shelf.get(DefaultValue.NumOfRowsShelf-3, 0).getType());
    }
}