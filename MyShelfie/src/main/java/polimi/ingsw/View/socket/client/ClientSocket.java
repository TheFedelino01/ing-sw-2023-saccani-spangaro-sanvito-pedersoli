package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.View.CommonClientActions;
import polimi.ingsw.View.handlerResponsesByClient.GameListenersHandlerClientRMI;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.handlerResponsesByClient.GameListenersHandlerClientSocket;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessageGrabTileFromPlayground;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessagePositionTileOnShelf;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessageSetReady;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageCreateGame;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinFirst;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinGame;
import polimi.ingsw.View.socket.client.ServerToClientMessages.SocketServerGenericMessage;

import java.io.*;
import java.net.Socket;

public class ClientSocket extends Thread implements CommonClientActions {

    private Socket clientSoc;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    private MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private GameListener modelInvokedEvents;
    private String nickname;

    private MainControllerInterface controller;

    public ClientSocket() {
        startConnection(DefaultValue.Remote_ip,DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersHandlerClientSocket();
        this.start();
    }

    public void run() {
        while(true){
            try {
                //System.out.println("Client "+nickname+" received: "+in.readObject().toString());
                SocketServerGenericMessage msg = (SocketServerGenericMessage) in.readObject();
                msg.execute(modelInvokedEvents);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void startConnection(String ip, int port) {
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
}
