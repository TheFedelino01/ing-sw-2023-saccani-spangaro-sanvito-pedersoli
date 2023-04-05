package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;

import java.io.IOException;

public class AppClient2 {
    private static ClientSocket client2;

    public static void main(String[] args) throws IOException {

        client2 = new ClientSocket();


        client2.joinFirstAvailable("ugo");

        client2.setAsReady();

    }
}
