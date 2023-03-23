package polimi.ingsw.Model;

import polimi.ingsw.Model.Enumeration.TileType;

public class Shelf {
    private Tile[][] shelf;
    private Integer freeSpace; //6*5

    public Shelf() {
        shelf = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
        for(int c=0; c<DefaultValue.NumOfColumnsShelf;c++){
            for(int r=0; r<DefaultValue.NumOfRowsShelf;r++){
                shelf[r][c]= new Tile();
            }
        }
        freeSpace = DefaultValue.NumOfRowsShelf * DefaultValue.NumOfColumnsShelf;
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
        //Set every tile to not used
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                this.shelf[i][j] = new Tile(TileType.NOT_USED);
            }
        }
    }

    public void setSingleTile(Tile t, int r, int c){
        this.shelf[r][c]=t;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }



    public Tile get(int r, int c) {
        return shelf[r][c];
    }

    public void position(int row, TileType tipo) {
        //push the tile in the column making it slide down until it finds a tile, or it reaches the bottom
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            if (shelf[row][i].isSameType(TileType.NOT_USED)) {
                shelf[row][i].setType(tipo);
                freeSpace--;
                break;
            }
        }
    }



}

