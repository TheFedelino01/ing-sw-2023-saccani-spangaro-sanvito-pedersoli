package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class CommonCard extends Card {
    private Queue<Point> points;
    private CardCommonType commonType;

    public CommonCard(CardCommonType type){
        points = new ArrayDeque<>();
        commonType=type;
    }
    public CommonCard(Queue<Point> points, CardCommonType commonType) {
        this.points = points;
        this.commonType = commonType;
    }


    public abstract boolean verify(Shelf toCheck);


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


    @Override
    public boolean isSameType(Card c) {
        if(c instanceof CommonCard) {
            return this.commonType.equals(((CommonCard) c).commonType);
        }
        return false;
    }
    public boolean equals(CommonCard c){
        return this.points.containsAll(c.getPoints()) && this.commonType==c.commonType;
    }

}
