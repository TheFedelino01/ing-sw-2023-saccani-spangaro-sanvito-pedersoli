package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonSixGroups extends CommonMethods{

    public CommonSixGroups(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //check vertical
        {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i < DefaultValue.NumOfRowsShelf - 1) {   //vertical analysis
                    if (!(toCheck.get(i, j).isSameType(TileType.NOT_USED)) && toCheck.get(i, j) == toCheck.get(i + 1, j)) {
                        sum++;
                        if (sum == 6) {
                            return true;
                        }
                        //delete all the same adjacent elements
                        deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                    }
                }
                if (j < DefaultValue.NumOfColumnsShelf - 1) { //check horizontal
                    if (!(toCheck.get(i, j).isSameType(TileType.NOT_USED)) && toCheck.get(i, j) == toCheck.get(i, j + 1)) {
                        sum++;
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
}
