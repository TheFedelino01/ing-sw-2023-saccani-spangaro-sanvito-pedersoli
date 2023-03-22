package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public class CommonVertixes extends CardCommon{

    private final static Tile controller = new Tile(TileType.NOT_USED);

    public CommonVertixes(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        return toCheck.get(0, 0) != controller &&
                toCheck.get(0, 0) == toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1) &&
                toCheck.get(0, DefaultValue.NumOfColumnsShelf - 1) == toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) &&
                toCheck.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) == toCheck.get(DefaultValue.NumOfRowsShelf - 1, 0);
    }
}
