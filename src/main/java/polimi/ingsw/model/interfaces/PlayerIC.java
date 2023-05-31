package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.util.ArrayList;
import java.util.List;

public interface PlayerIC {

    public String getNickname();

    public Shelf getShelf();

    public CardGoalIC getSecretGoal_IC();

    public List<TileIC> getInHandTile_IC();

    public List<PointIC> getObtainedPoints_IC();

    public int getTotalPoints();

    public boolean getReadyToStart();

    public boolean equals(Object p);

    public boolean isConnected();

    public int getMaxFreeSpacesInACol();

    public int getNumofFreeSpacesInCol(int col);


}
