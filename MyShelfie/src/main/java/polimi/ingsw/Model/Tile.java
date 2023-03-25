package polimi.ingsw.Model;

import polimi.ingsw.Model.Enumeration.TileType;

import static polimi.ingsw.Model.Enumeration.TileType.NOT_USED;

public class Tile {
    private TileType TYPE;
    private boolean freeSide;
    private Integer numOfAvailable;

    public Tile(){
        TYPE=NOT_USED;
        freeSide=false;
        numOfAvailable=1;
    }
    public Tile(TileType type){
        this.TYPE = type;
        freeSide=false;
        numOfAvailable=-1;
    }

    public Tile(TileType TYPE, Integer numOfAvailable) {
        this.TYPE = TYPE;
        this.freeSide = false;
        this.numOfAvailable = numOfAvailable;
    }
    public Tile(TileType TYPE, Integer numOfAvailable, boolean freeSide) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
        this.numOfAvailable = numOfAvailable;
    }

    public TileType getType() {
        return TYPE;
    }

    public void setType(TileType TYPE) {
        this.TYPE = TYPE;
    }

    public boolean isFreeSide() {
        return freeSide;
    }

    public void setFreeSide(boolean freeSide) {
        this.freeSide = freeSide;
    }

    public Integer getNumOfAvailable() {
        return numOfAvailable;
    }

    public void setNumOfAvailable(Integer numOfAvailable) {
        this.numOfAvailable = numOfAvailable<=22 && numOfAvailable>=0 ? numOfAvailable :
                numOfAvailable>22? 22 : 0;
    }

    public void decrementAvailableBy1(){
        if(numOfAvailable>=1){
            numOfAvailable--;
        }
    }

    public boolean isSameType(TileType type){
        return this.TYPE==type;
    }
}
