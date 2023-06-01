package polimi.ingsw.view.networking.socket.server;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.networking.socket.client.ServerToClientMessages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class is used to pass the GameListener to the client via socket
 * {@link GameListener}
 * It has a private ObjectOutputStream where it writes the data
 **/
public class GameListenersHandlerSocket implements GameListener, Serializable {

    private ObjectOutputStream out;

    /**
     * This constructor creates a GameListenersHandlerSocket
     * @param o the ObjectOutputStream
     */
    public GameListenersHandlerSocket(ObjectOutputStream o) {
        out = o;
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has joined the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.reset();
            out.writeObject(new msgPlayerJoined(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has left the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgPlayerLeft(gamemodel,nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has reconnected to the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nickPlayerReconnected is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.reset();
            out.writeObject(new msgPlayerReconnected(gamemodel, nickPlayerReconnected));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because it is full
     * @param p is the player that has tried to join the game {@link Player}
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgJoinUnableGameFull(p,gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game {@link Player}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgJoinUnableNicknameAlreadyIn(wantedToJoin));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that the gameID is not valid
     * @param gameid is the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGameIdNotExists(gameid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream a generic error when entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the connection fails
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGenericErrorWhenEntryingGame(why));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that player is ready to start
     * @param model is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerIsReadyToStart(GameModelImmutable model, String nick) throws RemoteException {
        //System.out.println(nick +" ready to start by socket");
        try {
            out.reset();
            out.writeObject(new msgPlayerIsReadyToStart(model, nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the common cards are extracted
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(card.getCommonType() +" common card extracted by socket");
        try {
            out.reset();
            out.writeObject(new msgCommonCardsExtracted(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the game started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.reset();
            out.writeObject(new msgGameStarted(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the game ended
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGameEnded(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a message has been sent
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param msg is the message sent {@link Message}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgSentMessage(gameModel, msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a Tile has been grabbed
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset(); //Else the object is not updated!!
            out.writeObject(new msgGrabbedTile(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a Tile has been grabbed but it is not correct
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGrabbedTileNotCorrect(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a Tile has been positioned
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param type is the type of the tile {@link TileType}
     * @param column is the column where the tile has been positioned
     * @throws RemoteException if the connection fails
     */
    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgPositionedTile(gamemodel, type, column));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the next turn is started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgNextTurn(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the player has added points
     * @param p is the player that has added the points
     * @param point is the number of points that have been added
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgAddedPoint(p, point,gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a player has disconnected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgPlayerDisconnected(gameModel,nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a column is too small
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param column is the column that is too small
     * @throws RemoteException if the connection fails
     */
    @Override
    public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgColumnShelfTooSmall(gameModel,column));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that only one player is connected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the connection fails
     */
    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgOnlyOnePlayerConnected(gameModel,secondsToWaitUntilGameEnded));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the last circle is started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgLastCircle(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
