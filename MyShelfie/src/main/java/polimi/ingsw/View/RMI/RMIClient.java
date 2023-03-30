package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient {

    private ClientRequestsInterface requests;
    private GameListener responses;


    public RMIClient(){

    }
    public boolean connect(){
        try {

            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

            requests = (ClientRequestsInterface) registry.lookup("myShelfie");

            responses = (GameListener) UnicastRemoteObject.exportObject(new GameListenersHandler(), 0);


            System.err.println("Client RMI ready");
            return true;
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public void createGame(String nick){
        try {
            requests.createGame(responses,nick);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
