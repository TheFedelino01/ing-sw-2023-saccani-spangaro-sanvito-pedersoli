package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient {

    public static RMIClient connect(){
        RMIClient obj=null;
        try {
            obj = new RMIClient();

            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

            ClientRequestsInterface inte = (ClientRequestsInterface) registry.lookup("myShelfie");
            GameListener stub = (GameListener) UnicastRemoteObject.exportObject(new GameListener() {
                // Implement callbacks
            }, 0);
            inte.createGame(stub)
            System.err.println("Server RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
        return obj;
    }
}
