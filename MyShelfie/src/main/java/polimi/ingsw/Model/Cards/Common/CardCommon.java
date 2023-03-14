package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;

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
