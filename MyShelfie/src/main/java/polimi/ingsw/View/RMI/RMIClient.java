package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.View.handlerResponsesByClient.GameListenersHandlerClient;
import polimi.ingsw.View.userView.CommonClientActions;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.userView.View;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements CommonClientActions {

    private MainControllerInterface requests;
    private GameControllerInterface gameController=null;
    private GameListener modelInvokedEvents;
    private String nickname;
    private GameListenersHandlerClient gameListenersHandler;

    public RMIClient(View gui) {
        super();
        gameListenersHandler=new GameListenersHandlerClient(gui);
        connect();
    }
    public void connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
            requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

            modelInvokedEvents = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler,0);

            System.out.println("Client RMI ready");
        } catch (Exception e) {
            System.err.println("Server RMI exception: " + e);
            e.printStackTrace();
        }
    }

    public void createGame(String nick){
        try {
            gameController = requests.createGame(modelInvokedEvents,nick);
            nickname=nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinFirstAvailable(String nick){
        try {
            gameController = requests.joinFirstAvailableGame(modelInvokedEvents,nick);
            nickname=nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void joinGame(String nick, int idGame){
        try {
            gameController = requests.joinGame(modelInvokedEvents,nick,idGame);

            nickname=nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAsReady(){
        try {
            if(gameController!=null){
                gameController.playerIsReadyToStart(nickname);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isMyTurn(){
        try {
            return gameController.isThisMyTurn(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) {
        try {
            gameController.grabTileFromPlayground(nickname,x,y,direction,num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void positionTileOnShelf(int column, TileType type){
        try {
            gameController.positionTileOnShelf(nickname,column,type);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }


}
