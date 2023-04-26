package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonFourGroups extends CommonMethods {

    public CommonFourGroups(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        int check = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {  //check for groups of 4
                    adjacentToFU(toCheck, i, j, toCheck.get(i, j));
                    sum = countAdjacent(toCheck);
                    if (sum >= 4) {
                        //delete all the same adjacent elements
                        deleteAdjacent(toCheck, i, j, toCheck.get(i, j));
                        check++;
                        if (check == 4) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
