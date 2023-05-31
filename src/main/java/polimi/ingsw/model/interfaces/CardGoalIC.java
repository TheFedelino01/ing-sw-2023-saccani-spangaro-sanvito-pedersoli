package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.enumeration.CardGoalType;

import java.util.Map;

public interface CardGoalIC {
    PointIC verify(Shelf toCheck);

    ShelfIC getLayoutToMatch_IC();

    Map<Integer, Point> getLegendPoint();

    CardGoalType getGoalType();

    boolean isSameType(Card c);

    String toString(int col);

}
