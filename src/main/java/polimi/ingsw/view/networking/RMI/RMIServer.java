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

/**
 * RMIServer Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the RMI Network protocol
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * MainController of all the games
     */
    private final MainControllerInterface mainController;

    /**
     * RMIServer object
     */
    private static RMIServer serverObject = null;

    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Create a RMI Server
     * @return the instance of the server
     */
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


    /**
     * @return the istance of the RMI Server
     */
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

    /**
     * @return the registry associated with the RMI Server
     * @throws RemoteException
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    /**
     * A player requested, through the network, to create a new game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the new created game
     * @throws RemoteException
     */
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

    /**
     * A player requested, through the network, to join a random game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the first available game
     * @throws RemoteException
     */
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

    /**
     * A player requested, through the network, to join a specific game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the specific game
     * @throws RemoteException
     */
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

    /**
     * A player requested, through the network, to reconnect to a game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the game
     * @throws RemoteException
     */
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


    /**
     * A player requested, through the network, to leave a game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @param idGame of the game to leave
     * @return GameControllerInterface of the game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {

        serverObject.mainController.leaveGame(lis,nick,idGame);

        return null;
    }

    /**
     * Close the RMI Server
     * Used only for testing purposes
     *
     * @return RMI Server
     */
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
