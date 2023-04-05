package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;

import java.io.IOException;

public class AppClient {
    private static ClientSocket client;

    public static void main(String[] args) throws IOException {
        client = new ClientSocket();


        client.createGame("pippo");


        client.setAsReady();


    }
}
