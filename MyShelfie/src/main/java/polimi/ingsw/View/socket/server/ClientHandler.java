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

    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        gameListenersHandlerSocket = new GameListenersHandlerSocket();
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        SocketClientGenericMessage temp;
        while(true){
            try {
                temp = (SocketClientGenericMessage) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                if(temp.isMessageForMainController()) {
                    gameController = temp.execute(gameListenersHandlerSocket,MainController.getInstance());
                }else{
                    temp.execute(gameController);
                }
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
