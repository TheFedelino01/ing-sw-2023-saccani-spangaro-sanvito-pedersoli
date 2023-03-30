package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements GameListener{

    private ClientRequestsInterface requests;
    private GameListener responses;


    public RMIClient() throws RemoteException {
        super();
    }
    public boolean connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(4321);

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

    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {

    }

    @Override
    public void JoinUnableGameFull(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(String nick) throws RemoteException {

    }

    @Override
    public void PlayerIsReadyToStart(String nick) throws RemoteException {

    }

    @Override
    public void GameStarted(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void GameEnded(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void SentMessage(Message msg) throws RemoteException {

    }

    @Override
    public void grabbedTile(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void grabbedTileNotCorrect(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void positionedTile(GameModel gameModel) throws RemoteException {

    }

    @Override
    public void nextTurn(GameModel gameModel) throws RemoteException {

    }

    @Override
    public void addedPoint(Player p) throws RemoteException {

    }
}
