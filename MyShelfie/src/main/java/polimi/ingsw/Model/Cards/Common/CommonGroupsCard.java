package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class CommonGroupsCard extends CommonCard {

    private static int param;

    public CommonGroupsCard(CardCommonType type, int param) {
        super(type);
        CommonGroupsCard.param = param;
    }

    /**
     * Check if the player's shelf met the "adjacent" goal not in line
     * One card is adjacent to the other if one of the edges touches the other
     *
     * @return true if the goal is satisfied, false else
     */

    public boolean verifyIfxGroupsOfyTiles (Shelf toCheck, int x, int y){
        int check = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {  //check for groups of 4
                    adjacentToFU(toCheck, i, j, toCheck.get(i, j));
                    sum = countAdjacent(toCheck);
                    if (sum >= x) {
                        //delete all the same adjacent elements
                        deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                        check = check + 1;
                        if (check == y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    public boolean verifySquared (Shelf toCheck){
            for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //vertical check
            {
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analyse square
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED) &&
                                toCheck.get(i, j) == toCheck.get(i + 1, j) &&
                                toCheck.get(i, j) == toCheck.get(i, j + 1) &&
                                toCheck.get(i, j) == toCheck.get(i + 1, j + 1)) {
                            sum = sum + 1;
                            if (sum == 2) {
                                return true;
                            }
                            //delete all the same adjacent elements
                            deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                        }
                    }
                }
            }
            return false;
        }

    public boolean verify8 (Shelf toCheck){
            Map<TileType, Integer> tileCheck = new HashMap<>();
            for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {
                        if (tileCheck.get(toCheck.get(i, j).getType()) == null) {
                            tileCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                        } else {
                            tileCheck.put(toCheck.get(i, j).getType(), tileCheck.get(toCheck.get(i, j).getType()) + 1);
                        }
                    }
                }
            }
            for (TileType t : TileType.values()) {
                if (Optional.ofNullable(tileCheck.get(t)).orElse(0) >= 8) {
                    return true;
                }
            }
            return false;
        }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        switch (param) {
            case (0) -> {       //check if there are 6 separate groups formed by 2 adjacent tiles of the same type
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //check vertical
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1) {   //vertical analysis
                            if (!(toCheck.get(i, j).isSameType(TileType.NOT_USED)) && toCheck.get(i, j) == toCheck.get(i + 1, j)) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    return true;
                                }
                                //delete all the same adjacent elements
                                deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                            }
                        }
                        if (j < DefaultValue.NumOfColumnsShelf - 1) { //check horizontal
                            if (!(toCheck.get(i, j).isSameType(TileType.NOT_USED)) && toCheck.get(i, j) == toCheck.get(i, j + 1)) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    return true;
                                }
                                deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                            }
                        }
                    }
                }
                return false;
            }
            case (1) -> {       //check if there are 4 separate groups formed by 4 adjacent tiles of the same type
                int check = 0;
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {  //check for groups of 4
                            adjacentToFU(toCheck, i, j, toCheck.get(i, j));
                            sum = countAdjacent(toCheck);
                            if (sum >= 4) {
                                //delete all the same adjacent elements
                                deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                                check = check + 1;
                                if (check == 4) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            case (2) -> {       //check if there are 2 separate groups formed by 4 adjacent "squared" tiles of the same type
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //vertical check
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analyse square
                            if (!toCheck.get(i, j).isSameType(TileType.NOT_USED) &&
                                    toCheck.get(i, j) == toCheck.get(i + 1, j) &&
                                    toCheck.get(i, j) == toCheck.get(i, j + 1) &&
                                    toCheck.get(i, j) == toCheck.get(i + 1, j + 1)) {
                                sum = sum + 1;
                                if (sum == 2) {
                                    return true;
                                }
                                //delete all the same adjacent elements
                                deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                            }
                        }
                    }
                }
                return false;
            }
            case (3) -> {       //check if there are 8 tiles of the same type
                Map<TileType, Integer> tileCheck = new HashMap<>();
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {
                            if (tileCheck.get(toCheck.get(i, j).getType()) == null) {
                                tileCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                            } else {
                                tileCheck.put(toCheck.get(i, j).getType(), tileCheck.get(toCheck.get(i, j).getType()) + 1);
                            }
                        }
                    }
                }
                for (TileType t : TileType.values()) {
                    if (Optional.ofNullable(tileCheck.get(t)).orElse(0) >= 8) {
                        return true;
                    }
                }
                return false;
            }
            default -> {
                System.out.println("Default error");
                return false;
            }
        }
    }

    /**
     * Erase the adjacencies already counted
     *
     * @set the adjacent to FINISHED_USING
     */
    private static void deleteAdjacent(Shelf playerShelf, int i, int j, Tile tile) {

        if (checkIfSafe(playerShelf, i, j, tile)) {
            playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);      //delete
            deleteAdjacent(playerShelf, i - 1, j, tile); // up
            deleteAdjacent(playerShelf, i + 1, j, tile); // down
            deleteAdjacent(playerShelf, i, j - 1, tile); // sx
            deleteAdjacent(playerShelf, i, j + 1, tile); // dx
        }
    }

    private static void adjacentToFU(Shelf playerShelf, int i, int j, Tile tile) {     //useful for adjacent count(FU is finished_using)
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
    private static boolean checkIfSafe(Shelf playerShelf, int i, int j, Tile tile) {
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
    private static int countAdjacent(Shelf playerShelf) {
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
