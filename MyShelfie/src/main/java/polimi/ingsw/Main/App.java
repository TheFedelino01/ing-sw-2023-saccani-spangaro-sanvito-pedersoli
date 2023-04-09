package polimi.ingsw.Main;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.rmi.RemoteException;

public class App {
    public static void main( String[] args ) throws RemoteException {
        View gui1 = new TextUI(ConnectionSelection.RMI),gui2 = new TextUI(ConnectionSelection.RMI);
        RMIServer.bind();

        RMIClient client = new RMIClient(gui1);
        RMIClient client2 = new RMIClient(gui2);

        client.createGame("pino");
        client2.joinFirstAvailable("ugo");

        client.setAsReady();

        client2.setAsReady();
        /*
        if(client.isMyTurn()) {
            client.grabTileFromPlayground(1, 3, Direction.RIGHT, 2);
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
        }else{
            client2.grabTileFromPlayground(1, 3, Direction.RIGHT, 2);
            client2.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
            client2.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
        }*/
    }
}
