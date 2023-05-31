package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.enumeration.CardCommonType;

import java.util.Queue;


public interface CommonCardIC {

    boolean verify(Shelf toCheck);

    String toString(int i);

    Queue<PointIC> getPoints_IC();

    CardCommonType getCommonType();

    boolean isSameType(Card c);

    boolean equals(CommonCard c);
}
