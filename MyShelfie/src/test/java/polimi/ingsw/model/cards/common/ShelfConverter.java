package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

public class ShelfConverter {
    protected static Shelf setShelf(String[][] shelf) {
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
}
