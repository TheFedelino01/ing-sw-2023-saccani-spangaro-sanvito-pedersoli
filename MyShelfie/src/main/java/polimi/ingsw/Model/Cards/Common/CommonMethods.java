package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public abstract class CommonMethods extends CommonCard {

    @Override
    public abstract boolean verify(Shelf toCheck);

    public CommonMethods(CardCommonType type) {
        super(type);
    }

    static Shelf getCopy(Shelf toCopy){
        Shelf temp = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                temp.setSingleTile(new Tile(toCopy.get(r, c).getType()), r, c);
            }
        }
        return temp;
    }

}
