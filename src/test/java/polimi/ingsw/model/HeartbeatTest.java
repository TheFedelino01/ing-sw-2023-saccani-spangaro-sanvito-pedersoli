package polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeartbeatTest {

    @Test
    public void testGetBeat() {
        Long beat = 12345L;
        String nick = "John";
        Heartbeat heartbeat = new Heartbeat(beat, nick);

        Assertions.assertEquals(beat, heartbeat.getBeat());
    }

    @Test
    public void testGetNick() {
        Long beat = 12345L;
        String nick = "John";
        Heartbeat heartbeat = new Heartbeat(beat, nick);

        Assertions.assertEquals(nick, heartbeat.getNick());
    }
}