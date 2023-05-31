package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.Direction;

public interface PlaygroundIC {
    TileIC getTile_IC(int r, int c);

    int getNumOfTileinTheBag();

    boolean checkBeforeGrab(int r, int c, Direction direction, int num);

    boolean allTileHaveAllFreeSide();

    String toString();


}
