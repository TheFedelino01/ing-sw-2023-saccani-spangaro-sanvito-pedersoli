package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonSameDiagonal extends CommonCard {
    public CommonSameDiagonal(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        boolean check1, check2;
        for (int i = 0; i < 2; i++) {
            check1 = true;
            check2 = true;

            //amazing for loop, checks for occurrences in diagonal going from top to bottom
            for (int r = i, c = 0; r < DefaultValue.NumOfRowsShelf - 2 + i && check1 && c < DefaultValue.NumOfColumnsShelf - 1; r++, c++) {
                if ((!toCheck.get(r, c).isSameType(toCheck.get(r + 1, c + 1).getType()) || toCheck.get(r, c).isSameType(TileType.NOT_USED))) {
                    check1 = false;
                }
            }

            for (int r = DefaultValue.NumOfRowsShelf - 2 + i, c = 0; r > i && c < DefaultValue.NumOfColumnsShelf; r--, c++) {
                if (!toCheck.get(r, c).isSameType(toCheck.get(r - 1, c + 1).getType()) || toCheck.get(r, c).isSameType(TileType.NOT_USED)) {
                    check2 = false;
                }
            }

            if (check1 || check2)
                return true;
        }
        return false;
    }
}
