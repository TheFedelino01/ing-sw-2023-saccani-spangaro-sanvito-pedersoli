package polimi.ingsw.view.networking.socket.client;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.view.networking.socket.client.MainControllerMessages.*;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageNewChatMessage;
import polimi.ingsw.view.userView.CommonClientActions;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.view.handlerResponsesByClient.GameListenersHandlerClient;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageGrabTileFromPlayground;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessagePositionTileOnShelf;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageSetReady;
import polimi.ingsw.view.networking.socket.client.ServerToClientMessages.SocketServerGenericMessage;
import polimi.ingsw.view.userView.Flow;

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

    public ClientSocket(Flow gui) {
        startConnection(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersHandlerClient(gui);
        this.start();

    }

    public void run() {
        while (true) {
            try {
                //System.out.println("Client "+nickname+" received: "+in.readObject().toString());
                SocketServerGenericMessage msg = (SocketServerGenericMessage) in.readObject();
                msg.execute(modelInvokedEvents);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.err.println("[ERROR] Connection to server lost! " + e.toString());
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
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                clientSoc = new Socket(ip, port);
                out = new ObjectOutputStream(clientSoc.getOutputStream());
                in = new ObjectInputStream(clientSoc.getInputStream());
                retry = false;
            } catch (IOException e) {
                if (!retry) {
                    System.err.println("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.out.print("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip + "'");

                i = 0;
                while (i < DefaultValue.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.print(".");
                    i++;
                }
                System.out.print("\n");

                if (attempt >= DefaultValue.num_of_attempt_to_connect_toServer_before_giveup) {
                    System.out.print("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);

    }


    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSoc.close();
    }

    @Override
    public void createGame(String nick) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageCreateGame(nick));
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageJoinFirst(nick));
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageJoinGame(nick, idGame));
    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageReconnect(nick, idGame));
    }

    @Override
    public void leave(String nick, int idGame) throws IOException {
        out.writeObject(new SocketClientMessageLeave(nick, idGame));
        nickname=null;
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
        out.writeObject(new SocketClientMessageGrabTileFromPlayground(nickname, x, y, direction, num));
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        out.writeObject(new SocketClientMessagePositionTileOnShelf(nickname, column, type));
    }

    @Override
    public void sendMessage(Message msg) {
        try {
            out.writeObject(new SocketClientMessageNewChatMessage(msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void heartbeat() {
        //not usefull for socket
    }
}
