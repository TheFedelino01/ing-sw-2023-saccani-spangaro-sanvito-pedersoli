package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Shelf;

public class CommonVertixesCard extends CommonCard {

    public CommonVertixesCard(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        return toCheck.get(0, 0)
                        .isSameType(toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1).getType()) &&
                toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1)
                        .isSameType(toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1).getType()) &&
                toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1)
                        .isSameType(toCheck.get(DefaultValue.NumOfRowsShelf - 1, 0).getType());
    }
}
