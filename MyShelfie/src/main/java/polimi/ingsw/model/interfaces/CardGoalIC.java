package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.TileType;

import java.util.Map;

public interface CardGoalIC {
    public PointIC verify(Shelf toCheck);

    public ShelfIC getLayoutToMatch_IC();

    public Map<Integer, Point> getLegendPoint();

    public CardGoalType getGoalType();

    public boolean isSameType(Card c);

    public String toString(int col);

}
