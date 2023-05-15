package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public class CommonSixGroups extends CommonMethods {

    public CommonSixGroups(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;
        while (sum < 6 && !temp.isEmpty()) {
            for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                    if (!temp.get(r, c).isSameType(TileType.NOT_USED)) {
                        if (temp.get(r, c).isSameType(temp.get(r + 1, c).getType())) {
                            sum++;
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                        } else if (temp.get(r, c).isSameType(temp.get(r, c + 1).getType())) {
                            sum++;
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 1);
                        }
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                    }
                }
            }
        }
        return sum >= 6;
    }
}
