package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;

import java.io.IOException;
import java.rmi.RemoteException;

public class AppSocket {

    private final static String ip = "127.0.0.1";
    private final static int port = 4567;
    private static ClientSocket client, client2;
    private static SocketWelcome server;

    public static void main(String[] args) throws IOException {

        server = new SocketWelcome();
        server.start(port);
        client = new ClientSocket();
        client.startConnection(ip, port);
        client2 = new ClientSocket();
        client2.startConnection(ip, port);
        client.createGame("pippo");
        client2.joinFirstAvailable("ugo");

    }
}
