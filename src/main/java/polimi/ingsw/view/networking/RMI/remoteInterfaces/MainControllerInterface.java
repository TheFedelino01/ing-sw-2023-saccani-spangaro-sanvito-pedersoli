package polimi.ingsw.view.networking.RMI.remoteInterfaces;

import polimi.ingsw.listener.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {
    GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException;

    GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;

    GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException;


    GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException;
}
