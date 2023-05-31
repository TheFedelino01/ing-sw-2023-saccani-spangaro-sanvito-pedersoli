package polimi.ingsw.view.networking.RMI;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.view.handlerResponsesByClient.GameListenersHandlerClient;
import polimi.ingsw.view.userView.CommonClientActions;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.view.userView.Flow;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class RMIClient implements CommonClientActions, Runnable {

    private static MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private static GameListener modelInvokedEvents;
    private String nickname;
    private final GameListenersHandlerClient gameListenersHandler;
    private Registry registry;

    private Flow flow;

    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenersHandlerClient(flow);
        connect();
        new Thread(this).start();

        this.flow=flow;
    }

    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
                requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

                modelInvokedEvents = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                System.out.println("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry) {
                    System.err.println("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.out.print("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValue.Default_port_RMI + "' with name: '" + DefaultValue.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultValue.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.print(".");
                    i++;
                }
                System.out.print("\n");

                if (attempt >= DefaultValue.num_of_attempt_to_connect_toServer_before_giveup) {
                    System.out.print("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);

    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            try {
                Timer timer = new Timer();
                TimerTask task = new TaskOnNetworkDisconnection(flow);
                timer.schedule( task, DefaultValue.timeoutRMI_millis );

                //send heartbeat so the server knows I am still online
                heartbeat();

                timer.cancel();
            } catch (RemoteException e) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createGame(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.createGame(modelInvokedEvents, nick);
        nickname = nick;
    }

    public void joinFirstAvailable(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        nickname = nick;
    }

    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.joinGame(modelInvokedEvents, nick, idGame);

        nickname = nick;

    }

    @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.reconnect(modelInvokedEvents, nick, idGame);

        nickname = nick;

    }

    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

        requests.leaveGame(modelInvokedEvents, nick, idGame);
        gameController = null;
        nickname = null;
    }


    @Override
    public void sendMessage(Message msg) throws RemoteException {
        gameController.sentMessage(msg);
    }

    @Override
    public void setAsReady() throws RemoteException {
        if (gameController != null) {
            gameController.playerIsReadyToStart(nickname);
        }
    }
    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isThisMyTurn(nickname);
    }
    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws RemoteException {
        gameController.grabTileFromPlayground(nickname, x, y, direction, num);
    }
    @Override
    public void positionTileOnShelf(int column, TileType type) throws GameEndedException, RemoteException {
        gameController.positionTileOnShelf(nickname, column, type);
    }

    @Override
    public void heartbeat() throws RemoteException {
        if (gameController != null) {
            gameController.heartbeat(nickname, modelInvokedEvents);
        }
    }

}
