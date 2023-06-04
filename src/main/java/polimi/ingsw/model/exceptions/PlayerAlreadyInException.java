package polimi.ingsw.model.exceptions;

/**
 * This exception is thrown when a player is already in the game
 */
public class PlayerAlreadyInException extends RuntimeException {
    public PlayerAlreadyInException() {
        super("Player already in exception");
    }

}
