package polimi.ingsw.model;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.interfaces.CardGoalIC;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.model.interfaces.PointIC;
import polimi.ingsw.model.interfaces.TileIC;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable, PlayerIC {
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

    public void clearInHandTile() {
        inHandTile = new ArrayList<Tile>();
    }


    public List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    public void addPoint(Point obtainedPoints, GameModel model) {
        this.obtainedPoints.add(obtainedPoints);
        notify_addedPoint(obtainedPoints, new GameModelImmutable(model));
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

    @Override
    public boolean equals(Object p) {
        if(p instanceof Player) {
            return this.nickname.equals(((Player)p).nickname);
        }
        return false;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getMaxFreeSpacesInACol() {
        return this.shelf.getMaxFreeSpacesInACol();
    }

    public int getNumofFreeSpacesInCol(int col) {
        return this.shelf.getNumofFreeSpacesInCol(col);
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


    public List<TileIC> getInHandTile_IC() {
        return new ArrayList<TileIC>(inHandTile);
    }

    @Override
    public List<PointIC> getObtainedPoints_IC() {
        return new ArrayList<PointIC>(obtainedPoints);
    }

    @Override
    public CardGoalIC getSecretGoal_IC() {
        return secretGoal;
    }
}
