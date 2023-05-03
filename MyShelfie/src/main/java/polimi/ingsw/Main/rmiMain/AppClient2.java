package polimi.ingsw.Main.rmiMain;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UiSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.GameFlow;

import java.rmi.RemoteException;

public class AppClient2 {
    public static void main(String[] args) throws RemoteException {
        View gui = new GameFlow(ConnectionSelection.RMI, UiSelection.TUI);
        RMIClient client2 = new RMIClient(gui);


        client2.joinFirstAvailable("ugo");

        client2.setAsReady();

       /* while(client2.getLastModelReceived()==null){
            Thread.onSpinWait();
        }

        if(client2.isMyTurn()) {
            client2.grabTileFromPlayground(1, 3, Direction.RIGHT, 2);
            client2.positionTileOnShelf(0,client2.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
            client2.positionTileOnShelf(0,client2.getLastModelReceived().getHandOfCurrentPlaying().get(0).getType());
        }*/
    }
}
