package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.*;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;

import java.util.Arrays;

public class CommonFourGroups extends CommonMethods {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonFourGroups(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the four groups goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {
        Shelf temp = CommonMethods.getCopy(toCheck);
        int sum = 0;

        boolean allTilesFound;
        int occurrences;
        if (toCheck.getOccupiedSpace() < 16)
            return false;

        for (TileType t : TileType.getUsableValues()) {
            allTilesFound = false;
            while ((!allTilesFound) && (sum < 4)) {
                occurrences = CommonMethods.checkAdjacent(t, temp, 0, 0);
                if (occurrences > 3) {
                    sum++;
                }
                //checks whether all the tiles with tileType t have been checked
                allTilesFound = Arrays.stream(temp.getShelf())
                        .flatMap(Arrays::stream)
                        .noneMatch(x -> x.isSameType(t));
            }

        }

        return sum >= 4;
    }
}
