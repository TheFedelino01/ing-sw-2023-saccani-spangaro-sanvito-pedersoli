package polimi.ingsw.model.interfaces;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.enumeration.TileType;

import static org.fusesource.jansi.Ansi.ansi;

public interface ShelfIC {
    public TileIC[][] getShelf_IC();

    public Integer getFreeSpace();

    public Integer getOccupiedSpace();

    public TileIC get_IC(int r, int c);

    public String toString();

    public String toString(int col);

    public String toStringGoalCard();

    public boolean isEmpty();

    public int getNumofFreeSpacesInCol(int col);
}
