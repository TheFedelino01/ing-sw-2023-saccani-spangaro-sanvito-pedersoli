package polimi.ingsw.main;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.networking.RMI.RMIServer;
import polimi.ingsw.view.networking.socket.server.SocketWelcome;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) throws IOException {

        System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);

        RMIServer.bind();

        SocketWelcome serverSOCKET = new SocketWelcome();
        serverSOCKET.start(DefaultValue.Default_port_Socket);

    }

}
