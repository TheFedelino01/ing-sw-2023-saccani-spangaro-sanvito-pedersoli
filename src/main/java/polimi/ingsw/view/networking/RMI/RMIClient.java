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

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the RMI Network Protocol
 */
public class RMIClient implements CommonClientActions, Runnable {

    /**
     * The remote object returned by the registry that represents the main controller
     */
    private static MainControllerInterface requests;
    /**
     * The remote object returned by the RMI server that represents the connected game
     */
    private GameControllerInterface gameController = null;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListener modelInvokedEvents;
    /**
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private String nickname;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private final GameListenersHandlerClient gameListenersHandler;
    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Flow to notify network error messages
     */
    private Flow flow;

    /**
     * Create, start and connect a RMI Client to the server
     *
     * @param flow for visualising network error messages
     */
    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenersHandlerClient(flow);
        connect();
        new Thread(this).start();

        this.flow=flow;
    }

    /**
     * Connect the client to the RMI server by {@link DefaultValue#Default_port_RMI} and {@link DefaultValue#Default_servername_RMI}
     */
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

    /**
     * Send heartbeats to the RMI server
     * If sending a message takes more than {@link DefaultValue#timeoutRMI_millis} millis, the client
     * will be considered no longer connected to the server
     */
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

    /**
     * Request the creation of a Game to the server
     *
     * @param nick of the player who wants to create a game
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void createGame(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.createGame(modelInvokedEvents, nick);
        nickname = nick;
    }

    /**
     * Request to join a server game (first game available)
     *
     * @param nick of the player who wants to join a game
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void joinFirstAvailable(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        nickname = nick;
    }


    /**
     * Request to join a specific server game
     *
     * @param nick of the player who wants to join a specific game
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.joinGame(modelInvokedEvents, nick, idGame);

        nickname = nick;

    }

    /**
     * Request the reconnection of a player @param nick to a game @param idGame
     *
     * @param nick of the player who wants to be reconnected
     * @param idGame of the game to be reconnected
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController = requests.reconnect(modelInvokedEvents, nick, idGame);

        nickname = nick;

    }

    /**
     * Request to leave a game without the possibility to be reconnected
     * Calling leave means that the player wants to quit forever the game
     *
     * @param nick of the player that wants to leave
     * @param idGame of the game to leave
     * @throws IOException
     * @throws NotBoundException
     */
    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

        requests.leaveGame(modelInvokedEvents, nick, idGame);
        gameController = null;
        nickname = null;
    }


    /**
     * Send a message to the server
     *
     * @param msg message to send
     * @throws RemoteException
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {
        gameController.sentMessage(msg);
    }

    /**
     * Notify the server that a client is ready to start
     *
     * @throws RemoteException
     */
    @Override
    public void setAsReady() throws RemoteException {
        if (gameController != null) {
            gameController.playerIsReadyToStart(nickname);
        }
    }

    /**
     * Ask the server if it is currently my turn
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isThisMyTurn(nickname);
    }

    /**
     * Ask the server to grab a tile from the playground
     *
     * @param x coordinate x of the playground of the first tiles to grab
     * @param y coordinate y of the playground of the first tiles to grab
     * @param direction direction to grab the tiles
     * @param num of tiles to grab
     * @throws RemoteException
     */
    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws RemoteException {
        gameController.grabTileFromPlayground(nickname, x, y, direction, num);
    }

    /**
     * Ask the server to position a grabbed tile on the shelf
     *
     * @param column column where to place the tile
     * @param type   type to place
     * @throws GameEndedException
     * @throws RemoteException
     */
    @Override
    public void positionTileOnShelf(int column, TileType type) throws GameEndedException, RemoteException {
        gameController.positionTileOnShelf(nickname, column, type);
    }

    /**
     * Send a heartbeat to the server
     *
     * @throws RemoteException
     */
    @Override
    public void heartbeat() throws RemoteException {
        if (gameController != null) {
            gameController.heartbeat(nickname, modelInvokedEvents);
        }
    }

}
