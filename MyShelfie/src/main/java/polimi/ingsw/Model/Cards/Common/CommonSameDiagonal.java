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
        boolean check1 = true, check2 = true;
        for (int i = 0; i < 2; i++) {
            check1 = true;
            check2 = true;
            for (int r = i; r < DefaultValue.NumOfRowsShelf - 2 + i; r++) {
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                    if ((!toCheck.get(r, c).isSameType(toCheck.get(r + 1, c + 1).getType()) || toCheck.get(r, c).isSameType(TileType.NOT_USED))&&r==c)
                        check1 = false;
                }
            }
            for (int r = DefaultValue.NumOfRowsShelf - 1 - i; r > 1 - i; r--) {
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                    if ((!toCheck.get(r, c).isSameType(toCheck.get(r - 1, c + 1).getType()) || toCheck.get(r, c).isSameType(TileType.NOT_USED))&&r==c)
                        check2 = false;
                }
            }
        }
        return check1 || check2;
    }
}
