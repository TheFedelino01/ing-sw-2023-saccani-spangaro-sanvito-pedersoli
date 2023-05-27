package polimi.ingsw.utility;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.view.userView.gui.IntRecord;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonlyUsedMethods {

    /**
     * Creates a list of full shelves, randomly initialized<br>
     * <br>
     * @param playerNum number of shelves to create
     * @return list of randomly initialized shelves, full
     */

    protected static List<Shelf> createRandomShelves(int playerNum){
        List<Shelf> s = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            Tile[][] t = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                    t[r][c] = new Tile(TileType.randomTile());
            s.add(new Shelf(t, 0));
        }
        return s;
    }

    /**
     * Tiles will be removed column by column, meaning that<br>
     * if col=1 and numToRemove=10, a new shelf will be returned<br>
     * equal to the one received, but with column 1 and 2 empty<br>
     * <br>
     * @param s shelf from which to remove tiles
     * @param col will remove tiles starting from this column
     * @param numToRemove number of tiles to remove
     */
    protected static Shelf removeTile(Shelf s, int col, int numToRemove) {
        int row = 0, cont = 0;
        while (cont < numToRemove) {
            if (row >= DefaultValue.NumOfRowsShelf) {
                row = 0;
                col++;
            }
            while (s.get(row, col).equals(new Tile(TileType.NOT_USED))) {
                row++;
                if (col >= DefaultValue.NumOfColumnsShelf)
                    return s;
            }
            s.setSingleTile(new Tile(TileType.NOT_USED), row, col);
            s.setFreeSpace(s.getFreeSpace() + 1);
            row++;
            cont++;
        }
        return s;
    }

    /**
     * Method returns new coordinates
     *
     * @param move indicates the column just now emptied
     * @param playerNum indicates how many players are playing
     * @return the new starting point to pick tiles (is set so that the test will pick tiles in columns, going top to bottom)
     */
    protected static IntRecord setMoves(int move, int playerNum) {
        switch (playerNum){
            case (2) -> {
                switch (move) {
                    case (0) -> {
                        return new IntRecord(4, 1);
                    }
                    case (1) -> {
                        return new IntRecord(3, 2);
                    }
                    case (2) -> {
                        return new IntRecord(1, 3);
                    }
                    case (3) -> {
                        return new IntRecord(1, 4);
                    }
                    case (4) -> {
                        return new IntRecord(2, 5);
                    }
                    case (5) -> {
                        return new IntRecord(3, 6);
                    }
                    case (6) -> {
                        return new IntRecord(3, 7);
                    }
                    default -> {
                        throw new RuntimeException("Game broke");
                    }
                }
            }
            case (3) -> {
                switch (move) {
                    case (0) -> {
                        return new IntRecord(5, 0);
                    }
                    case (1) -> {
                        return new IntRecord(4, 1);
                    }
                    case (2) -> {
                        return new IntRecord(2, 2);
                    }
                    case (3) -> {
                        return new IntRecord(0, 3);
                    }
                    case (4) -> {
                        return new IntRecord(1, 4);
                    }
                    case (5) -> {
                        return new IntRecord(2, 5);
                    }
                    case (6) -> {
                        return new IntRecord(2, 6);
                    }
                    case (7) -> {
                        return new IntRecord(3, 7);
                    }
                    case (8) -> {
                        return new IntRecord(3, 8);
                    }
                    default -> {
                        throw new RuntimeException("Game broke");
                    }
                }
            }
            case (4) -> {
                switch (move) {
                    case (0) -> {
                        return new IntRecord(4, 0);
                    }
                    case (1) -> {
                        return new IntRecord(3, 1);
                    }
                    case (2) -> {
                        return new IntRecord(2, 2);
                    }
                    case (3) -> {
                        return new IntRecord(0, 3);
                    }
                    case (4) -> {
                        return new IntRecord(0, 4);
                    }
                    case (5) -> {
                        return new IntRecord(1, 5);
                    }
                    case (6) -> {
                        return new IntRecord(2, 6);
                    }
                    case (7) -> {
                        return new IntRecord(3, 7);
                    }
                    case (8) -> {
                        return new IntRecord(3, 8);
                    }
                    default -> {
                        throw new RuntimeException("Game broke");
                    }
                }
            }
            default -> {
                return new IntRecord(-1, -1);
            }
        }
    }
}
