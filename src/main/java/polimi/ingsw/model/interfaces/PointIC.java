package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.CardType;

/**
 * This interface is implemented by Point
 */
public interface PointIC {
    /**
     * This method is used to get the point
     * @return the point
     */
    Integer getPoint();

    /**
     * This method is used to get the card type this point is referred to
     * @return the card type this point is referred to {@link CardType}
     */
    CardType getReferredTo();

    /**
     * @return true if it is for the last point
     */
    boolean isFinishedPoint();
}
