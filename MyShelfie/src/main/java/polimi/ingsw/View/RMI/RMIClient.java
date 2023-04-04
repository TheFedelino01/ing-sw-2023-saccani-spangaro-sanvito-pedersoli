package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.GameModel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject{

    private MainControllerInterface requests;
    private GameListener responses;

    private ClientResponsesInterface gameController=null;
    private PlayerInterface player=null;
    private GameListenersHandler gameListenersHandler;

    public RMIClient() throws RemoteException {
        super();
    }
    public void connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
            requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
            gameListenersHandler=new GameListenersHandler();
            responses = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler,0);

            System.out.println("Client RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e.toString());
            e.printStackTrace();
        }
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

    public boolean isMyTurn(){
        try {
            return gameController.isThisMyTurn(player.getNickname());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) {
        try {
            gameController.grabTileFromPlayground(player.getNickname(),x,y,direction,num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void positionTileOnShelf(int column, TileType type){
        try {
            gameController.positionTileOnShelf(player.getNickname(),column,type);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized GameModel getLastModelReceived(){
        return gameListenersHandler.getLastModelReceived();
    }

}
