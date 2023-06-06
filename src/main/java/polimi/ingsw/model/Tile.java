package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.interfaces.TileIC;

import java.io.Serializable;
import java.util.Random;

import static polimi.ingsw.model.enumeration.TileType.NOT_USED;

/**
 * Tile class
 * Tile is the class that represents the tiles of the game
 */
public class Tile implements Serializable, TileIC {
    /**
     * Every tile has a type and a free side flag
     */
    private TileType TYPE;
    /**
     * The free side flag is true if the tile has at least a free side, and it's used to check if the tile can be picked
     */
    private boolean freeSide;
    /**
     * The offset is used to randomize the tile's background
     */
    private final int offset;

    /**
     * Constructor
     */
    public Tile() {
        TYPE = NOT_USED;
        freeSide = false;
        offset = new Random().nextInt(3);
    }

    /**
     * Constructor
     *
     * @param type
     */
    public Tile(TileType type) {
        this.TYPE = type;
        freeSide = false;
        offset = new Random().nextInt(3);
    }

    /**
     * Constructor
     *
     * @param TYPE
     * @param freeSide
     */
    public Tile(TileType TYPE, boolean freeSide) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
        offset = new Random().nextInt(3);
    }

    /**
     * @return the tile's type
     */
    public TileType getType() {
        return TYPE;
    }

    /**
     * Sets the tile's type
     *
     * @param TYPE
     */
    public void setType(TileType TYPE) {
        this.TYPE = TYPE;
    }

    /**
     * @return the tile's free side flag
     */
    public boolean hasFreeSide() {
        return freeSide;
    }

    /**
     * Sets the tile's free side
     *
     * @param freeSide
     */
    public void setFreeSide(boolean freeSide) {
        this.freeSide = freeSide;
    }

    /**
     * Checks if the types are the same
     *
     * @param type
     * @return
     */
    public boolean isSameType(TileType type) {
        return this.TYPE == type;
    }

    /**
     * @return the tile's background
     */
    public String getBackground() {
        return TYPE.getBackgroundClass() + offset;
    }

    /**
     * @return the tile to string format
     */
    @Override
    public String toString() {
        return TYPE.toString();
    }
}
