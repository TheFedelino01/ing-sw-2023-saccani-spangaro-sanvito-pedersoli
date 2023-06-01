package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

public class CommonVertexesCard extends CommonCard {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonVertexesCard(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the corner goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {      //4 tiles of the same type at the corners of the shelf
        return !(toCheck.getSingleTile(0, 0).isSameType(TileType.NOT_USED)) &&
               toCheck.getSingleTile(0, 0)
                       .isSameType(toCheck.getSingleTile(0, DefaultValue.NumOfColumnsShelf - 1).getType()) &&
               toCheck.getSingleTile(0, DefaultValue.NumOfColumnsShelf - 1)
                       .isSameType(toCheck.getSingleTile(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1).getType()) &&
               toCheck.getSingleTile(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1)
                       .isSameType(toCheck.getSingleTile(DefaultValue.NumOfRowsShelf - 1, 0).getType());
    }
}
