package polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.TileType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShelfTest {


    @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf1() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.CAT);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());

    }
    @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf2() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.PLANT);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());

    }
    @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf3() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.ACTIVITY);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());

    }
    @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf4() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.FRAME);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());

    }  @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf5() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.BOOK);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
        assertEquals(30, shelf.getFreeSpace());

    }  @Test
    @DisplayName("Test Shelf constructor + get shelf + get free space")
    void getShelf6() {
        Tile[][] tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                tiles[i][j] = new Tile(TileType.TROPHY);
            }
        }
        Shelf shelf = new Shelf(tiles, 30);
        shelf.toString();
        shelf.toString(0);
        shelf.toStringGoalCard();
        assertEquals(9, shelf.getShelf().length);
        assertEquals(9, shelf.getShelf()[0].length);
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
        assertEquals(TileType.CAT, shelf.get(DefaultValue.NumOfRowsShelf - 1, 0).getType());


        shelf.position(0, TileType.FRAME);
        assertEquals(TileType.FRAME, shelf.get(DefaultValue.NumOfRowsShelf - 2, 0).getType());

        shelf.position(0, TileType.TROPHY);
        assertEquals(TileType.TROPHY, shelf.get(DefaultValue.NumOfRowsShelf - 3, 0).getType());
    }
}