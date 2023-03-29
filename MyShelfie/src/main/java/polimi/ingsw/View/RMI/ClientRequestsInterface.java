package polimi.ingsw.View.RMI;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRequestsInterface extends Remote {


    GameController createGame(GameListener lis, Player p) throws RemoteException;
    GameController joinFirstAvailableGame(GameListener lis, Player p) throws RemoteException;
    GameController joinGame(GameListener lis, Player p,Integer idGame) throws RemoteException;
}
