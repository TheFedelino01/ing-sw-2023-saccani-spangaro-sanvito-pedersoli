package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

public abstract class CommonMethods extends CommonCard {

    @Override
    public abstract boolean verify(Shelf toCheck);

    public CommonMethods(CardCommonType type) {
        super(type);
    }

    public static Shelf getCopy(Shelf toCopy){
        Shelf temp = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                temp.setSingleTile(new Tile(toCopy.get(r, c).getType()), r, c);
            }
        }
        return temp;
    }

    public static int checkAdjacent(TileType typeToCheck, Shelf temp, int r, int c) {
        int res = 0, col, row = 0;
        boolean found = false;

        if (temp.get(r, c).isSameType(typeToCheck)) {
            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            res++;
        } else {
            while (row < DefaultValue.NumOfRowsShelf && !found) {
                col = 0;
                while (col < DefaultValue.NumOfColumnsShelf && !found) {
                    if (temp.get(row, col).isSameType(typeToCheck)) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), row, col);
                        res++;
                        found = true;
                        r = row;
                        c = col;
                    }
                    col++;
                }
                row++;
            }
        }
        switch (r) {
            case (0) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, 0);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            case (DefaultValue.NumOfRowsShelf - 1) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            default -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                }
            }
        }
    }

}
