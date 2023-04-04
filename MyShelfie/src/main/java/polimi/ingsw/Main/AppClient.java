package polimi.ingsw.Main;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
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
    public static void main( String[] args ) throws RemoteException, InterruptedException {
        RMIClient client = new RMIClient();

        client.connect();

        client.createGame("pino");

        client.setAsReady();

        while(client.getLastModelReceived()==null){
            Thread.onSpinWait();
        }
        if(client.isMyTurn()) {
            client.grabTileFromPlayground(1, 3, Direction.RIGHT, 2);
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
        }
    }
}
