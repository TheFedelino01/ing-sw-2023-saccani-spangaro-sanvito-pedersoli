package polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket soc) {
        this.clientSocket = soc;
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
                String inp;
                while (!Thread.interrupted()) {
                    try {
                        inp = in.readObject().toString();
                        if ((inp == null) || inp.equals("."))
                            break;
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        out.writeObject(inp);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    out.writeObject("Ciao Ciao");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
