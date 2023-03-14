package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Goal.CardGoal;

import java.util.List;

public class Player {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTail;
    private List<Point> obtainedPoints;

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
        this.inHandTail = inHandTail;
    }

    public List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    public void setObtainedPoints(List<Point> obtainedPoints) {
        this.obtainedPoints = obtainedPoints;
    }
}
