package polimi.ingsw.networking.socket.server;

import polimi.ingsw.controller.MainController;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.socket.client.SocketClientGenericMessage;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * ClientHandler Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the Socket Network protocol
 */
public class ClientHandler extends Thread {
    /**
     * Socket associated with the Client
     */
    private final Socket clientSocket;
    /**
     * ObjectInputStream in
     */
    private ObjectInputStream in;
    /**
     * ObjectOutputStream out
     */
    private ObjectOutputStream out;



    /**
     * GameController associated with the game
     */
    private GameControllerInterface gameController;

    /**
     * The GameListener of the ClientSocket for notifications
     */
    private GameListenersHandlerSocket gameListenersHandlerSocket;

    /**
     * Nickname of the SocketClient
     */
    private String nick = null;

    /**
     * Handle all the network requests performed by a specific ClientSocket
     *
     * @param soc the socket to the client
     * @throws IOException
     */
    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        gameListenersHandlerSocket = new GameListenersHandlerSocket(out);
    }

    /**
     * Stop the thread
     */
    public void interruptThread() {
        this.interrupt();
    }

    /**
     * Receive all the actions sent by the player, execute them on the specific controller required
     * It detects client network disconnections by catching Exceptions
     * {@link MainController} or {@link GameControllerInterface}
     */
    @Override
    public void run() {
        SocketClientGenericMessage temp;

        while (!this.isInterrupted()) {
            try {
                temp = (SocketClientGenericMessage) in.readObject();

                try {
                    if (temp.isMessageForMainController()) {
                        gameController = temp.execute(gameListenersHandlerSocket, MainController.getInstance());
                        nick = gameController != null ? temp.getNick() : null;

                    } else {
                        temp.execute(gameController);
                    }
                } catch (RemoteException | GameEndedException e) {
                    throw new RuntimeException(e);
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[SOCKET] Client disconnected!");
                try {
                    if (nick != null && gameController != null) {

                        gameController.disconnectPlayer(nick, gameListenersHandlerSocket);

                        if (gameController.getNumOnlinePlayers() == 0) {
                            MainController.getInstance().deleteGame(gameController.getGameId());
                        }
                        return; //This ClientHandler now dies

                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                return;

            }


        }
    }


}
