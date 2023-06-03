package polimi.ingsw.view.networking.socket.client;

import org.junit.jupiter.api.*;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.networking.socket.client.ClientSocket;
import polimi.ingsw.networking.socket.server.Server;
import polimi.ingsw.view.flow.ConnectionSelection;
import polimi.ingsw.view.flow.GameFlow;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
class ClientSocketTest {
    private static GameFlow gameFlow;
    private static ClientSocket clientSocket;
    private static Server serverSocket;

    @BeforeEach
    void startServer() throws IOException {
        serverSocket = new Server();
        serverSocket.start(DefaultValue.Default_port_Socket);
        gameFlow = new GameFlow(ConnectionSelection.SOCKET);
        clientSocket = new ClientSocket(gameFlow);
    }


    @AfterEach
    void tearDown() throws IOException {
        clientSocket.stopConnection();
        serverSocket.stopConnection();
    }

    @Test
    void createGameTest() throws IOException {
        clientSocket.createGame("TestPlayer");
        assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
    }

    @Test
    void joinFirstAvailableTest() throws IOException {
        // Call the joinFirstAvailable() method
        clientSocket.joinFirstAvailable("TestPlayer");
        clientSocket.setAsReady();
    }

    @Test
    void joinGameTest() throws IOException {
        clientSocket.joinGame("TestPlayer", (int) clientSocket.getId());
    }
    @Test
    void Reconnection() throws IOException, InterruptedException {
        int id = (int) clientSocket.getId();
        clientSocket.leave("TestPlayer", id);
        clientSocket.reconnect("TestPlayer", id);
        Assertions.assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
        assertEquals(clientSocket.getId(), id);
        //assertFalse(clientSocket.isMyTurn());
    }

    @Test
    void sendMessageTest() throws IOException {
        Player player = new Player("TestPlayer");
        Message msg = new Message("TestPlayer", player);
        clientSocket.sendMessage(msg);
        clientSocket.positionTileOnShelf(1, TileType.BOOK);
        assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
        clientSocket.grabTileFromPlayground(1, 1, Direction.UP, 1);
        assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
    }
}