package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

public class ShelfConverter {
    protected Shelf setShelf(String[][] shelf) {
        int rows = DefaultValue.NumOfRowsShelf;
        int cols = DefaultValue.NumOfColumnsShelf;
        Shelf ris = new Shelf();
        TileType tmp = null;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                switch (shelf[r][c].toUpperCase()) {
                    case "C" -> tmp = TileType.CAT;
                    case "B" -> tmp = TileType.BOOK;
                    case "A" -> tmp = TileType.ACTIVITY;
                    case "F" -> tmp = TileType.FRAME;
                    case "T" -> tmp = TileType.TROPHY;
                    case "P" -> tmp = TileType.PLANT;
                    case "FIN" -> tmp = TileType.FINISHED_USING;
                    case "X" -> tmp = TileType.NOT_USED;
                    case "" -> tmp = TileType.NOT_USED;
                }

                ris.setSingleTile(new Tile(tmp), r, c);
            }
        }
        return ris;
    }
}
