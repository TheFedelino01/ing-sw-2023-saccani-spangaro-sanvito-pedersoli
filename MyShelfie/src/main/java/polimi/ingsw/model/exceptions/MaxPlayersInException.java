package polimi.ingsw.model.exceptions;

public class MaxPlayersInException extends RuntimeException {
    public MaxPlayersInException() {
        super("Max number of players in reached");
    }

}