package polimi.ingsw.view.networking.RMI;

import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private MainControllerInterface mainController;

    //if the registry is not static, java will garbage collect it, and the client will crash
    private static Registry registry;

    public static RMIServer bind() {
        RMIServer obj = null;
        try {
            obj = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI);
            registry.rebind(DefaultValue.Default_servername_RMI, obj);


            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return obj;
    }


    public RMIServer() throws RemoteException {
        super();
        mainController = MainController.getInstance();
    }

    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        GameControllerInterface ris = mainController.createGame(lis, nick);
        //The GameController and the Player have just created so, I need to set them as an Exportable Object

        try {
            UnicastRemoteObject.exportObject(ris, DefaultValue.Default_port_RMI);
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
        GameControllerInterface ris = mainController.joinFirstAvailableGame(lis, nick);
        if (ris != null) {
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            try {
                UnicastRemoteObject.exportObject(ris, DefaultValue.Default_port_RMI);
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
        GameControllerInterface ris = mainController.joinGame(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, DefaultValue.Default_port_RMI);
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
        GameControllerInterface ris = mainController.reconnect(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, DefaultValue.Default_port_RMI);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            //System.out.println("[RMI] "+nick+" joined to specific game with id: "+idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        mainController.leaveGame(lis,nick,idGame);

        return null;
    }


}
