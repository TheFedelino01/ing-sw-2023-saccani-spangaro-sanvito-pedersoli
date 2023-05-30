package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.CardType;
import polimi.ingsw.model.interfaces.PointIC;

import java.io.Serializable;

public class Point implements Serializable, PointIC {
    private Integer point;
    private CardType referredTo;

    public Point(Integer point, CardType referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    public Point(Integer point) {
        this.point = point;
        this.referredTo = CardGoalType.NOT_SET;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public CardType getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(CardGoalType referredTo) {
        this.referredTo = referredTo;
    }

}
