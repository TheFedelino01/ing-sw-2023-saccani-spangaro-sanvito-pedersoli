package polimi.ingsw.model.interfaces;


public interface ShelfIC {
    TileIC[][] getShelf_IC();

    Integer getFreeSpace();

    Integer getOccupiedSpace();

    TileIC get_IC(int r, int c);

    String toString();

    String toString(int col);

    String toStringGoalCard();

    boolean isEmpty();

    int getNumofFreeSpacesInCol(int col);
}
