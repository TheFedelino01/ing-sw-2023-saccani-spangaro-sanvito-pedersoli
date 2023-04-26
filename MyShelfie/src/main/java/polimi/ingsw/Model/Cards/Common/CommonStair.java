package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonStair extends CommonCard {
    public CommonStair(CardCommonType type) {
        super(type);
    }

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
