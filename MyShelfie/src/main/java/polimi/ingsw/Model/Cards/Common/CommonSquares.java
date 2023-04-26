package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonSquares extends CommonMethods {
    public CommonSquares(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //vertical check
        {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analyse square
                    if (!toCheck.get(i, j).isSameType(TileType.NOT_USED) &&
                            toCheck.get(i, j).isSameType(toCheck.get(i + 1, j).getType()) &&
                            toCheck.get(i, j).isSameType(toCheck.get(i, j + 1).getType()) &&
                            toCheck.get(i, j).isSameType(toCheck.get(i + 1, j + 1).getType())) {
                        sum++;
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
}
