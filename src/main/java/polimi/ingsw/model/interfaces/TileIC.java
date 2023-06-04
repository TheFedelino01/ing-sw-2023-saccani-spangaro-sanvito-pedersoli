package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.TileType;

/**
 * This interface is implemented by Tile
 */
public interface TileIC {
    /**
     * This method is used to get the type of the tile
     * @return the type of the tile {@link TileType}
     */
    TileType getType();

    /**
     * This method check if the tile has a free side
     * @return true if the tile has a free side
     */
    boolean hasFreeSide();

    /**
     * This method check if the tile is the same type of the parameter
     * @param type type of the tile
     * @return true if the tile is the same type of the tile passed as parameter
     */
    boolean isSameType(TileType type);

    /**
     * This method is used to get the string representation of the tile
     * @return the string representation of the tile
     */
    String toString();

    /**
     * This method is used to get the Background of the tile
     * @return the Background of the tile
     */
    String getBackground();
}
