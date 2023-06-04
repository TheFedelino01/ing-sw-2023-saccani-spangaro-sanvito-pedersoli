package polimi.ingsw.model.exceptions;

/**
 * This exception is thrown when a player that is not playing tries to perform an action
 */
public class NotPlayerTurnException extends RuntimeException {
    public NotPlayerTurnException() {
        super();
    }
}
