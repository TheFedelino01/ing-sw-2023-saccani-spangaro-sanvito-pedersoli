package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIClient;

import java.rmi.RemoteException;

public class AppClient2 {
    public static void main( String[] args ) throws RemoteException {
        RMIClient client2 = new RMIClient();

        client2.connect();

        client2.joinFirstAvailable("ugo");

        client2.setAsReady();
    }
}
