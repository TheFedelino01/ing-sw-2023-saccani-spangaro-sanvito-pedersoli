package polimi.ingsw.View.RMI;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRequestsInterface extends Remote {


    ControllerAndPlayer createGame(GameListener lis, Player p) throws RemoteException;
    ControllerAndPlayer joinFirstAvailableGame(GameListener lis, Player p) throws RemoteException;
    ControllerAndPlayer joinGame(GameListener lis, Player p,Integer idGame) throws RemoteException;

    record ControllerAndPlayer(GameControllerInterface controller, Player player) implements Serializable {
    }
}
