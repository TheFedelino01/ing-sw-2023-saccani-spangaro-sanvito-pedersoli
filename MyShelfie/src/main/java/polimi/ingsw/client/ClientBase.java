package polimi.ingsw.client;

import java.io.*;
import java.net.Socket;

public class ClientBase {

    private Socket clientSoc;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void startConnection(String ip, int port) {
        try {
            clientSoc = new Socket(ip, port);
            out = new ObjectOutputStream(clientSoc.getOutputStream());
            in = new ObjectInputStream(clientSoc.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMsg(String msg) throws IOException, ClassNotFoundException {
        out.writeObject(msg);
        return in.readObject().toString();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSoc.close();
    }
}
