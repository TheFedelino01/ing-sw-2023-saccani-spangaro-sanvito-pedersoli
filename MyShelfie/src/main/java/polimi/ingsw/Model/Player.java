package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.CardGoalType;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTail;
    private List<Point> obtainedPoints;
    private boolean readyToStart=false;

    public Player(String nickname){
        this.nickname=nickname;
        shelf=new Shelf();
        secretGoal= new CardGoal();
        inHandTail = new ArrayList<Tile>();
        obtainedPoints=new ArrayList<Point>();
    }
    public Player(String nickname, Shelf shelf, CardGoal secretGoal, List<Tile> inHandTail, List<Point> obtainedPoints) {
        this.nickname = nickname;
        this.shelf = shelf;
        this.secretGoal = secretGoal;
        this.inHandTail = inHandTail;
        this.obtainedPoints = obtainedPoints;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public CardGoal getSecretGoal() {
        return secretGoal;
    }

    public void setSecretGoal(CardGoal secretGoal) {
        this.secretGoal = secretGoal;
    }

    public List<Tile> getInHandTail() {
        return inHandTail;
    }

    public void setInHandTail(List<Tile> inHandTail) {
        if (inHandTail.size() > 3) {
            throw new IllegalArgumentException("You can't have more than 3 tiles in hand");
        }
        else {
            this.inHandTail = inHandTail;
        }
    }

    private List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    public void addPoint(Point obtainedPoints) {
        //TODO Controllare che non possono esserci point relativi alla stessa carta
        for(Point p: this.obtainedPoints){
            if(p.getReferredTo().isSameType(obtainedPoints.getReferredTo())){
                throw new IllegalArgumentException("You can't have more than one point for the same card");
            }
        }
        this.obtainedPoints.add(obtainedPoints);
    }

    public int getTotalPoints(){
        return obtainedPoints.stream().map(Point::getPoint).reduce(0, Integer::sum);
    }

    public boolean getReadyToStart(){return readyToStart;}
    public void setReadyToStart(){
        readyToStart=true;
    }
    public boolean equals(Player p){
        return this.nickname.equals(p.nickname);
    }
}
