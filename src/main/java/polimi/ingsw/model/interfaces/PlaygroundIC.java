package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.Direction;

/**
 * This interface is implemented by Playground
 */
public interface PlaygroundIC {
    /**
     * This method is used to get the tile in the playground
     * @param r row
     * @param c column
     * @return the tile in the playground {@link TileIC}
     */
    TileIC getTile_IC(int r, int c);

    /**
     * This method is used to get the tiles in the bag
     * @return the tiles in the bag {@link TileIC}
     */
    int getNumOfTileinTheBag();

    /**
     * This method is used to check if the tile actually exists in the playground
     * @param r row
     * @param c column
     * @param direction direction
     * @param num number of the tile
     * @return true if the tile actually exists in the playground
     */
    boolean checkBeforeGrab(int r, int c, Direction direction, int num);

    /**
     * This method check if all the tiles have at least one free side
     * @return true if all the tiles have at least one free side
     */
    boolean allTileHaveAllFreeSide();

    /**
     * This method is used to get the string representation of the playground
     * @return the string representation of the playground
     */
    String toString();


}
