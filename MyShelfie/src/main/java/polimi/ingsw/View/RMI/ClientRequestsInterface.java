package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;
import polimi.ingsw.Model.Exceptions.NotAvailableGamesException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRequestsInterface extends Remote {


    ControllerAndPlayer createGame(GameListener lis, String nick) throws RemoteException;
    ControllerAndPlayer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;
    ControllerAndPlayer joinGame(GameListener lis, String nick, Integer idGame) throws RemoteException;


}
