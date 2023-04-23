package polimi.ingsw.Main;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.socket.server.SocketWelcome;

import java.io.IOException;

public class RmiSocketServers {

    public static void main(String[] args) throws IOException {

        RMIServer serverRMI = RMIServer.bind();

        SocketWelcome serverSOCKET = new SocketWelcome();
        serverSOCKET.start(DefaultValue.Default_port_Socket);

    }

}
