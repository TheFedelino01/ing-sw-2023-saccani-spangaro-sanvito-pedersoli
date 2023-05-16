package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

public class CommonSquares extends CommonMethods {
    public CommonSquares(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;

        while (!temp.isEmpty() && sum < 2) {
            for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                    if (!temp.get(r, c).isSameType(TileType.NOT_USED) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r, c + 1).getType()) &&
                        temp.get(r, c).isSameType(temp.get(r + 1, c + 1).getType())) {

                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 1);
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c + 1);

                        //sets the adjacent tiles to null, so that the cubes cannot be adjacent
                        if(c != DefaultValue.NumOfColumnsShelf - 2){
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 2);
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c + 2);
                        }

                        if(r != DefaultValue.NumOfRowsShelf - 2){
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 2, c);
                            temp.setSingleTile(new Tile(TileType.NOT_USED), r + 2, c + 1);
                        }
                        sum++;
                    }

                    if (c == DefaultValue.NumOfColumnsShelf - 2) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c + 1);
                    }

                    if (r == DefaultValue.NumOfRowsShelf - 2) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c);
                    }

                    if (r == DefaultValue.NumOfRowsShelf - 2 && c == DefaultValue.NumOfColumnsShelf - 2) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r + 1, c + 1);
                    }

                    temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                }

            }
        }
        return sum >= 2;
    }
}
