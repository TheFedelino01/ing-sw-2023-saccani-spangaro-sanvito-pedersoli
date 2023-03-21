package polimi.ingsw.Model.Cards.Goal;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;

import java.util.Map;

public class CardGoal extends Card {
    private Shelf layoutToMatch; //La tavola del giocatore deve matchare questo layout (per acquisire punti a seconda della Map legendPoint)
    private Map<Integer, Point> legendPoint;
    private CardGoalType goalType;

    public CardGoal(){
        layoutToMatch=null;
        legendPoint=null;
        goalType=CardGoalType.NOT_SET;
    }

    public CardGoal(CardGoalType type){
        layoutToMatch=null;
        legendPoint=null;
        goalType=type;
    }

    public CardGoal(Shelf layoutToMatch, Map<Integer, Point> legendPoint, CardGoalType goalType) {
        this.layoutToMatch = layoutToMatch;
        this.legendPoint = legendPoint;
        this.goalType = goalType;
    }

    public Point verify(Shelf shelfToCheck){
        return null;
    }

    public Shelf getLayoutToMatch() {
        return layoutToMatch;
    }

    public void setLayoutToMatch(Shelf layoutToMatch) {
        this.layoutToMatch = layoutToMatch;
    }

    public Map<Integer, Point> getLegendPoint() {
        return legendPoint;
    }

    public void setLegendPoint(Map<Integer, Point> legendPoint) {
        this.legendPoint = legendPoint;
    }

    public CardGoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(CardGoalType goalType) {
        this.goalType = goalType;
    }


    public boolean isSameType(CardGoal c) {
        return goalType.equals(c.goalType);
    }

}
