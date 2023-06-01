package polimi.ingsw.utility;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.enumeration.CardType;
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
    public static Shelf removeTile(Shelf s, int col, int numToRemove) {
        int row = 0, cont = 0;
        while (cont < numToRemove) {
            if (row >= DefaultValue.NumOfRowsShelf) {
                row = 0;
                col++;
            }
            while (s.getSingleTile(row, col).equals(new Tile(TileType.NOT_USED))) {
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

    public static Shelf setShelf(String[][] shelf) {
        int rows = DefaultValue.NumOfRowsShelf;
        int cols = DefaultValue.NumOfColumnsShelf;
        Shelf ris = new Shelf();
        TileType tmp;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                switch (shelf[r][c].toUpperCase()) {
                    case "C" -> {
                        tmp = TileType.CAT;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "B" -> {
                        tmp = TileType.BOOK;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "A" -> {
                        tmp = TileType.ACTIVITY;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "F" -> {
                        tmp = TileType.FRAME;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "T" -> {
                        tmp = TileType.TROPHY;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "P" -> {
                        tmp = TileType.PLANT;
                        ris.setSingleTile(new Tile(tmp), r, c);
                        ris.setFreeSpace(ris.getFreeSpace()-1);
                    }
                    case "FIN" -> {
                        tmp = TileType.CAT;
                        ris.setSingleTile(new Tile(tmp), r, c);
                    }
                    case "X", "" -> {
                        tmp = TileType.NOT_USED;
                        ris.setSingleTile(new Tile(tmp), r, c);
                    }
                }
            }
        }
        return ris;
    }

    public static Shelf setUpShelf(CardType type) {
        Shelf ret = new Shelf();
        switch (type.toString()) {
            case "GOAL0" -> {
                ret.setSingleTile(new Tile(TileType.PLANT), 0, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 1, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 3, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 5, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL1" -> {
                ret.setSingleTile(new Tile(TileType.PLANT), 1, 1);
                ret.setSingleTile(new Tile(TileType.CAT), 2, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 2, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 4, 3);
                ret.setSingleTile(new Tile(TileType.FRAME), 5, 4);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL2" -> {
                ret.setSingleTile(new Tile(TileType.FRAME), 1, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 1, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 2, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 3, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL3" -> {
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 0, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 2, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 2, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 3, 3);
                ret.setSingleTile(new Tile(TileType.BOOK), 4, 1);
                ret.setSingleTile(new Tile(TileType.CAT), 4, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL4" -> {
                ret.setSingleTile(new Tile(TileType.TROPHY), 1, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 3, 1);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 4, 4);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 5, 0);
                ret.setSingleTile(new Tile(TileType.CAT), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL5" -> {
                ret.setSingleTile(new Tile(TileType.TROPHY), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 0, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 4, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL6" -> {
                ret.setSingleTile(new Tile(TileType.CAT), 0, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 1, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 2, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 5, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL7" -> {
                ret.setSingleTile(new Tile(TileType.FRAME), 0, 4);
                ret.setSingleTile(new Tile(TileType.CAT), 1, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 2, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 3, 0);
                ret.setSingleTile(new Tile(TileType.BOOK), 4, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL8" -> {
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 2, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 4, 1);
                ret.setSingleTile(new Tile(TileType.PLANT), 4, 4);
                ret.setSingleTile(new Tile(TileType.FRAME), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL9" -> {
                ret.setSingleTile(new Tile(TileType.TROPHY), 0, 4);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 1, 1);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 0);
                ret.setSingleTile(new Tile(TileType.CAT), 3, 3);
                ret.setSingleTile(new Tile(TileType.FRAME), 4, 1);
                ret.setSingleTile(new Tile(TileType.PLANT), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL10" -> {
                ret.setSingleTile(new Tile(TileType.PLANT), 0, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 1, 1);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 2, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 3, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 4, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }
            case "GOAL11" -> {
                ret.setSingleTile(new Tile(TileType.BOOK), 0, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 1, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 2, 2);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 4);
                ret.setSingleTile(new Tile(TileType.CAT), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
            }

            case "CommonSixGroups" -> {
                String[][] matrix = {
                        {"", "P", "", "", "F"},
                        {"", "P", "", "", "F"},
                        {"", "F", "", "P", ""},
                        {"", "F", "", "P", ""},
                        {"", "T", "", "", "T"},
                        {"", "T", "", "", "T"}
                };
                ret = setShelf(matrix);
            }
            case "CommonVertex" -> {
                String[][] matrix = {
                        {"C", "A", "A", "A", "C"},
                        {"A", "A", "A", "A", "A"},
                        {"A", "A", "", "", "A"},
                        {"A", "T", "", "", "C"},
                        {"C", "A", "T", "C", "A"},
                        {"C", "C", "A", "A", "C"}
                };
                ret = setShelf(matrix);
            }
            case "CommonFourGroups" -> {
                String[][] matrix = {
                        {"A", "C", "C", "C", "C"},
                        {"A", "", "A", "A", ""},
                        {"A", "", "F", "", "F"},
                        {"A", "F", "F", "F", "F"},
                        {"", "", "F", "", "F"},
                        {"A", "A", "A", "A", "F"}
                };

                ret = setShelf(matrix);
            }
            case "CommonSquares" -> {
                String[][] matrix = {
                        {"C", "C", "", "", ""},
                        {"C", "C", "", "", ""},
                        {"", "", "", "", ""},
                        {"", "", "", "B", "B"},
                        {"", "", "", "B", "B"},
                        {"", "", "", "", ""}
                };

                ret = setShelf(matrix);
            }
            case "CommonVertical0" -> {
                String[][] matrix = {
                        {"C", "", "T", "", "F"},
                        {"C", "", "T", "", "F"},
                        {"C", "", "T", "", "F"},
                        {"C", "", "T", "", "F"},
                        {"C", "", "T", "", "F"},
                        {"C", "", "T", "", "F"}
                };

                ret = setShelf(matrix);
            }
            case "CommonEight" -> {
                String[][] matrix = {
                        {"A", "A", "A", "A", "A"},
                        {"", "", "P", "A", "A"},
                        {"", "", "A", "", ""},
                        {"", "", "", "", ""},
                        {"", "", "", "", "A"},
                        {"", "", "A", "", "A"}
                };
                ret = setShelf(matrix);
            }
            case "CommonSameDiagonal" -> {
                String[][] matrix = {
                        {"T", "", "", "", "A"},
                        {"A", "T", "", "T", ""},
                        {"", "A", "T", "", ""},
                        {"", "T", "A", "T", ""},
                        {"T", "", "", "A", "A"},
                        {"", "", "", "", "A"}
                };
                ret = setShelf(matrix);
            }
            case "CommonHorizontal0" -> {
                String[][] matrix = {
                        {"C", "C", "C", "A", "P"},
                        {"C", "P", "C", "C", "C"},
                        {"", "", "", "", ""},
                        {"A", "C", "C", "C", "C"},
                        {"C", "A", "C", "C", "C"},
                        {"", "", "", "", ""}
                };
                ret = setShelf(matrix);
            }
            case "CommonVertical1" -> {
                String[][] matrix = {
                        {"", "P", "T", "F", ""},
                        {"", "C", "F", "P", ""},
                        {"", "C", "F", "P", ""},
                        {"", "P", "T", "F", ""},
                        {"", "P", "T", "F", ""},
                        {"", "P", "T", "F", ""}
                };

                ret = setShelf(matrix);
            }
            case "CommonHorizontal1" -> {
                String[][] matrix = {
                        {"", "", "", "", ""},
                        {"", "", "", "", ""},
                        {"", "", "", "", ""},
                        {"", "", "", "", ""},
                        {"C", "A", "T", "P", "F"},
                        {"P", "C", "F", "T", "A"}
                };
                ret = setShelf(matrix);
            }
            case "CommonX" -> {
                String[][] matrix = {
                        {"C", "", "T", "", ""},
                        {"", "P", "", "P", ""},
                        {"C", "", "P", "", ""},
                        {"", "P", "", "P", ""},
                        {"", "", "", "", ""},
                        {"", "", "", "", ""}
                };
                ret = setShelf(matrix);
            }
            case "CommonStair" -> {
                String[][] matrix = {
                        {"C", "", "", "", ""},
                        {"P", "C", "", "", ""},
                        {"F", "T", "C", "", ""},
                        {"F", "F", "A", "C", ""},
                        {"P", "A", "F", "T", "C"},
                        {"A", "P", "F", "C", "T"}
                };

                ret = setShelf(matrix);
            }
            default -> {
                return new Shelf();
            }
        }
        return ret;
    }
}
