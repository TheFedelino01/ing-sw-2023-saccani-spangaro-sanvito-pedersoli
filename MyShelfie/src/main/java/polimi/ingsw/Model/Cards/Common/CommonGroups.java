package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import javax.naming.TimeLimitExceededException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CommonGroups extends CardCommon {

    private static int param;

    public CommonGroups(CardCommonType type, int param) {
        super(type);
        CommonGroups.param = param;
    }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        switch (param) {
            case (0) -> {
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
                        if (j < DefaultValue.NumOfColumnsShelf - 1) {
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
            case (1) -> {
                int check = 0;
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {  //check for groups of 4
                            adjacentToFU(toCheck, i, j, toCheck.get(i, j));
                            sum = countAdjacent(toCheck);
                            if (sum >= 4) {
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
            case (2) -> {
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //vertical check
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analyse 4
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
            case (3) -> {
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
        if (checkIfSafe(playerShelf, i, j, tile)){
            playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);     //finished using
            if(i>0)
                adjacentToFU(playerShelf, i - 1, j, tile); // up
            if(i<DefaultValue.NumOfRowsShelf-1)
                adjacentToFU(playerShelf, i + 1, j, tile); // down
            if(j>0)
                adjacentToFU(playerShelf, i, j - 1, tile); // sx
            if(j<DefaultValue.NumOfColumnsShelf-1)
                adjacentToFU(playerShelf, i, j + 1, tile); // dx
        }
    }

    private static boolean checkIfSafe(Shelf playerShelf, int i, int j, Tile tile) {
        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0
                || j >= DefaultValue.NumOfColumnsShelf) {  //check if out of bounds
            return false;
        }
        //check if different type is found
        try{
            return playerShelf.get(i, j).getType() == tile.getType();
        }catch(StackOverflowError er){
            er.printStackTrace();
            return false;
        }
    }

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
