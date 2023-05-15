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
        for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
                shelf[r][c] = new Tile();
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

    public void setSingleTile(Tile t, int r, int c) {
        this.shelf[r][c] = t;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }


    public Tile get(int r, int c) {
        return shelf[r][c];
    }

    public void position(int column, TileType tipo) {
        //push the tile in the column making it slide down until it finds a tile, or it reaches the bottom
        for (int i = DefaultValue.NumOfRowsShelf - 1; i >= 0; i--) {
            if (shelf[i][column].isSameType(TileType.NOT_USED)) {
                shelf[i][column].setType(tipo);
                freeSpace--;
                break;
            }
        }
    }

    public String toString() {
        int i = DefaultValue.row_shelves;
        StringBuilder ris = new StringBuilder();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(i, DefaultValue.col_shelves).toString());
            ris.append(createRow(r));
            i++;
        }
        return ris.toString();
    }

    public String toString(int col) {
        StringBuilder ris = new StringBuilder();
        int i = DefaultValue.row_shelves;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(i, col).toString());
            i++;
            ris.append(createRow(r));
        }
        return ris.toString();
    }

    public String toStringGoalCard() {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_goalCards, DefaultValue.col_goalCards - 1).bold().a("Personal goal:").boldOff().toString());
        int row = 1;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            ris.append(ansi().cursor(DefaultValue.row_goalCards + row, DefaultValue.col_goalCards).toString());
            row++;
            ris.append(createRow(r));
        }
        return ris.toString();
    }

    private String createRow(int r) {
        StringBuilder ris = new StringBuilder();
        ris.append("[");
        for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
            switch (shelf[r][c].getType()) {
                case CAT ->
                        ris.append(ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.WHITE).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                case PLANT ->
                        ris.append(ansi().bg(Ansi.Color.MAGENTA).fg(Ansi.Color.WHITE).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                case ACTIVITY ->
                        ris.append(ansi().bg(Ansi.Color.YELLOW).fg(Ansi.Color.WHITE).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                case FRAME ->
                        ris.append(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.WHITE).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                case BOOK ->
                        ris.append(ansi().bg(Ansi.Color.WHITE).fg(Ansi.Color.BLACK).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                case TROPHY ->
                        ris.append(ansi().bg(Ansi.Color.CYAN).fg(Ansi.Color.WHITE).a(" ").a(shelf[r][c].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
                default ->
                        ris.append(ansi().bg(Ansi.Color.BLACK).fg(Ansi.Color.BLACK).a(" ").a("N").a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString());
            }
            if (c != DefaultValue.NumOfColumnsShelf - 1)
                ris.append(",");
        }
        ris.append("]");
        return ris.toString();
    }

    public boolean isEmpty(){
        for(int r = 0; r<DefaultValue.NumOfRowsShelf; r++){
            for(int c = 0; c<DefaultValue.NumOfColumnsShelf; c++){
                if(!shelf[r][c].isSameType(TileType.NOT_USED))
                    return false;
            }
        }
        return true;
    }

}

