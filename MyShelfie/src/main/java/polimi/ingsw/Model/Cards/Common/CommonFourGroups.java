package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public class CommonFourGroups extends CommonMethods {

    public CommonFourGroups(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;

        //checks if there are occurrences in line (vertically)
        for (int r = 0; r < DefaultValue.NumOfRowsShelf - 3; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (!temp.get(r, c).isSameType(TileType.NOT_USED)) {
                    if (temp.get(r, c).isSameType(temp.get(r + 1, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 2, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 3, c).getType())) {
                        sum++;
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 2, c);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 3, c);
                    }
                }
            }
        }

        //checks if there are occurrences in line (horizontally)
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 3; c++) {
                if (!temp.get(r, c).isSameType(TileType.NOT_USED)) {
                    if (temp.get(r, c).isSameType(temp.get(r, c + 1).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 2).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 3).getType())) {
                        sum++;
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 1);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 2);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 3);
                    }
                }
            }
        }
        //checks if there are occurrences in squares
        for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                if (!temp.get(r, c).isSameType(TileType.NOT_USED)) {
                    if (temp.get(r, c).isSameType(temp.get(r + 1, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 1).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c + 1).getType())) {
                        sum++;
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                    }
                }
            }
        }
        return sum >= 4;
    }
}
