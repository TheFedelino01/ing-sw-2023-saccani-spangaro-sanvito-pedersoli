package polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private List<ClientHandler> handler;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        handler = new ArrayList<>();
        this.start();
    }

    public void run(){
        while(!Thread.interrupted()) {
            try {
                handler.add(new ClientHandler(serverSocket.accept()));
                handler.get(handler.size()-1).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void stopConnection() throws IOException {
        for(ClientHandler c : handler){
            c.interruptThread();
        }
        this.interrupt();
        serverSocket.close();
    }
}
