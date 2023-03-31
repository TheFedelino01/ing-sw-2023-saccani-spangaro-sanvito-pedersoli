package polimi.ingsw.View.RMI;

import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private MainControllerInterface mainController;

    public static RMIServer bind(){
        RMIServer obj=null;
        try {
            obj = new RMIServer();
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(4321);
            registry.rebind("myShelfie", obj);


            System.out.println("Server RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
        return obj;
    }

    public RMIServer() throws RemoteException{
        super();
        mainController = (MainControllerInterface) UnicastRemoteObject.exportObject(MainController.getInstance(),0);
    }
    @Override
    public ControllerAndPlayer createGame(GameListener lis, String nick) throws RemoteException {
        return mainController.createGame(lis,nick);
    }

    @Override
    public ControllerAndPlayer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return mainController.joinFirstAvailableGame(lis,nick);
    }

    @Override
    public ControllerAndPlayer joinGame(GameListener lis, String nick, Integer idGame) throws RemoteException {
        return mainController.joinGame(lis,nick,idGame);
    }

}
