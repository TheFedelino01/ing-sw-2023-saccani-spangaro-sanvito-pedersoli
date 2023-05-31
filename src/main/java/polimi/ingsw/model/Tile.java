package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.interfaces.TileIC;

import java.io.Serializable;
import java.util.Random;

import static polimi.ingsw.model.enumeration.TileType.NOT_USED;

public class Tile implements Serializable, TileIC {
    private TileType TYPE;
    private boolean freeSide;
    private final int offset;

    public Tile() {
        TYPE = NOT_USED;
        freeSide = false;
        offset = new Random().nextInt(3);
    }

    public Tile(TileType type) {
        this.TYPE = type;
        freeSide = false;
        offset = new Random().nextInt(3);
    }

    public Tile(TileType TYPE, boolean freeSide) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
        offset = new Random().nextInt(3);
    }

    public TileType getType() {
        return TYPE;
    }

    public void setType(TileType TYPE) {
        this.TYPE = TYPE;
    }

    public boolean hasFreeSide() {
        return freeSide;
    }

    public void setFreeSide(boolean freeSide) {
        this.freeSide = freeSide;
    }


    public boolean isSameType(TileType type) {
        return this.TYPE == type;
    }

    public String getBackground(){
        return TYPE.getBackgroundClass()+offset;
    }

    @Override
    public String toString() {
        return TYPE.toString();
    }
}
