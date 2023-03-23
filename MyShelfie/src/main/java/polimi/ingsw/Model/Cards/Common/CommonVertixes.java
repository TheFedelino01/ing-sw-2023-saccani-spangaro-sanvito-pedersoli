package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public class CommonVertixes extends CardCommon{

    public CommonVertixes(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        return !(toCheck.get(0, 0).isSameType(TileType.NOT_USED)) &&
                toCheck.get(0, 0) == toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1) &&
                toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1) == toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) &&
                toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) == toCheck.get(DefaultValue.NumOfRowsShelf - 1, 0);
    }
}
