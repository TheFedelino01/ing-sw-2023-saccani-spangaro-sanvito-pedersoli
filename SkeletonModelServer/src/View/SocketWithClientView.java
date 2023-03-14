package View;

import java.net.Socket;

public class SocketWithClientView implements Runnable{
    private Socket clientSocket;

    public SocketWithClientView(Socket client){
        clientSocket=client;
    }

    @Override
    public void run() {

    }
}
