package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.Objects;

public abstract class CommonMethods extends CommonCard {

    @Override
    public abstract boolean verify(Shelf toCheck);

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
     * Check if the indexes meet the shelf number of rows and columns
     * Check if a different type of tile is found
     *
     * @return false if the limits are exceeded or is found a different tile type
     */
    static boolean checkIfSafe(Shelf playerShelf, int i, int j, Tile tile) {
        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0
                || j >= DefaultValue.NumOfColumnsShelf) {  //check if out of bounds
            return false;
        }
        if (tile.isSameType(TileType.FINISHED_USING))
            return false;
        return playerShelf.get(i, j).getType().equals(tile.getType());
    }

    /**
     * Count the number of adjacent tiles of the same type
     *
     * @return res = number of adjacent tiles
     */
    static int countAdjacent(Shelf playerShelf) {
        int res = 0;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (playerShelf.get(r, c).isSameType(TileType.FINISHED_USING)) {
                    res++;
                }
            }
        }
        return res;
    }
}
