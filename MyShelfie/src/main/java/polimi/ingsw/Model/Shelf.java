package polimi.ingsw.Model;

import polimi.ingsw.Model.Enumeration.TileType;

import java.io.Serializable;

public class Shelf implements Serializable {
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

    public void position(int column, TileType tipo) {
        //push the tile in the column making it slide down until it finds a tile, or it reaches the bottom
        for (int i = DefaultValue.NumOfRowsShelf-1; i >0 ; i--) {
            if (shelf[i][column].isSameType(TileType.NOT_USED)) {
                shelf[i][column].setType(tipo);
                freeSpace--;
                break;
            }
        }
    }

    public String toString(){
        String ris="{";
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris+="[";
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                ris+=shelf[r][c];
                ris+=(c+1)!=DefaultValue.NumOfColumnsShelf? ",":"";
            }
            ris+="]\n";
        }
        ris+="}";
        return ris;
    }



}

