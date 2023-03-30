package polimi.ingsw.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private final static int acceptTimeout = 300;
    private volatile boolean safeToClose;
    private List<ClientHandler> handler;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(acceptTimeout);
        handler = new ArrayList<>();
        this.start();
    }

    public void run(){
        while(!Thread.interrupted()) {
            try {
                safeToClose = false;
                handler.add(new ClientHandler(serverSocket.accept()));
                handler.get(handler.size() - 1).start();
            } catch (InterruptedIOException e){
                safeToClose = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        safeToClose = true;
    }
    public void stopConnection() throws IOException {
        for(ClientHandler c : handler){
            c.interruptThread();
        }
        this.interrupt();
        while (!safeToClose) {
            Thread.onSpinWait();
        }
        serverSocket.close();
    }
}
