package polimi.ingsw.model.interfaces;


/**
 * This interface is implemented by Shelf
 */
public interface ShelfIC {
    /**
     * This method return the shelf in a matrix form
     * @return the shelf in a matrix form {@link TileIC}
     */
    TileIC[][] getShelf_IC();

    /**
     * This method return the number of free spaces in the shelf
     * @return the number of free spaces in the shelf
     */
    Integer getFreeSpace();

    /**
     * This method return the number of occupied spaces in the shelf
     * @return the number of occupied spaces in the shelf
     */
    Integer getOccupiedSpace();

    /**
     * This method return the single tile in the shelf
     * @param r row
     * @param c column
     * @return the single tile in the shelf {@link TileIC}
     */
    TileIC get_IC(int r, int c);

    /**
     * To string method
     * @return the string representation of the shelf
     */
    String toString();

    /**
     * This method is used to get the string representation of a single column of the shelf
     * @param col number of the column
     * @return the string representation of the shelf
     */
    String toString(int col);

    /**
     * This method is used to get the string representation of the goal card
     * @return the string representation of the goal card
     */
    String toStringGoalCard();

    /**
     * This method is used to check if the shelf is empty
     * @return true if the shelf is empty
     */
    boolean isEmpty();

    /**
     * This method is used to get the number of free spaces in a column
     * @param col number of the column
     * @return the number of free spaces in a column
     */
    int getNumofFreeSpacesInCol(int col);
}
