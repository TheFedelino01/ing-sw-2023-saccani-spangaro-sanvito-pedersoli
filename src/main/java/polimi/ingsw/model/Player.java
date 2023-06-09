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
import java.util.Iterator;
import java.util.List;

/**
 * Player's class<br>
 * A player has a nickname, his shelf, a secretGoal, <br>
 * the tiles he has in hand (inHandTile), the points he obtained<br>
 * all the listeners needed for the network, a flag that indicates if he's ready to start<br>
 * and a flag that indicates whether he's connected to the server or not<br>
 */
public class Player implements Serializable, PlayerIC {
    private String nickname;
    private Shelf shelf;
    private CardGoal secretGoal;
    private List<Tile> inHandTile;
    private List<Point> obtainedPoints;
    private boolean readyToStart = false;
    private boolean connected = true;

    private transient List<GameListener> listeners;

    /**
     * Constructor
     *
     * @param nickname
     */
    public Player(String nickname) {
        this.nickname = nickname;
        shelf = new Shelf();
        secretGoal = new CardGoal();
        inHandTile = new ArrayList<>();
        obtainedPoints = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param nickname
     * @param shelf
     * @param secretGoal
     * @param inHandTile
     * @param obtainedPoints
     */
    public Player(String nickname, Shelf shelf, CardGoal secretGoal, List<Tile> inHandTile, List<Point> obtainedPoints) {
        this.nickname = nickname;
        this.shelf = shelf;
        this.secretGoal = secretGoal;
        this.inHandTile = inHandTile;
        this.obtainedPoints = obtainedPoints;
        listeners = new ArrayList<>();
    }

    /**
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname sets the player's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the player's shelf
     */
    public Shelf getShelf() {
        return shelf;
    }


    /**
     * @param shelf sets the player's shelf
     */
    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    /**
     * @return the player's card goal
     */
    public CardGoal getSecretGoal() {
        return secretGoal;
    }

    /**
     * @param secretGoal sets the player's card goal
     */
    public void setSecretGoal(CardGoal secretGoal) {
        this.secretGoal = secretGoal;
    }

    /**
     * @return player's hand
     */
    public List<Tile> getInHandTile() {
        return inHandTile;
    }

    /**
     * @param inHandTile sets player's hand
     */
    public void setInHandTile(List<Tile> inHandTile) {
        if (inHandTile.size() > 3) {
            throw new IllegalArgumentException("You can't have more than 3 tiles in hand");
        } else {
            this.inHandTile = inHandTile;
        }
    }

    /**
     * Clears the player's hand
     */
    public void clearInHandTile() {
        inHandTile = new ArrayList<>();
    }

    /**
     * @return the player's obtained points
     */
    public List<Point> getObtainedPoints() {
        return obtainedPoints;
    }

    /**
     * Adds a point object to the player's points, then notifies the server
     *
     * @param obtainedPoints
     * @param model
     */
    public void addPoint(Point obtainedPoints, GameModel model) {
        this.obtainedPoints.add(obtainedPoints);
        notify_addedPoint(obtainedPoints, new GameModelImmutable(model));
    }

    /**
     * @return the sum pf the player's points
     */
    public int getTotalPoints() {
        return obtainedPoints.stream().map(Point::getPoint).reduce(0, Integer::sum);
    }

    /**
     * @return the player's readiness to start
     */
    public boolean getReadyToStart() {
        return readyToStart;
    }

    /**
     * Sets the player as ready to play
     */
    public void setReadyToStart() {
        readyToStart = true;
    }

    /**
     * Sets the player as not ready to play
     */
    public void setNotReadyToStart() {
        readyToStart = false;
    }

    /**
     * @param p player to check
     * @return true if the players are the same
     */
    @Override
    public boolean equals(Object p) {
        if (p instanceof Player) {
            return this.nickname.equals(((Player) p).nickname);
        }
        return false;
    }

    /**
     * @return the player's connection status
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @param connected sets the player's connection status to the param
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * @return number of max free spaces in a random column
     */
    public int getMaxFreeSpacesInACol() {
        return this.shelf.getMaxFreeSpacesInACol();
    }

    /**
     * @param col column
     * @return number of free spaces in said column
     */
    public int getNumOfFreeSpacesInCol(int col) {
        return this.shelf.getNumofFreeSpacesInCol(col);
    }

    /**
     * Adds a listener to the list
     *
     * @param obj listener to add
     */
    public void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * Notifies the server that a point has been added to the player
     *
     * @param point added to the player
     * @param model where the player is playing
     */
    private void notify_addedPoint(Point point, GameModelImmutable model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.addedPoint(this, point, model);
            } catch (RemoteException e) {
                System.out.println("During notification of notify_addedPoint, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * @return the list of gameListeners attached to the player
     */
    public List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * Remove said listener from the player
     *
     * @param lis listener to remove
     */
    public void removeListener(GameListener lis) {
        listeners.remove(lis);
    }

    /**
     * @return a list of TileIC, an interface that we send to the clients<br>
     * to make the model immutable<br>
     */
    public List<TileIC> getInHandTile_IC() {
        return new ArrayList<>(inHandTile);
    }

    /**
     * @return a list of PointIC, an interface that we send to the clients<br>
     * to make the model immutable<br>
     */
    @Override
    public List<PointIC> getObtainedPoints_IC() {
        return new ArrayList<>(obtainedPoints);
    }

    /**
     * @return a CardGoalIC, an interface that we send to the clients<br>
     * to make the model immutable<br>
     */
    @Override
    public CardGoalIC getSecretGoal_IC() {
        return secretGoal;
    }
}
