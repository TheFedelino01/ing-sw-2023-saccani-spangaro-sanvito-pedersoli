package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

import static polimi.ingsw.networking.PrintAsync.printAsync;


public class CommonXCard extends CommonCard {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonXCard(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the "X" goal
     * X is the visual arrangement of tiles of the same type
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {
        printAsync(toCheck.getOccupiedSpace().toString());
        if (toCheck.getOccupiedSpace() < 5)
            return false;

        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i < DefaultValue.NumOfRowsShelf - 2 && j < DefaultValue.NumOfColumnsShelf - 2) {   //check cross
                    if (!(toCheck.getSingleTile(i, j).isSameType(TileType.NOT_USED)) &&
                        toCheck.getSingleTile(i, j).isSameType(toCheck.getSingleTile(i + 2, j).getType()) &&
                        toCheck.getSingleTile(i, j).isSameType(toCheck.getSingleTile(i, j + 2).getType()) &&
                        toCheck.getSingleTile(i, j).isSameType(toCheck.getSingleTile(i + 1, j + 1).getType()) &&
                        toCheck.getSingleTile(i, j).isSameType(toCheck.getSingleTile(i + 2, j + 2).getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
