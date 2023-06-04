package polimi.ingsw.model.exceptions;

/**
 * This exception is thrown when a player tries to position a tile that he has not grabbed
 */
public class PositioningATileNotGrabbedException extends RuntimeException {
    public PositioningATileNotGrabbedException() {
        super();
    }
}
