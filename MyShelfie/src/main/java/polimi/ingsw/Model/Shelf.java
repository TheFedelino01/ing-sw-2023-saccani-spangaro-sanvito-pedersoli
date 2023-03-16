package polimi.ingsw.Model;

import polimi.ingsw.Controller.DefaultValue;

public class Shelf {
    private Tile[][] shelf;
    private Integer freeSpace; //6*5

    public Shelf(){
        shelf = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
        freeSpace=DefaultValue.NumOfRowsShelf*DefaultValue.NumOfColumnsShelf;
    }

    public Shelf(Tile[][] shelf, Integer freeSpace) {
        this.shelf = shelf;
        this.freeSpace = freeSpace;
    }

    public Tile[][] getShelf() {
        return shelf;
    }

    public void setShelf(Tile[][] shelf) {
        this.shelf = shelf;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
    }
}
