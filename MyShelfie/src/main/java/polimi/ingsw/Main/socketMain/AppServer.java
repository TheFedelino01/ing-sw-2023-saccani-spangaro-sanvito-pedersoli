package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.networking.socket.server.SocketWelcome;

import java.io.IOException;

public class AppServer {
    public static void main(String[] args) throws IOException {

        SocketWelcome server = new SocketWelcome();
        server.start(DefaultValue.Default_port_Socket);


    }
}
