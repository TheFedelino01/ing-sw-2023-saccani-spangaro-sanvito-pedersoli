package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

public class CommonSquares extends CommonMethods {
    public CommonSquares(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;

        if (toCheck.getOccupiedSpace() < 8)
            return false;

        for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                if (!temp.get(r, c).isSameType(TileType.NOT_USED) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 1).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c + 1).getType())) {

                    if (CommonMethods.checkAdjacent(temp.get(r, c).getType(), temp, r, c) == 4)
                        sum++;
                    CommonMethods.deleteAdjacent(temp.get(r, c).getType(), temp, r, c);
                }
            }

        }
        return sum >= 2;
    }
}
