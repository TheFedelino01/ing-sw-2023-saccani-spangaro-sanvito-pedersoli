package polimi.ingsw.Main.rmiMain;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UiSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.GameFlow;

import java.rmi.RemoteException;

/**
 * Hello world!
 */
public class AppClient {
    public static void main(String[] args) throws RemoteException {
        View gui = new GameFlow(ConnectionSelection.RMI, UiSelection.TUI);

        RMIClient client = new RMIClient(gui);


        client.createGame("pino");

        client.setAsReady();

        /*while(gui.getLastModelReceived()==null){
            Thread.onSpinWait();
        }
        if(client.isMyTurn()) {
            client.grabTileFromPlayground(1, 3, Direction.RIGHT, 2);
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
            client.positionTileOnShelf(0,client.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
        }*/
    }
}
