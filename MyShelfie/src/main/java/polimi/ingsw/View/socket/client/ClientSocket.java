package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageReconnect;
import polimi.ingsw.View.userView.CommonClientActions;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.handlerResponsesByClient.GameListenersHandlerClient;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessageGrabTileFromPlayground;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessagePositionTileOnShelf;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessageSetReady;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageCreateGame;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinFirst;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinGame;
import polimi.ingsw.View.socket.client.ServerToClientMessages.SocketServerGenericMessage;
import polimi.ingsw.View.userView.View;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket extends Thread implements CommonClientActions {

    private Socket clientSoc;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    private MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private GameListener modelInvokedEvents;
    private String nickname;

    private MainControllerInterface controller;

    public ClientSocket(View gui) {
        startConnection(DefaultValue.Remote_ip,DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersHandlerClient(gui);
        this.start();

    }

    public void run() {
        while(true){
            try {
                //System.out.println("Client "+nickname+" received: "+in.readObject().toString());
                SocketServerGenericMessage msg = (SocketServerGenericMessage) in.readObject();
                msg.execute(modelInvokedEvents);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.err.println("[ERROR] Connection to server lost! "+e.toString());
                try {
                    System.in.read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(-1);
            }
        }
    }


    private void startConnection(String ip, int port) {
        boolean retry=false;
        int attempt=1;
        int i=0;

        do {
            try {
                clientSoc = new Socket(ip, port);
                out = new ObjectOutputStream(clientSoc.getOutputStream());
                in = new ObjectInputStream(clientSoc.getInputStream());
                retry=false;
            } catch (IOException e) {
                if (retry == false) {
                    System.err.println("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e.toString()+"\n");
                }
                System.out.print("[#"+attempt+"]Waiting to reconnect to Socket Server on port: '"+port+"' with ip: '"+ip+"'");

                i=0;
                while(i<DefaultValue.seconds_between_reconnection){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.print(".");
                    i++;
                }
                System.out.print("\n");

                if(attempt>=DefaultValue.num_of_attempt_to_connect_toServer_before_giveup){
                    System.out.print("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry=true;
                attempt++;
            }
        }while(retry);

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

    @Override
    public void createGame(String nick) throws IOException {
        nickname=nick;
        out.writeObject(new SocketClientMessageCreateGame(nick));
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        nickname=nick;
        out.writeObject(new SocketClientMessageJoinFirst(nick));
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname=nick;
        out.writeObject(new SocketClientMessageJoinGame(nick, idGame));
    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException {
        nickname=nick;
        out.writeObject(new SocketClientMessageReconnect(nick, idGame));
    }

    @Override
    public void setAsReady() throws IOException {
        out.writeObject(new SocketClientMessageSetReady(nickname));
    }

    @Override
    public boolean isMyTurn() {
        return false;
    }

    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
        out.writeObject(new SocketClientMessageGrabTileFromPlayground(nickname,x,y,direction,num));
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        out.writeObject(new SocketClientMessagePositionTileOnShelf(nickname,column,type));
    }

    @Override
    public void heartbeat() {
        //not usefull for socket
    }
}
