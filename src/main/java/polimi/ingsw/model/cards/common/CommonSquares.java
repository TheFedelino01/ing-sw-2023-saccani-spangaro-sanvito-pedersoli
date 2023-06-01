package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

public class CommonSquares extends CommonMethods {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonSquares(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the squares goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;

        if (toCheck.getOccupiedSpace() < 8)
            return false;

        for (int r = 0; r < DefaultValue.NumOfRowsShelf - 1; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf - 1; c++) {
                if (!temp.getSingleTile(r, c).isSameType(TileType.NOT_USED) &&
                    temp.getSingleTile(r, c).isSameType(temp.getSingleTile(r + 1, c).getType()) &&
                    temp.getSingleTile(r, c).isSameType(temp.getSingleTile(r, c + 1).getType()) &&
                    temp.getSingleTile(r, c).isSameType(temp.getSingleTile(r + 1, c + 1).getType())) {

                    if (CommonMethods.checkAdjacent(temp.getSingleTile(r, c).getType(), temp, r, c) == 4)
                        sum++;
                    CommonMethods.deleteAdjacent(temp.getSingleTile(r, c).getType(), temp, r, c);
                }
            }

        }
        return sum >= 2;
    }
}
