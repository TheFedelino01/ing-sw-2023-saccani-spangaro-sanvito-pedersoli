package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.io.IOException;
import java.rmi.RemoteException;

public class AppSocket {


    private static ClientSocket client, client2;
    private static SocketWelcome server;

    public static void main(String[] args) throws IOException {

        server = new SocketWelcome();
        server.start(DefaultValue.Default_port_Socket);

        View gui1= new TextUI(),gui2 = new TextUI();

        client = new ClientSocket(gui1);

        client2 = new ClientSocket(gui2);


        client.createGame("pippo");


        client2.joinFirstAvailable("ugo");

        client.setAsReady();

        client2.setAsReady();

    }
}
