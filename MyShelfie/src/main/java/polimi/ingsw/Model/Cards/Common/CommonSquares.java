package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.Arrays;
import java.util.List;

public class CommonSquares extends CommonMethods {
    public CommonSquares(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Integer row = null, col = null;
        Shelf temp = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                temp.setSingleTile(new Tile(toCheck.get(r, c).getType()), r, c);
            }
        }
        int sum = 0;
        for (TileType t : TileType.getUsableValues()) {
            for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                    if (temp.get(r, c).isSameType(temp.get(r + 1, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 1).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c + 1).getType()) &&
                        !temp.get(r, c).isSameType(TileType.NOT_USED) && !temp.get(r, c).isSameType(TileType.FINISHED_USING)) {

                        if (sum == 1) {
                            if(r != row && c != col){
                                return true;
                            }
                        } else {
                            row = r + 2;
                            col = c + 2;
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 1);
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c + 1);
                            sum++;
                        }
                    }
                }
            }
        }
        return false;
    }
}
