package polimi.ingsw.view.networking.RMI;

import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private final MainControllerInterface mainController;

    //if the registry is not static, java will garbage collect it, and the client will crash
    // basically a singleton on steroids
    private static RMIServer serverObject = null;
    private static Registry registry = null;

    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI);
            getRegistry().rebind(DefaultValue.Default_servername_RMI, serverObject);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    public synchronized static RMIServer getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {

        //before every call, need to recreate the stub, or java will just GC everything
        GameControllerInterface ris = serverObject.mainController.createGame(lis, nick);
        //The GameController and the Player have just created so, I need to set them as an Exportable Object

        try {
            UnicastRemoteObject.exportObject(ris, 0);
        }catch (RemoteException e){
            //Already exported, due to another RMI Client running on the same machine
        }
        //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
        System.out.println("[RMI] " + nick + " has created a new game");
        return ris;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {

        //Return the GameController already existed => not necessary to re-Export Object
        GameControllerInterface ris = serverObject.mainController.joinFirstAvailableGame(lis, nick);
        if (ris != null) {
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            System.out.println("[RMI] " + nick + " joined in first available game");
        }
        return ris;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {

        //Return the GameController already existed => not necessary to re-Export Object
        GameControllerInterface ris = serverObject.mainController.joinGame(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            System.out.println("[RMI] " + nick + " joined to specific game with id: " + idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {

        GameControllerInterface ris = serverObject.mainController.reconnect(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),DefaultValue.Default_port_RMI));
            //System.out.println("[RMI] "+nick+" joined to specific game with id: "+idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {

        serverObject.mainController.leaveGame(lis,nick,idGame);

        return null;
    }

    //for testing
    @Deprecated
    public static RMIServer unbind(){
        try {
            getRegistry().unbind(DefaultValue.Default_servername_RMI);
            UnicastRemoteObject.unexportObject(getRegistry(), true);
            System.out.println("Server RMI correctly closed");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
        } catch (NotBoundException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getInstance();
    }


}
