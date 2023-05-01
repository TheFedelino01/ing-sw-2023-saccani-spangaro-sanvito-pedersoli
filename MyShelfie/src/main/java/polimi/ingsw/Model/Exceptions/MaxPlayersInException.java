package polimi.ingsw.Model.Exceptions;

public class MaxPlayersInException extends RuntimeException {
    public MaxPlayersInException() {
        super("Max number of players in reached");
    }

}