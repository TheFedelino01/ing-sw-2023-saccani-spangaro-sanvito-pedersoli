package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 *
 */
public class AppClient
{
    public static void main( String[] args ) throws RemoteException {
        RMIClient client = new RMIClient();


        client.connect();


        client.createGame("pino");


        client.setAsReady();

    }
}