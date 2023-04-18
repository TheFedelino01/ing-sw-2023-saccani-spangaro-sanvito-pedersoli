package polimi.ingsw.Model;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.Model.Enumeration.TileType;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

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
        int i = DefaultValue.displayShelfRow;
        StringBuilder ris = new StringBuilder();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(i, DefaultValue.displayShelfStartingCol).toString());
            ris.append("[ ");
            i++;
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                switch (shelf[r][c].getType()){
                    case CAT ->  ris.append(ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                    case PLANT -> ris.append(ansi().bg(Ansi.Color.MAGENTA).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                    case ACTIVITY -> ris.append(ansi().bg(Ansi.Color.YELLOW).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                    case FRAME -> ris.append(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                    case BOOK -> ris.append(ansi().bg(Ansi.Color.WHITE).fg(Ansi.Color.BLACK).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                    case TROPHY -> ris.append(ansi().bg(Ansi.Color.CYAN).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    default -> ris.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("N").bg(Ansi.Color.DEFAULT).fg(Ansi.Color.DEFAULT));
                }
                ris.append(" , ");
            }
            ris.append("  ]");
        }
        return ris.toString();
    }

    public String toString(int col){
        StringBuilder ris = new StringBuilder();
        int i = DefaultValue.displayShelfRow;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(i, col).toString());
            i++;
            ris.append("[ ");
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                switch (shelf[r][c].getType()){
                    case CAT ->  ris.append(ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    case PLANT -> ris.append(ansi().bg(Ansi.Color.MAGENTA).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    case ACTIVITY -> ris.append(ansi().bg(Ansi.Color.YELLOW).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    case FRAME -> ris.append(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    case BOOK -> ris.append(ansi().bg(Ansi.Color.WHITE).fg(Ansi.Color.BLACK).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    case TROPHY -> ris.append(ansi().bg(Ansi.Color.CYAN).fg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
                    default -> ris.append(ansi().bg(Ansi.Color.BLACK).fg(Ansi.Color.BLACK).a("N").bg(Ansi.Color.DEFAULT));
                }
                ris.append(" , ");
            }
            ris.append("  ]\n");
        }
        return ris.toString();
    }

    public String toStringCommonCard(){
        StringBuilder ris = new StringBuilder();
        int row = 10;
        int col = 50;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(row, col).toString());
            row++;
            ris.append("[ ");
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                switch (shelf[r][c].getType()){
                    case CAT ->  ris.append(ansi().bg(Ansi.Color.GREEN).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    case PLANT -> ris.append(ansi().bg(Ansi.Color.MAGENTA).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    case ACTIVITY -> ris.append(ansi().bg(Ansi.Color.YELLOW).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    case FRAME -> ris.append(ansi().bg(Ansi.Color.BLUE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    case BOOK -> ris.append(ansi().bg(Ansi.Color.WHITE).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    case TROPHY -> ris.append(ansi().bg(Ansi.Color.CYAN).a(shelf[r][c].toString().substring(0, 1)).bg(Ansi.Color.DEFAULT));
                    default -> ris.append(ansi().bg(Ansi.Color.BLACK).a("N").bg(Ansi.Color.DEFAULT));
                }
                ris.append(" , ");
            }
            ris.append("  ]\n");
        }
        return  ris.toString();
    }



}

