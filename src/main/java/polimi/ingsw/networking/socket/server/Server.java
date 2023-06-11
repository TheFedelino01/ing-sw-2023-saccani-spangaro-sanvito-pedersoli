package polimi.ingsw.networking.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static polimi.ingsw.networking.PrintAsync.printAsync;

/**
 * Server Class<br>
 * Handle all the incoming network requests and start for each one a specific {@link ClientHandler} that handle the single Socket Connection<br>
 * by the Socket Network protocol
 */
public class Server extends Thread {
    /**
     * The Socket that represents the Server
     */
    private ServerSocket serverSocket;
    /**
     * List of {@link ClientHandler} created associated with connections
     */
    private List<ClientHandler> handler;

    /**
     * Starts the Server that is the first Socket that each Client connects on the first connection
     *
     * @param port of the Socket Server
     * @throws IOException
     */
    public void start(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            handler = new ArrayList<>();
            this.start();
            printAsync("Server Socket ready");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING SOCKET SERVER: \n\tServer RMI exception: " + e);
        }
    }

    /**
     * Accept connections and start {@link ClientHandler} handler for each one
     */
    public void run() {
        try {
            while (!Thread.interrupted()) {
                handler.add(new ClientHandler(serverSocket.accept()));
                handler.get(handler.size() - 1).start();
                printAsync("[SOCKET] new connection accepted");
            }
        } catch (IOException e) {
            System.err.println("[ERROR] ACCEPTING WELCOME SOCKET CONNECTION: \n\tServer SOCKET exception: " + e);
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the Socket Server connection
     */
    @Deprecated
    public void stopConnection() {
        if (handler != null)
            for (ClientHandler c : handler) {
                c.interruptThread();
            }
        this.interrupt();
    }
}
