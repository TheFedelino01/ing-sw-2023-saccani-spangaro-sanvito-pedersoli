package polimi.ingsw.View.RMI;

import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements ClientRequestsInterface {

    private MainController mainController = MainController.getInstance();
    public static RMIServer bind(){
        RMIServer obj=null;
        try {
            obj = new RMIServer();
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(4321);
            registry.rebind("myShelfie", obj);

            System.err.println("Server RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
        return obj;
    }

    public RMIServer() throws RemoteException{
        super();
    }
    @Override
    public ControllerAndPlayer createGame(GameListener lis, String nick) throws RemoteException {
        return mainController.createGame(nick,lis);
    }

    @Override
    public ControllerAndPlayer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return mainController.joinFirstGameAvailable(nick, lis);
    }

    @Override
    public ControllerAndPlayer joinGame(GameListener lis, String nick, Integer idGame) throws RemoteException {
        return mainController.joinGame(nick,0,lis);
    }

}
