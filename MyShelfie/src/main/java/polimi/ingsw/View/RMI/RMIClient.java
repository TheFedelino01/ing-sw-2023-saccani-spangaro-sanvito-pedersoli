package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject{

    private ClientRequestsInterface requests;
    private GameListener responses;

    private ClientResponsesInterface gameController=null;
    private Player player=null;

    public RMIClient() throws RemoteException {
        super();
    }
    public boolean connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(4321);
            requests = (ClientRequestsInterface) registry.lookup("myShelfie");
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
            ControllerAndPlayer ris = requests.createGame(responses,nick);
            gameController=ris.getGameControllerInterface();
            player=ris.getPlayerIdentity();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinFirstAvailable(String nick){
        try {
            ControllerAndPlayer ris = requests.joinFirstAvailableGame(responses,nick);
            if(ris!=null){
                gameController=ris.getGameControllerInterface();
                player=ris.getPlayerIdentity();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

}
