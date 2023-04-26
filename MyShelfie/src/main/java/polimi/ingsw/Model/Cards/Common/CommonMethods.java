package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.Objects;

public abstract class CommonMethods extends CommonCard {
    public CommonMethods(CardCommonType type) {
        super(type);
    }

    /**
     * Erase the adjacency already counted
     *
     * @set the adjacent to FINISHED_USING
     */
    static void deleteAdjacent(Shelf playerShelf, int i, int j, Tile tile) {

        if (checkIfSafe(playerShelf, i, j, tile)) {
            playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);      //delete
            deleteAdjacent(playerShelf, i - 1, j, tile); // up
            deleteAdjacent(playerShelf, i + 1, j, tile); // down
            deleteAdjacent(playerShelf, i, j - 1, tile); // sx
            deleteAdjacent(playerShelf, i, j + 1, tile); // dx
        }
    }

    static void adjacentToFU(Shelf playerShelf, int i, int j, Tile tile) {     //useful for adjacent count(FU is finished_using)
        if (checkIfSafe(playerShelf, i, j, tile)) {
            playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);     //finished using
            if (i > 0)
                adjacentToFU(playerShelf, i - 1, j, tile); // up
            if (i < DefaultValue.NumOfRowsShelf - 1)
                adjacentToFU(playerShelf, i + 1, j, tile); // down
            if (j > 0)
                adjacentToFU(playerShelf, i, j - 1, tile); // sx
            if (j < DefaultValue.NumOfColumnsShelf - 1)
                adjacentToFU(playerShelf, i, j + 1, tile); // dx
        }
    }

    /**
     * Check if the indices meet the shelf number of rows and columns
     * Check if a different type of tile is found
     *
     * @return false if the limits are exceeded or is found a different type, the type of the tile else
     */
    static boolean checkIfSafe(Shelf playerShelf, int i, int j, Tile tile) {
        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0
                || j >= DefaultValue.NumOfColumnsShelf) {  //check if out of bounds
            return false;
        }
        //check if different type is found
        try {
            return playerShelf.get(i, j).getType() == tile.getType();
        } catch (StackOverflowError er) {
            er.printStackTrace();
            return false;
        }
    }

    /**
     * Count the number of adjacent tiles of the same type
     *
     * @return res = number of adjacent tiles
     */
    static int countAdjacent(Shelf playerShelf) {
        int res = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (Objects.equals(playerShelf.get(i, j), new Tile(TileType.FINISHED_USING))) {
                    res = res + 1;
                }
            }
        }
        return res;
    }
}
