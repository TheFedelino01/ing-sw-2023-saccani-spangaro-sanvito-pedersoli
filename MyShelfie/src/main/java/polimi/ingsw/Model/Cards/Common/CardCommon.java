package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;

import java.util.ArrayDeque;
import java.util.Queue;

public class CardCommon extends Card {
    private Queue<Point> points;
    private CardCommonType commonType;

    public CardCommon(CardCommonType type){
        points = new ArrayDeque<Point>();
        commonType=type;
    }
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


    @Override
    public boolean isSameType(Card c) {
        if(c instanceof CardCommon ) {
            return this.commonType.equals(((CardCommon) c).commonType);
        }
        return false;
    }
    public boolean equals(CardCommon c){
        return this.points.containsAll(c.getPoints()) && this.commonType==c.commonType;
    }

}
