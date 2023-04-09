package polimi.ingsw.Main.socketMain;

import org.w3c.dom.Text;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.io.IOException;

public class AppClient {
    private static ClientSocket client;

    public static void main(String[] args) throws IOException {
        View gui = new TextUI(ConnectionSelection.SOCKET);

        client = new ClientSocket(gui);


        client.createGame("pippo");


        client.setAsReady();


    }
}
