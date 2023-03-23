package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

public class CommonXCard extends CommonCard {

    public CommonXCard(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i < DefaultValue.NumOfRowsShelf - 2 && j < DefaultValue.NumOfColumnsShelf - 2) {   //check cross
                    if (!(toCheck.get(i,j).isSameType(TileType.NOT_USED)) &&
                            toCheck.get(i,j).isSameType(toCheck.get(i+2,j).getType()) &&
                            toCheck.get(i,j).isSameType(toCheck.get(i,j+2).getType()) &&
                            toCheck.get(i,j).isSameType(toCheck.get(i+1,j+1).getType()) &&
                            toCheck.get(i,j).isSameType(toCheck.get(i+2,j+2).getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
