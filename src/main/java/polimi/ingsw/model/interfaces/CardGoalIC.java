package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.enumeration.CardGoalType;

import java.util.Map;

/**
 * This interface is implemented by CardGoal
 */
public interface CardGoalIC {
    /**
     * This method is used to check if the player's shelf matches the layoutToMatch
     * @param toCheck player's shelf
     * @return the points gained
     */
    PointIC verify(Shelf toCheck);

    /**
     * This method is used to get the layout that the CardGoal need to match
     */
    ShelfIC getLayoutToMatch_IC();

    /**
     * This method is used to get the legend of the CardGoal
     * @return a map that contains the points gained for each number of match
     */
    Map<Integer, Point> getLegendPoint();

    /**
     * This method is used to get the type of the CardGoal
     * @return the type of the CardGoal
     */
    CardGoalType getGoalType();

    /**
     * This method is used to set the type of the CardGoal
     * @param c type of the CardGoal
     * @return true if the type is the same
     */
    boolean isSameType(Card c);

    /**
     * This method is used to get the string representation of the CardGoal
     * @param col number of the column
     * @return the string representation of the CardGoal
     */
    String toString(int col);

}
