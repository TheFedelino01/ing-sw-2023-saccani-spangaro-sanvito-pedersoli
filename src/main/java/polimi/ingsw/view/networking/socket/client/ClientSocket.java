package polimi.ingsw.view.networking.socket.client;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.view.networking.socket.client.MainControllerMessages.*;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageNewChatMessage;
import polimi.ingsw.view.userView.CommonClientActions;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageGrabTileFromPlayground;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessagePositionTileOnShelf;
import polimi.ingsw.view.networking.socket.client.GameControllerMessages.SocketClientMessageSetReady;
import polimi.ingsw.view.networking.socket.client.ServerToClientMessages.SocketServerGenericMessage;
import polimi.ingsw.view.userView.Flow;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

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
    private GameListenersHandlerClient modelInvokedEvents;
    /**
     * The nickname associated with the ClientSocket communication
     */
    private String nickname;


    /**
     * Create a Client Socket
     *
     * @param gui to notify network errors
     */
    public ClientSocket(Flow gui) {
        startConnection(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersHandlerClient(gui);
        this.start();

    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
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

    /**
     * Close the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSoc.close();
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
        nickname=null;
    }


    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        out.writeObject(new SocketClientMessageSetReady(nickname));
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
    }

    /**
     * Send a message to the Socket Server
     *
     * @param msg message to send
     * @throws RemoteException
     */
    @Override
    public void sendMessage(Message msg) {
        try {
            out.writeObject(new SocketClientMessageNewChatMessage(msg));
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
        //not useful for socket
    }
}
