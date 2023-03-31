package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject{

    private MainControllerInterface requests;
    private GameListener responses;

    private ClientResponsesInterface gameController=null;
    private PlayerInterface player=null;

    public RMIClient() throws RemoteException {
        super();
    }
    public boolean connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(4321);
            requests = (MainControllerInterface) registry.lookup("myShelfie");
            responses = (GameListener) UnicastRemoteObject.exportObject(new GameListenersHandler(),0);

            System.out.println("Client RMI ready");
            return true;
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public void createGame(String nick){
        try {
            RemoteResultInterface ris = requests.createGame(responses,nick);
            gameController=ris.getGameControllerInterface();
            player=ris.getPlayerIdentity();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinFirstAvailable(String nick){
        try {
            RemoteResultInterface ris = requests.joinFirstAvailableGame(responses,nick);
            if(ris!=null){
                gameController=ris.getGameControllerInterface();
                player=ris.getPlayerIdentity();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void joinGame(String nick, int idGame){
        try {
            RemoteResultInterface ris = requests.joinGame(responses,nick,idGame);
            if(ris!=null){
                gameController=ris.getGameControllerInterface();
                player=ris.getPlayerIdentity();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAsReady(){
        try {
            if(gameController!=null){
                gameController.playerIsReadyToStart(player.getNickname());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
