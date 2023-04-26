package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonSameDiagonal extends CommonCard{
    public CommonSameDiagonal(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        for (int i = 0; i < 2; i++) {
            int check1 = 1;
            int check2 = 1;
            for (int k = i, j = 0; k < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1; k++, j++) {     //controllo sx dx
                if (toCheck.get(k, j).isSameType(TileType.NOT_USED) || toCheck.get(k, j) != toCheck.get(k + 1, j + 1))
                    check1 = 0;
            }
            for (int k = i, j = DefaultValue.NumOfColumnsShelf - 1; k < DefaultValue.NumOfRowsShelf - 1 && j > 0; k++, j--) {     //controllo dx sx
                if (toCheck.get(k, j).isSameType(TileType.NOT_USED) || toCheck.get(k, j) != toCheck.get(k + 1, j - 1))
                    check2 = 0;
            }
            if (check1 == 1 || check2 == 1) {
                return true;
            }
        }
        return false;
    }
}
