package polimi.ingsw.Main.rmiMain;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AppServer {
    public static void main( String[] args ) throws RemoteException {

        RMIServer server = RMIServer.bind();

    }
}
