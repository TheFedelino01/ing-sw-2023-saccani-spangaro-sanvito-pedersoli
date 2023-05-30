package polimi.ingsw.view.userView;

import org.junit.jupiter.api.*;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.networking.RMI.RMIServer;
import polimi.ingsw.view.networking.socket.client.ClientSocket;
import polimi.ingsw.view.networking.socket.server.SocketWelcome;
import polimi.ingsw.view.userView.gui.GUI;
import polimi.ingsw.view.userView.gui.GUIApplication;
import polimi.ingsw.view.userView.utilities.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameFlowTest {

    private static RMIServer server;
    private static SocketWelcome serverSocket;
    protected InputParser inputParser;
    protected InputReader inputReader;
    private GameFlow gameFlow;
    private CommonClientActions clientActions;
    private UI ui;
    private GUIApplication guiApplication;

    @BeforeAll
    public static void setUp() throws IOException {
        System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        server = new RMIServer();
        server = RMIServer.bind();
        serverSocket = new SocketWelcome();
        serverSocket.start(DefaultValue.Default_port_Socket);
    }

    @AfterAll
    public static void kill() {
        //unbinds the RMI server
        server = RMIServer.unbind();

        //kills the serverSocket
        serverSocket.stopConnection();
        System.out.println("Server socket correctly closed");
    }

    @Test
    public void testingRun() {
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(connectionSelection);
        gameFlow.youleft();
        gameFlow.setEnded(true);
        Assertions.assertTrue(gameFlow.isEnded());
        FileDisconnection fileDisconnection = gameFlow.getFileDisconnection();
        assertNotNull(fileDisconnection);
    }

    @Test
    public void testRun() {
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        GameFlow gf = new GameFlow(connectionSelection);
        BufferData buffer = gf.getBuffer_ForTesting();


        buffer.addData("c");

        buffer.addData("test");

        while(gf.isEnded_ForTesting()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}