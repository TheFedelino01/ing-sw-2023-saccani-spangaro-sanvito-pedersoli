package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Shelf;

import java.util.List;

/**
 * This interface is implemented by Player
 */
public interface PlayerIC {
    /**
     * This method is used to get the nickname of the player
     * @return the nickname of the player
     */
    String getNickname();

    /**
     * This method is used to get the shelf of the player
     * @return the shelf of the player
     */
    Shelf getShelf();

    /**
     * This method is used to get the private objective card of the player
     * @return the private objective card of the player {@link CardGoalIC}
     */
    CardGoalIC getSecretGoal_IC();

    /**
     * This method is used to get tile in hand of the player
     * @return the list of tile in hand of the player {@link TileIC}
     */
    List<TileIC> getInHandTile_IC();

    /**
     * This method is used to get the obtained points of the player
     * @return the list of obtained points of the player {@link PointIC}
     */
    List<PointIC> getObtainedPoints_IC();

    /**
     * This method is used to get the obtained points of the player
     * @return the sum of the obtained points of the player
     */
    int getTotalPoints();

    /**
     * This method check if the player is ready to start
     * @return true if the player is ready to start
     */
    boolean getReadyToStart();

    /**
     * This method check if an object p is equals to the player
     * @param p object to check
     * @return true if the object p is equals to the player
     */
    boolean equals(Object p);

    /**
     * This method check if the player is connected
     * @return true if the player is connected
     */
    boolean isConnected();

    /**
     * This method return the number of max free spaces in a column
     * @return the number of free spaces in a column
     */
    int getMaxFreeSpacesInACol();


    /**
     * This method return the number of free spaces in a column
     * @param col column to check
     * @return the number of free spaces in a column
     */
    int getNumOfFreeSpacesInCol(int col);


}
