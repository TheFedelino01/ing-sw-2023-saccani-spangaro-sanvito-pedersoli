package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.networking.socket.client.ClientSocket;
import polimi.ingsw.View.networking.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;

import java.io.IOException;

public class AppSocket {


    private static ClientSocket client, client2;
    private static SocketWelcome server;

    public static void main(String[] args) throws IOException {

        server = new SocketWelcome();
        server.start(DefaultValue.Default_port_Socket);

        Flow gui1 = new GameFlow(ConnectionSelection.SOCKET, UISelection.TUI), gui2 = new GameFlow(ConnectionSelection.SOCKET, UISelection.TUI);

        client = new ClientSocket(gui1);

        client2 = new ClientSocket(gui2);

        client.setAsReady();

        client2.setAsReady();

    }
}
