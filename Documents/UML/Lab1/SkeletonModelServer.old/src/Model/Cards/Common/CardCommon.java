package Model.Cards.Common;

import Model.Cards.Card;
import Model.Enumeration.CardCommonType;
import Model.Point;
import Model.Shelf;

import java.util.Queue;

public class CardCommon extends Card {
    private Queue<Point> points;
    private CardCommonType commonType;

    public CardCommon(Queue<Point> points, CardCommonType commonType) {
        this.points = points;
        this.commonType = commonType;
    }

    public boolean verify(Shelf shelfToCheck){
        return false;
    }

    public Queue<Point> getPoints() {
        return points;
    }

    public void setPoints(Queue<Point> points) {
        this.points = points;
    }

    public CardCommonType getCommonType() {
        return commonType;
    }

    public void setCommonType(CardCommonType commonType) {
        this.commonType = commonType;
    }
}
