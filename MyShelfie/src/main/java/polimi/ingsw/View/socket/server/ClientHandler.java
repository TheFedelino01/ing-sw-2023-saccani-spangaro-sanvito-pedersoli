package polimi.ingsw.View.socket.server;

import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientMessage;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MainControllerInterface mainController;
    private GameControllerInterface gameController;

    private GameListenersHandlerSocket gameListenersHandlerSocket;

    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        this.mainController = MainController.getInstance();
        gameListenersHandlerSocket = new GameListenersHandlerSocket();
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        SocketClientMessage temp;
        while(true){
            try {
                temp = (SocketClientMessage) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                if(temp.isMessageForMainController()) {
                    temp.execute(mainController);
                }else{
                    temp.execute(gameController);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
