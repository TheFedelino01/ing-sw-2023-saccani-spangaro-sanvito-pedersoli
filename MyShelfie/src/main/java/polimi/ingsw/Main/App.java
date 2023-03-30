package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        RMIClient client = new RMIClient();
        RMIClient client2 = new RMIClient();

        RMIServer server = RMIServer.bind();


        client.connect();
        client2.connect();

        client.createGame("pino");
        client2.createGame("paolo");
    }
}
