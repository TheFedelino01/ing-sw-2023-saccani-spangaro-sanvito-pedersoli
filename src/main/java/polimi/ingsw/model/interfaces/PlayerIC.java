package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Shelf;

import java.util.List;

public interface PlayerIC {

    String getNickname();

    Shelf getShelf();

    CardGoalIC getSecretGoal_IC();

    List<TileIC> getInHandTile_IC();

    List<PointIC> getObtainedPoints_IC();

    int getTotalPoints();

    boolean getReadyToStart();

    boolean equals(Object p);

    boolean isConnected();

    int getMaxFreeSpacesInACol();

    int getNumOfFreeSpacesInCol(int col);


}
