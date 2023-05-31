package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

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
        System.out.println(toCheck.getOccupiedSpace());
        if (toCheck.getOccupiedSpace() < 5)
            return false;

        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i < DefaultValue.NumOfRowsShelf - 2 && j < DefaultValue.NumOfColumnsShelf - 2) {   //check cross
                    if (!(toCheck.get(i, j).isSameType(TileType.NOT_USED)) &&
                        toCheck.get(i, j).isSameType(toCheck.get(i + 2, j).getType()) &&
                        toCheck.get(i, j).isSameType(toCheck.get(i, j + 2).getType()) &&
                        toCheck.get(i, j).isSameType(toCheck.get(i + 1, j + 1).getType()) &&
                        toCheck.get(i, j).isSameType(toCheck.get(i + 2, j + 2).getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
