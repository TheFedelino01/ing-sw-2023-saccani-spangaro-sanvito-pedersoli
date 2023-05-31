package polimi.ingsw.model;

public class Heartbeat {
    private Long beat;
    private String nick;

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
