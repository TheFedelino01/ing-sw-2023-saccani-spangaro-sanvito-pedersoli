package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Card;

public class Point {
    private Integer point;
    private Card referredTo;

    public Point(Integer point, Card referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Card getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(Card referredTo) {
        this.referredTo = referredTo;
    }
}
