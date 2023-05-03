package polimi.ingsw.Remote;

import org.junit.jupiter.api.*;
import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UiSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.GameFlow;

import java.io.IOException;

@Disabled
public class RMITest {

    private static RMIClient client, client1, client2, client3, client4, client5, client6;
    private static View gui, gui1;
    private static RMIServer server;

    @BeforeAll
    static void setup() throws IOException {
        server = RMIServer.bind();

        gui = new GameFlow(ConnectionSelection.RMI, UiSelection.TUI);

        gui1 = new GameFlow(ConnectionSelection.RMI, UiSelection.TUI);

    }

    @Test
    @DisplayName("Tests a random interaction between two clients")
    void testTPGame() {

        client = new RMIClient(gui);
        client1 = new RMIClient(gui1);
    }


}
