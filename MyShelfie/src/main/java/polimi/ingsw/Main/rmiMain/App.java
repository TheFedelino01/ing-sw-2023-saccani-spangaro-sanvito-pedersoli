package polimi.ingsw.Main.rmiMain;

import polimi.ingsw.View.networking.RMI.RMIClient;
import polimi.ingsw.View.networking.RMI.RMIServer;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;

import java.rmi.RemoteException;

public class App {
    public static void main(String[] args) throws RemoteException {
        Flow gui1 = new GameFlow(ConnectionSelection.RMI, UISelection.TUI), gui2 = new GameFlow(ConnectionSelection.RMI, UISelection.TUI);
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
