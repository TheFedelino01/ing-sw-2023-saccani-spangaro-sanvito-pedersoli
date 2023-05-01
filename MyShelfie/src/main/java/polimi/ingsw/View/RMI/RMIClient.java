package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.View.handlerResponsesByClient.GameListenersHandlerClient;
import polimi.ingsw.View.userView.CommonClientActions;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements CommonClientActions, Runnable {

    private MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private GameListener modelInvokedEvents;
    private String nickname;
    private GameListenersHandlerClient gameListenersHandler;

    public RMIClient(View gui) {
        super();
        gameListenersHandler = new GameListenersHandlerClient(gui);
        connect();
        new Thread(this).start();
    }

    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                Registry registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
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

    @Override
    public void run() {
        //For the heartbeat
        while (true) {
            heartbeat();//send heartbeat so the server knows I am still online
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createGame(String nick) {
        try {
            gameController = requests.createGame(modelInvokedEvents, nick);
            nickname = nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinFirstAvailable(String nick) {
        try {
            gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
            nickname = nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void joinGame(String nick, int idGame) {
        try {
            gameController = requests.joinGame(modelInvokedEvents, nick, idGame);

            nickname = nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reconnect(String nick, int idGame) {
        try {
            gameController = requests.reconnect(modelInvokedEvents, nick, idGame);

            nickname = nick;

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(Message msg) {
        try {
            gameController.sentMessage(msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAsReady() {
        try {
            if (gameController != null) {
                gameController.playerIsReadyToStart(nickname);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isMyTurn() {
        try {
            return gameController.isThisMyTurn(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void grabTileFromPlayground(int x, int y, Direction direction, int num) {
        try {
            gameController.grabTileFromPlayground(nickname, x, y, direction, num);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void positionTileOnShelf(int column, TileType type) {
        try {
            gameController.positionTileOnShelf(nickname, column, type);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void heartbeat() {
        try {
            if (gameController != null) {
                gameController.heartbeat(nickname, modelInvokedEvents);
            }
        } catch (RemoteException e) {
            System.err.println("[ERROR] Connection to server lost! " + e);
            try {
                System.in.read();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(-1);
        }
    }

}
