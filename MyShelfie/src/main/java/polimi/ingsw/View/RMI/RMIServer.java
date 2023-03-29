package polimi.ingsw.View.RMI;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.View;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer implements ClientRequestsInterface {

    private MainController mainController = MainController.getInstance();
    public RMIServer(){
        try {
            RMIServer obj = new RMIServer();
            RMIServer stub = (RMIServer) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("myShelfie", stub);

            System.err.println("Server RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public GameController createGame(GameListener lis, Player p) throws RemoteException {
        GameController c = mainController.createGame(p);
        c.addListener(lis,p);
        return c;
    }

    @Override
    public GameController joinFirstAvailableGame(GameListener lis, Player p) throws RemoteException {
        GameController c = mainController.joinFirstGameAvailable(p);
        c.addListener(lis,p);
        return c;
    }

    @Override
    public GameController joinGame(GameListener lis, Player p, Integer idGame) throws RemoteException {
        GameController c = mainController.joinGame(p,idGame);
        c.addListener(lis,p);
        return c;
    }

}
