package polimi.ingsw.model.enumeration;

import java.io.Serializable;

/**
 * This enum represents the game status
 */
public enum GameStatus implements Serializable {
    WAIT,
    RUNNING,
    LAST_CIRCLE,
    ENDED
}
