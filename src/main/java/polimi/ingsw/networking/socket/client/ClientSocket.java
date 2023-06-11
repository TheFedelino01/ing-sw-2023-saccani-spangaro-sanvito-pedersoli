package polimi.ingsw.networking.socket.client;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.networking.HeartbeatSender;
import polimi.ingsw.networking.socket.client.gameControllerMessages.*;
import polimi.ingsw.networking.socket.client.mainControllerMessages.*;
import polimi.ingsw.networking.socket.client.serverToClientMessages.SocketServerGenericMessage;
import polimi.ingsw.view.flow.CommonClientActions;
import polimi.ingsw.view.flow.Flow;

import java.io.*;
import java.net.Socket;

import static polimi.ingsw.networking.PrintAsync.*;

/**
 * ClientSocket Class<br>
 * Handle all the network communications between ClientSocket and ClientHandler<br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the Socket Network Protocol
 */
public class ClientSocket extends Thread implements CommonClientActions {

    /**
     * Socket that represents the Client
     */
    private Socket clientSoc;
    /**
     * ObjectOutputStream out
     */
    private ObjectOutputStream out;
    /**
     * ObjectInputStream in
     */
    private ObjectInputStream in;


    /**
     * GameListener on which to perform all actions requested by the Socket Server
     */
    private final GameListenersHandlerClient modelInvokedEvents;
    /**
     * The nickname associated with the ClientSocket communication
     */
    private String nickname;

    private final HeartbeatSender socketHeartbeat;
    private Flow flow;

    /**
     * Create a Client Socket
     *
     * @param flow to notify network errors
     */
    public ClientSocket(Flow flow) {
        this.flow=flow;
        startConnection(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersHandlerClient(flow);
        this.start();
        socketHeartbeat = new HeartbeatSender(flow,this);
    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
    public void run() {
        while (true) {
            try {
                SocketServerGenericMessage msg = (SocketServerGenericMessage) in.readObject();
                msg.execute(modelInvokedEvents);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                printAsync("[ERROR] Connection to server lost! " + e);
                try {
                    System.in.read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(-1);
            }
        }
    }


    /**
     * Start the Connection to the Socket Server
     *
     * @param ip of the Socket server to connect
     * @param port of the Socket server to connect
     */
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
                    printAsync("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip + "'");

                i = 0;
                while (i < DefaultValue.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValue.num_of_attempt_to_connect_toServer_before_giveup) {
                    printAsyncNoLine("Give up!");
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

    /**
     * Close the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSoc.close();
        if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }

    /**
     * Ask the Socket Server to create a new game
     *
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void createGame(String nick) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageCreateGame(nick));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    /**
     * Ask the Socket Server to join to first available game
     *
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageJoinFirst(nick));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    /**
     * Ask the Socket Server to join a specific game
     *
     * @param nick of the player
     * @param idGame of the game to join
     * @throws IOException
     */
    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageJoinGame(nick, idGame));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    /**
     * Ask the Socket Server to reconnect to a specific game
     *
     * @param nick of the player
     * @param idGame of the game to reconnect
     * @throws IOException
     */
    @Override
    public void reconnect(String nick, int idGame) throws IOException {
        nickname = nick;
        out.writeObject(new SocketClientMessageReconnect(nick, idGame));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }


    /**
     * Ask the Socket Server to leave a specific game
     *
     * @param nick of the player
     * @param idGame of the game to leave
     * @throws IOException
     */
    @Override
    public void leave(String nick, int idGame) throws IOException {
        out.writeObject(new SocketClientMessageLeave(nick, idGame));
        finishSending();
        nickname=null;
        if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }


    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        out.writeObject(new SocketClientMessageSetReady(nickname));
        finishSending();
    }


    @Deprecated
    @Override
    public boolean isMyTurn() {
        return false;
    }

    /**
     * Ask the Socket Server to grab tiles from playground
     *
     * @param x coordinate x of the playground of the first tiles to grab
     * @param y coordinate y of the playground of the first tiles to grab
     * @param direction direction to grab the tiles
     * @param num of tiles to grab
     * @throws IOException
     */
    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
        out.writeObject(new SocketClientMessageGrabTileFromPlayground(nickname, x, y, direction, num));
        finishSending();
    }

    /**
     * Ask the Socket Server to position a grabbed tile on the shelf
     *
     * @param column column where to place the tile
     * @param type   type to place
     * @throws IOException
     */
    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        out.writeObject(new SocketClientMessagePositionTileOnShelf(nickname, column, type));
        finishSending();
    }

    /**
     * Send a message to the Socket Server
     *
     * @param msg message to send
     */
    @Override
    public void sendMessage(Message msg) {
        try {
            out.writeObject(new SocketClientMessageNewChatMessage(msg));
            finishSending();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a heartbeat to the Socket Server
     * Now it is not used because the Socket Connection automatically detects disconnections by itself
     */
    @Override
    public void heartbeat() {
        if (out != null) {
            try {
                out.writeObject(new SocketClientMessageHeartBeat(nickname));
                finishSending();
            } catch (IOException e) {
                printAsync("Connection lost to the server!! Impossible to send heartbeat...");
            }
        }
    }

    /**
     * Makes sure the message has been sent
     * @throws IOException
     */
    private void finishSending() throws IOException {
        out.flush();
        out.reset();
    }
}
