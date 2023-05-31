package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

public class CommonStair extends CommonCard {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonStair(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the stair goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        int[] spaceCheck = {0, 0, 0, 0, 0};
        int checkSxToDx = 1, checkDxToSx = 1, checkSxToDx2 = 1, checkDxToSx2 = 1;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                if (toCheck.get(i, j).isSameType(TileType.NOT_USED))
                    spaceCheck[j]++;
            }
        }
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (spaceCheck[j] != sum) {
                checkSxToDx = 0;
            }
            sum++;
        }
        sum--;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (spaceCheck[j] != sum) {
                checkDxToSx = 0;
            }
            sum--;
        }
        sum += 2;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (spaceCheck[j] != sum) {
                checkSxToDx2 = 0;
            }
            sum++;
        }
        sum--;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (spaceCheck[j] != sum) {
                checkDxToSx2 = 0;
            }
            sum--;
        }
        return checkSxToDx != 0 || checkDxToSx != 0 || checkSxToDx2 != 0 || checkDxToSx2 != 0;
    }
}
