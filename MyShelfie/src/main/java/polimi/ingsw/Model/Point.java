package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.CardGoalType;

public class Point {
    private Integer point;
    private CardGoalType referredTo;

    public Point(Integer point, CardGoalType referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public CardGoalType getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(CardGoalType referredTo) {
        this.referredTo = referredTo;
    }

}
