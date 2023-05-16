package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.TileType;

import java.io.Serializable;

import static polimi.ingsw.model.enumeration.TileType.NOT_USED;

public class Tile implements Serializable {
    private TileType TYPE;
    private boolean freeSide;
    private String backgroundImg;

    public Tile() {
        TYPE = NOT_USED;
        freeSide = false;
    }

    public Tile(TileType type) {
        this.TYPE = type;
        freeSide = false;
    }

    public Tile(TileType TYPE, boolean freeSide) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
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

    @Override
    public String toString() {
        return TYPE.toString();
    }
}
