package polimi.ingsw.model;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.interfaces.ShelfIC;
import polimi.ingsw.model.interfaces.TileIC;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

public class Shelf implements Serializable, ShelfIC {
    private Tile[][] shelf;
    private Integer freeSpace; //6*5

    /**
     * Constructor
     */
    public Shelf() {
        shelf = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
        for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
                //shelf[r][c] = new Tile();
                //if((r==0&& c==0) || (r==1 && c==0)){
                    shelf[r][c] = new Tile();
               // }else{
                //    shelf[r][c] = new Tile(TileType.ACTIVITY);
               // }
            }
        }
        freeSpace = DefaultValue.NumOfRowsShelf * DefaultValue.NumOfColumnsShelf;
        //freeSpace=2;
    }

    /**
     * Constructor
     *
     * @param shelf
     * @param freeSpace
     */
    public Shelf(Tile[][] shelf, Integer freeSpace) {
        this.shelf = shelf;
        this.freeSpace = freeSpace;
    }

    /**
     * @return the shelf
     */
    public Tile[][] getShelf() {
        return shelf;
    }

    /**
     * @param shelf initialise a new shelf to this param
     */
    public void setShelf(Tile[][] shelf) {
        this.shelf = shelf;
        //Set every tile to not used
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                this.shelf[i][j] = new Tile(TileType.NOT_USED);
            }
        }
    }

    /**
     * @param t tile to set
     * @param r row in which to set the tile
     * @param c col in which to set the tile
     */
    public void setSingleTile(Tile t, int r, int c) {
        this.shelf[r][c] = t;
    }

    /**
     * @return shelf's free space
     */
    public Integer getFreeSpace() {
        return freeSpace;
    }

    /**
     * @return shelf's occupied space
     */
    public Integer getOccupiedSpace() {
        return (DefaultValue.NumOfRowsShelf * DefaultValue.NumOfColumnsShelf) - freeSpace;
    }

    /**
     * @param r row
     * @param c col
     * @return tile in said position
     */
    public Tile getSingleTile(int r, int c) {
        return shelf[r][c];
    }

    /**
     * places a tile in said column
     *
     * @param column in which to place the tile
     * @param type   of tile to place
     */
    public void position(int column, TileType type) {
        //push the tile in the column making it slide down until it finds a tile, or it reaches the bottom
        for (int i = DefaultValue.NumOfRowsShelf - 1; i >= 0; i--) {
            if (shelf[i][column].isSameType(TileType.NOT_USED)) {
                shelf[i][column].setType(type);
                freeSpace--;
                break;
            }
        }
    }

    /**
     * @return the shelf in string form
     */
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

    /**
     * @param col
     * @return The specified shelf column in string form
     */
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

    /**
     * @return the goal card in string form
     */
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

    /**
     * Part of the toString method
     *
     * @param r
     * @return
     */
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

    /**
     * @return true if a shelf is empty, false if not
     */
    public boolean isEmpty() {
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (!shelf[r][c].isSameType(TileType.NOT_USED))
                    return false;
            }
        }
        return true;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    /**
     * @return the free space in a column
     */
    public int getMaxFreeSpacesInACol() {
        int max = 0;
        int tmp = 0;
        for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
            if (tmp > max) {
                max = tmp;
            }
            tmp = 0;
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
                if (!getSingleTile(r, c).getType().equals(TileType.NOT_USED)) {
                    break;
                } else {
                    tmp++;
                }
            }
        }
        if (tmp > max) {
            max = tmp;
        }
        return max;
    }

    /**
     * @param col the column to check free space
     * @return the number of free spaces in the specified column
     */
    public int getNumofFreeSpacesInCol(int col) {
        int tmp = 0;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            if (!getSingleTile(r, col).getType().equals(TileType.NOT_USED)) {
                return tmp;
            } else {
                tmp++;
            }
        }

        return tmp;
    }

    /**
     * Different return, made for making the shelf immutable client side
     *
     * @return
     */
    @Override
    public TileIC[][] getShelf_IC() {
        return shelf;
    }

    /**
     * Different return, made for making the tile immutable client side
     *
     * @param r
     * @param c
     * @return
     */
    @Override
    public TileIC get_IC(int r, int c) {
        return shelf[r][c];
    }

}

