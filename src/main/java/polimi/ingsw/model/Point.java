package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.CardType;
import polimi.ingsw.model.interfaces.PointIC;

import java.io.Serializable;

/**
 * Point class
 * Each card has a list of points that can only be assigned once to each player
 * this class implements the Point object
 */
public class Point implements Serializable, PointIC {
    private Integer point;
    private CardType referredTo;

    /**
     * Constructor
     *
     * @param point
     * @param referredTo
     */
    public Point(Integer point, CardType referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    /**
     * Constructor
     *
     * @param point
     */
    public Point(Integer point) {
        this.point = point;
        this.referredTo = CardGoalType.NOT_SET;
    }

    /**
     * @return the point obj
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point init point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return the card type this point id referred to
     */
    public CardType getReferredTo() {
        return referredTo;
    }

    /**
     * @param referredTo sets the point to a said card type
     */
    public void setReferredTo(CardGoalType referredTo) {
        this.referredTo = referredTo;
    }

}
