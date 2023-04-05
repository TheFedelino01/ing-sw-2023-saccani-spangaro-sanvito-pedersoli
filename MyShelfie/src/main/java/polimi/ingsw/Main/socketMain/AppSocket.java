package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;

import java.io.IOException;
import java.rmi.RemoteException;

public class AppSocket {


    private static ClientSocket client, client2;
    private static SocketWelcome server;

    public static void main(String[] args) throws IOException {

        server = new SocketWelcome();
        server.start(DefaultValue.Default_port_Socket);

        client = new ClientSocket();

        client2 = new ClientSocket();


        client.createGame("pippo");


        client2.joinFirstAvailable("ugo");

        client.setAsReady();

        client2.setAsReady();

    }
}
