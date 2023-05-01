package polimi.ingsw.View.socket.server;

import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GameControllerInterface gameController;

    private GameListenersHandlerSocket gameListenersHandlerSocket;

    private String nick = null;

    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        gameListenersHandlerSocket = new GameListenersHandlerSocket(out);
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        SocketClientGenericMessage temp;

        while (true) {
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

                if (nick != null && gameController != null) {
                    try {
                        gameController.setConnectionStatus(nick, gameListenersHandlerSocket, false);

                        if(gameController.getNumOnlinePlayers()==0) {
                            MainController.getInstance().deleteGame(gameController.getGameId());
                        }
                        return; //This ClientHandler now dies

                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                return;

            }


        }
    }


}
