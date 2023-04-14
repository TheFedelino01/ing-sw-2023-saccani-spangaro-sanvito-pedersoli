package polimi.ingsw.Remote;

import org.junit.jupiter.api.*;
import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class RMITest {

    private static RMIClient client, client1,  client2, client3, client4, client5, client6;
    private static View gui, gui1;
    private static RMIServer server;

    @BeforeAll
    static void setup() throws IOException {
        server = RMIServer.bind();

        gui = new TextUI(ConnectionSelection.RMI);

        gui1 = new TextUI(ConnectionSelection.RMI);

    }

    @Test
    @DisplayName("Tests a random interaction between two clients")
    void testTPGame(){

        client = new RMIClient(gui);
        client1 = new RMIClient(gui1);
    }


}
