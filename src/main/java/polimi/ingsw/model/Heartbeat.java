package polimi.ingsw.model;

/**
 * Heartbeat's class<br>
 * This class implements the method we used to ping the server<br>
 * to let him know that we are still connected (needed for rmi connection)<br>
 */
public class Heartbeat {
    private final Long beat;
    private final String nick;

    public Heartbeat(Long beat, String nick) {
        this.beat = beat;
        this.nick = nick;
    }

    public Long getBeat() {
        return beat;
    }

    public String getNick() {
        return nick;
    }
}
