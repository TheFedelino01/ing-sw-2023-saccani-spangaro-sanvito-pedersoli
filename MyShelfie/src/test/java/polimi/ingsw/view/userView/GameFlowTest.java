package polimi.ingsw.view.userView;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private GameFlow gameFlow;

    @BeforeEach
    public void setUp() throws IOException {

        System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);

        server = new RMIServer();
        server = RMIServer.bind();

        SocketWelcome serverSOCKET = new SocketWelcome();
        serverSOCKET.start(DefaultValue.Default_port_Socket);


    }

    @Test
    public void testConstructorWithConnectionSelection_Socket() {
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor
    }

    @Test
    public void testConstructorWithConnectionSelection_RMI() {
        ConnectionSelection connectionSelection = ConnectionSelection.RMI;
        gameFlow = new GameFlow(connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor
    }

    @Test
    public void testConstructorWithGUIApplicationAndConnectionSelection_Socket() {
        GUIApplication guiApplication = new GUIApplication();
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(guiApplication, connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor
    }

    @Test
    public void testConstructorWithGUIApplicationAndConnectionSelection_RMI() {
        GUIApplication guiApplication = new GUIApplication();
        ConnectionSelection connectionSelection = ConnectionSelection.RMI;
        gameFlow = new GameFlow(guiApplication, connectionSelection);

        assertNotNull(gameFlow);
        // Assert any other conditions or behaviors you expect from the constructor
    }

    @Test
    public void testingRun() {
        ConnectionSelection connectionSelection = ConnectionSelection.SOCKET;
        gameFlow = new GameFlow(connectionSelection);
        gameFlow.youleft();
        gameFlow.setEnded(true);
        Assertions.assertEquals(gameFlow.isEnded(), true);
        FileDisconnection fileDisconnection = gameFlow.getFileDisconnection();
        assertNotNull(fileDisconnection);
    }

}