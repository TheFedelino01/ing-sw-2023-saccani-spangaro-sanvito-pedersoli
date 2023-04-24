package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonVerticalCard extends CommonCard {
    private static int param;
    //private static int numOfTiles;
    //private static int numOfColumns;

    public CommonVerticalCard(CardCommonType type, int param) {
        super(type);
        CommonVerticalCard.param = param;
        //CommonVerticalCard.numOfTiles = numOfColumns;
        //CommonVerticalCard.numOfColumns = numOfColumns;
    }

    /**
     * Check if the player's shelf met the vertical goals
     *
     * @return true if the goal is satisfied, false else
     */

    /*@Override
    public boolean verify(Shelf toCheck) {
        int sum=0;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            int ok = checkLines(toCheck, j);
            if(numOfTiles==6){
                if (ok == numOfTiles)    //num of different tiles type
                    sum++;
            }
            else {
                if (ok <= numOfTiles)
                    sum++;
            }
            if (sum == numOfColumns) {     //num of different columns
                return true;
            }
        }
        return false;
    }*/
    @Override
    public boolean verify(Shelf toCheck) {
        param = super.getCommonType().compareTo(CardCommonType.CommonVertical0) > 0 ? 1 : 0;
        int sum = 0;
        switch (param) {
            case (0) -> {       //3 columns each consisting of 6 tiles of at most 3 different types
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    int ok = checkLines(toCheck, j);
                    if (ok <= 3)
                        sum++;
                    if (sum == 3) {
                        return true;
                    }
                }
                return false;
            }
            case (1) -> {       //2 columns each of 6 different types of tiles
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    int ok = checkLines(toCheck, j);
                    if (ok == 6)
                        sum++;
                    if (sum == 2) {
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

    private int checkLines(Shelf toCheck, int j) {
        Map<TileType, Integer> colCheck = new HashMap<>();
        int ok = 0;
        int count = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            if (!toCheck.get(i, j).isSameType(TileType.NOT_USED))
                colCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
            else
                count++;
        }
        for (TileType t : TileType.values()) {
            ok = ok + Optional.
                    ofNullable(colCheck.get(t)).
                    orElse(0);
        }
        if (count > 0)
            ok = 4;
        return ok;
    }

}
