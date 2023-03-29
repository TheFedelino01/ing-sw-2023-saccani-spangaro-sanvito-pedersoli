package polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.client.ClientBase;
import polimi.ingsw.server.Server;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class SocketTest {
    private static ClientBase client;
    private static Server server;

    @BeforeEach
    void setup() throws IOException {
        server = new Server();
        server.start(3000);
        client = new ClientBase();
        client.startConnection("127.0.0.1", 3000);
    }

    @AfterEach
    void tearDown() throws IOException {
        client.stopConnection();
        //TODO: close the server connection without it throwing an exception
        server.stopConnection();
    }

    @Test
    @DisplayName("Test connection")
    void testConnection() throws IOException, ClassNotFoundException {
        String resp1 = client.sendMsg("ciao");
        String resp2 = client.sendMsg("ciao again");
        String resp3 = client.sendMsg(".");

        assertEquals("ciao", resp1);
        assertEquals("ciao again", resp2);
        assertEquals("Ciao Ciao", resp3);
    }

}
