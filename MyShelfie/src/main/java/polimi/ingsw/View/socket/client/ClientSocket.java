package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.View.CommonClientActions;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.GameControllerMessages.SocketClientMessageSetReady;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageCreateGame;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinFirst;
import polimi.ingsw.View.socket.client.MainControllerMessages.SocketClientMessageJoinGame;

import java.io.*;
import java.net.Socket;

public class ClientSocket implements CommonClientActions {

    private Socket clientSoc;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    private MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private GameListener modelInvokedEvents;
    private String nickname;

    private MainControllerInterface controller;

    public ClientSocket() {

    }

    public void startConnection(String ip, int port) {
        try {
            clientSoc = new Socket(ip, port);
            out = new ObjectOutputStream(clientSoc.getOutputStream());
            in = new ObjectInputStream(clientSoc.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public String sendMsg(String msg) throws IOException, ClassNotFoundException {
        out.writeObject(msg);
        return in.readObject().toString();
    }
    */

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSoc.close();
    }

    @Override
    public void createGame(String nick) throws IOException {
        out.writeObject(new SocketClientMessageCreateGame(nick));
        nickname=nick;
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        out.writeObject(new SocketClientMessageJoinFirst(nick));
        nickname=nick;
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        out.writeObject(new SocketClientMessageJoinGame(nick, idGame));
        nickname=nick;
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
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) {

    }

    @Override
    public void positionTileOnShelf(int column, TileType type) {

    }
}
