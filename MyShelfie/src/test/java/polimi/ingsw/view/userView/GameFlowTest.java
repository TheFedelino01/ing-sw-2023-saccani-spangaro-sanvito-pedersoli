package polimi.ingsw.view.userView;

import org.junit.jupiter.api.*;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.view.networking.RMI.RMIServer;
import polimi.ingsw.view.networking.socket.server.SocketWelcome;
import polimi.ingsw.view.userView.gui.GUIApplication;
import polimi.ingsw.view.userView.utilities.FileDisconnection;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameFlowTest {

    private static RMIServer server;
    private static SocketWelcome serverSocket;
    private GameFlow gameFlow;

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
    public void testConstructorWithConnectionSelection_Socket() {
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor

        //until new test parts are added, need to kill the game, or it will
        // run forever
        gameFlow.setEnded(true);
    }

    @Test
    public void testConstructorWithConnectionSelection_RMI() {
        ConnectionSelection connectionSelection = ConnectionSelection.RMI;
        gameFlow = new GameFlow(connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor

        //until new test parts are added, need to kill the game, or it will
        // run forever
        gameFlow.setEnded(true);
    }

    @Test
    public void testConstructorWithGUIApplicationAndConnectionSelection_Socket() {
        GUIApplication guiApplication = new GUIApplication();
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(guiApplication, connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor

        //until new test parts are added, need to kill the game, or it will
        // run forever
        gameFlow.setEnded(true);
    }

    @Test
    public void testConstructorWithGUIApplicationAndConnectionSelection_RMI() {
        GUIApplication guiApplication = new GUIApplication();
        ConnectionSelection connectionSelection = ConnectionSelection.RMI;
        gameFlow = new GameFlow(guiApplication, connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor

        //until new test parts are added, need to kill the game, or it will
        // run forever
        gameFlow.setEnded(true);
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

}