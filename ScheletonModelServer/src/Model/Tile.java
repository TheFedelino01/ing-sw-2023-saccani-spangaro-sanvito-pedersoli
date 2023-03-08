package Model;

public class Tile {
    private TileType TYPE;
    private boolean freeSide;
    private Integer numOfAvailable;

    public Tile(TileType TYPE, boolean freeSide, Integer numOfAvailable) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
        this.numOfAvailable = numOfAvailable;
    }

    public TileType getTYPE() {
        return TYPE;
    }

    public void setTYPE(TileType TYPE) {
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
        this.numOfAvailable = numOfAvailable;
    }
}
