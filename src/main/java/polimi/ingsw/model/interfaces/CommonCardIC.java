package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.enumeration.CardCommonType;

import java.util.Queue;

/**
 * This interface is implemented by CardCommon
 */
public interface CommonCardIC {

    /**
     * This method is used to verify if the player's shelf matches the layoutToMatch
     * @param toCheck player's shelf
     * @return true if the player's shelf matches the layoutToMatch
     */
    boolean verify(Shelf toCheck);

    /**
     * To string implementation
     * @param i spacer
     * @return string representation of the card
     */
    String toString(int i);

    /**
     * This method is used to get the points of the CardCommon
     * @return the points of the CardCommon
     */
    Queue<PointIC> getPoints_IC();

    /**
     * This method is used to get the type of the CardCommon
     * @return the type of the CardCommon
     */
    CardCommonType getCommonType();

    /**
     * This method check if the type of the card is the same
     * @param c card to check
     * @return true if the type is the same
     */
    boolean isSameType(Card c);

    /**
     * This method check if the card is the same
     * @param c card to check
     * @return true if the card is the same
     */
    boolean equals(CommonCard c);
}
