package polimi.ingsw.Remote;

import org.junit.jupiter.api.*;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

@Disabled
public class SocketTest {

    private static ClientSocket client, client1, client2, client3, client4, client5, client6;
    private static Robot keyboardBot;
    private static View gui, gui1, gui2, gui3, gui4, gui5, gui6;
    private static SocketWelcome server;

    @BeforeAll
    static void setup() throws IOException, AWTException {
        keyboardBot = new Robot();
        server = new SocketWelcome();
        server.start(DefaultValue.Default_port_Socket);


        /*
        gui1 = new TextUI(ConnectionSelection.SOCKET);
        gui2 = new TextUI(ConnectionSelection.SOCKET);
        gui3 = new TextUI(ConnectionSelection.SOCKET);
        gui4 = new TextUI(ConnectionSelection.SOCKET);
        gui5 = new TextUI(ConnectionSelection.SOCKET);
        gui6 = new TextUI(ConnectionSelection.SOCKET);
        */
    }

    @AfterAll
    static void tearDown() throws IOException {
        //client.stopConnection();
        //client1.stopConnection();
        //client2.stopConnection();
        //client3.stopConnection();
        //client4.stopConnection();
        server.stopConnection();
    }

    @Test
    @DisplayName("Test connection")
    void testConnection() throws IOException {
        String nick1 = "player1";
        String nick2 = "player2";
        gui = new TextUI(ConnectionSelection.SOCKET);
        gui1 = new TextUI(ConnectionSelection.SOCKET);
        client = new ClientSocket(gui);
        keyboardBot.keyPress(KeyEvent.VK_C);
        client1 = new ClientSocket(gui1);


    }
    /*
    @Test
    @DisplayName("Test multiple connection")
    void testMulConnection() throws IOException, ClassNotFoundException {
        client1 = new ClientSocket(gui1);
        client2 = new ClientSocket(gui2);
        client3 = new ClientSocket(gui3);

        String respCl10 = client1.sentMessage("ciao");
        String respCl11 = client1.sentMessage("ciao again");
        String respCl12 = client1.sentMessage(".");

        String respCl20 = client2.sentMessage("bau");
        String respCl21 = client2.sentMessage("miao");
        String respCl22 = client2.sentMessage(".");

        String respCl30 = client3.sentMessage("test magico");
        String respCl31 = client3.sentMessage("test magico 2");
        String respCl32 = client3.sentMessage(".");


        assertEquals("ciao", respCl10);
        assertEquals("bau", respCl20);
        assertEquals("test magico", respCl30);
        assertEquals("ciao again", respCl11);
        assertEquals("miao", respCl21);
        assertEquals("test magico 2", respCl31);
        assertEquals("Ciao Ciao", respCl12);
        assertEquals("Ciao Ciao", respCl22);
        assertEquals("Ciao Ciao", respCl32);
    }

    @Test
    @DisplayName("Test for lots of messages")
    void testInfinite() throws IOException, ClassNotFoundException {
        client4 = new ClientSocket(gui4);
        for (int i = 0; i < 10; i++) {
            String ciao = client4.sentMessage("ciao");
            assertEquals("ciao", ciao);
        }
        //Closes the connection
        client4.sentMessage(".");
    }

    @Test
    @DisplayName("Tests a random interaction between two clients")
    void testTPGame() {
        client5 = new ClientSocket(gui5);
        client6 = new ClientSocket(gui6);

    }


     */

}
