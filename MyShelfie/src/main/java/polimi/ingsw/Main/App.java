package polimi.ingsw.Main;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;

import java.rmi.RemoteException;

public class App {
    public static void main( String[] args ) throws RemoteException, InterruptedException {

        RMIServer server = RMIServer.bind();

        RMIClient client = new RMIClient();
        RMIClient client2 = new RMIClient();

        client.connect();
        client2.connect();


        client.createGame("pino");
        client2.joinFirstAvailable("ugo");

        client.setAsReady();

        client2.setAsReady();

        Thread.sleep(1000);

        if(client.isMyTurn()) {
            client.grabTileFromPlayground(1, 3, Direction.RIGHT, 1);
        }else{
            client2.grabTileFromPlayground(1, 3, Direction.RIGHT, 1);
        }
    }
}
