package polimi.ingsw.model;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.gameModelView.GameModelImmutable;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTile;
    private List<Point> obtainedPoints;
    private boolean readyToStart = false;
    private boolean connected = true;

    private transient List<GameListener> listeners;


    public Player(String nickname) {
        this.nickname = nickname;
        shelf = new Shelf();
        secretGoal = new CardGoal();
        inHandTile = new ArrayList<>();
        obtainedPoints = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public Player(String nickname, Shelf shelf, CardGoal secretGoal, List<Tile> inHandTile, List<Point> obtainedPoints, Integer columnChosen) {
        this.nickname = nickname;
        this.shelf = shelf;
        this.secretGoal = secretGoal;
        this.inHandTile = inHandTile;
        this.obtainedPoints = obtainedPoints;
        listeners = new ArrayList<>();
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

    public void setShelfS(Shelf shelf) {
        this.shelf = shelf;
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

    public List<Tile> getInHandTile() {
        return inHandTile;
    }

    public void setInHandTile(List<Tile> inHandTile) {
        if (inHandTile.size() > 3) {
            throw new IllegalArgumentException("You can't have more than 3 tiles in hand");
        } else {
            this.inHandTile = inHandTile;
        }
    }


    public List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    public void addPoint(Point obtainedPoints, GameModelImmutable model) {
        this.obtainedPoints.add(obtainedPoints);
        notify_addedPoint(obtainedPoints, model);
    }

    public int getTotalPoints() {
        return obtainedPoints.stream().map(Point::getPoint).reduce(0, Integer::sum);
    }

    public boolean getReadyToStart() {
        return readyToStart;
    }

    public void setReadyToStart() {
        readyToStart = true;
    }

    public void setNotReadyToStart() {
        readyToStart = false;
    }

    public boolean equals(Player p) {
        return this.nickname.equals(p.nickname);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getMaxFreeSpacesInACol(){
        return this.shelf.getMaxFreeSpacesInACol();
    }

    public void addListener(GameListener obj) {
        listeners.add(obj);
    }

    private void notify_addedPoint(Point point, GameModelImmutable model) {
        for (GameListener l : listeners) {
            try {
                l.addedPoint(this, point, model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<GameListener> getListeners() {
        return listeners;
    }

    public void removeListener(GameListener lis) {
        listeners.remove(lis);
    }
}
