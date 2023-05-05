package polimi.ingsw.Remote;

import org.junit.jupiter.api.*;
import polimi.ingsw.View.networking.RMI.RMIClient;
import polimi.ingsw.View.networking.RMI.RMIServer;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;

import java.io.IOException;

@Disabled
public class RMITest {

    private static RMIClient client, client1, client2, client3, client4, client5, client6;
    private static Flow gui, gui1;
    private static RMIServer server;

    @BeforeAll
    static void setup() throws IOException {
        server = RMIServer.bind();

        gui = new GameFlow(ConnectionSelection.RMI, UISelection.TUI);

        gui1 = new GameFlow(ConnectionSelection.RMI, UISelection.TUI);

    }

    @Test
    @DisplayName("Tests a random interaction between two clients")
    void testTPGame() {

        client = new RMIClient(gui);
        client1 = new RMIClient(gui1);
    }


}
