package polimi.ingsw.model.interfaces;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.enumeration.CardCommonType;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import static org.fusesource.jansi.Ansi.ansi;

public interface CommonCardIC {

    public abstract boolean verify(Shelf toCheck);

    public String toString(int i);

    public Queue<PointIC> getPoints_IC();

    public CardCommonType getCommonType();

    public boolean isSameType(Card c);

    public boolean equals(CommonCard c);
}
