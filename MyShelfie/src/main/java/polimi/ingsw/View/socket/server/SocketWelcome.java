package polimi.ingsw.View.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.List;

public class SocketWelcome extends Thread {
    private ServerSocket serverSocket;
    private List<ClientHandler> handler;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        handler = new ArrayList<>();
        this.start();
        System.out.println("Server Socket ready");
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                handler.add(new ClientHandler(serverSocket.accept()));
                handler.get(handler.size() - 1).start();

            }
        } catch (ClosedByInterruptException e) {
            //interrupted by close
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopConnection() {
        for (ClientHandler c : handler) {
            c.interruptThread();
        }
        this.interrupt();
    }
}
