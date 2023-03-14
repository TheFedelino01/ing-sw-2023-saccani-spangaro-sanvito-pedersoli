package polimi.ingsw.View;

import polimi.ingsw.Controller.PlayerController;

import java.net.Socket;

public class SocketWithClientView implements Runnable{
    private Socket clientSocket;
    private PlayerController playerController;



    public SocketWithClientView(Socket client){
        clientSocket=client;
    }

    @Override
    public void run() {
        //....
        createGame();
    }

    private void createGame(){
        playerController.createGame();
    }
}
