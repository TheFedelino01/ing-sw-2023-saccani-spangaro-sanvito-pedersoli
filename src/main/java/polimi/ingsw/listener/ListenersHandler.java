package polimi.ingsw.listener;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.GameModel;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static polimi.ingsw.networking.PrintAsync.printAsync;

/**
 * The ListenersHandler class is responsible for managing a List of GameListener {@link GameListener} <br>
 * and for notifying the view when a change occurs in the GameModel {@link GameModel}. <br>
 * When notifying an event, we need to pass the GameModelImmutable {@link GameModelImmutable} to the view to have access to the updated GameModel.
 */
public class ListenersHandler {
    private List<GameListener> listeners;

    /**
     * The constructor creates a new ArrayList of GameListener {@link GameListener}
     */
    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    /**
     * The addListener method adds a new GameListener {@link GameListener} to the List of GameListener {@link GameListener} <br>
     * @param obj is the GameListener {@link GameListener} to add
     */
    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * The getListeners method returns the List of GameListener {@link GameListener} <br>
     * @return the List of GameListener {@link GameListener}
     */
    public synchronized List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * The notify_playerJoined method notifies the view that a player has joined the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_playerJoined(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(GameModel model, String nickPlayerReconnected) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full <br>
     * @param playerWantedToJoin is the player that wanted to join the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because the nickname is already in use <br>
     * @param playerWantedToJoin is the player that wanted to join the game {@link Player} <br>
     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableNicknameAlreadyIn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_PlayerIsReadyToStart method notifies that a player is ready to start the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that is ready to start the game
     */
    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                printAsync("During notification of notify_PlayerIsReadyToStart, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameStarted method notifies that the game has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     */
    public synchronized void notify_GameStarted(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameStarted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameEnded method notifies that the game has ended <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_GameEnded(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_SentMessage method notifies that a message has been sent <br>
     * @param gameModel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param msg is the message that has been sent {@link Message}
     */
    public synchronized void notify_SentMessage(GameModel gameModel, Message msg) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.sentMessage(new GameModelImmutable(gameModel), msg);
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_grabbedTile method notifies that a tile has been grabbed <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public void notify_grabbedTile(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.grabbedTile(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_grabbedTile, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_positionedTile method notifies that a tile has been positioned <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param type is the type of the tile that has been positioned {@link TileType} <br>
     * @param collum is the collum where the tile has been positioned
     */
    public synchronized void notify_positionedTile(GameModel model, TileType type, int collum) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.positionedTile(new GameModelImmutable(model), type, collum);
            } catch (RemoteException e) {
                printAsync("During notification of notify_positionedTile, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_nextTurn method notifies that the next turn has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_nextTurn(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.nextTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_nextTurn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_lastCircle method notifies that the last circle has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public void notify_LastCircle(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.lastCircle(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_grabbedTileNotCorrect method notifies that a tile has been grabbed but it is not correct <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_grabbedTileNotCorrect(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.grabbedTileNotCorrect(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_grabbedTileNotCorrect, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_extractedCommonCard method notifies that a common card has been extracted <br>
     * @param gamemodel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_extractedCommonCard(GameModel gamemodel) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.commonCardsExtracted(new GameModelImmutable(gamemodel));
            } catch (RemoteException e) {
                printAsync("During notification of notify_extractedCommonCard, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerDisconnected method notifies that a player has disconnected <br>
     * @param gamemodel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that has disconnected
     */
    public synchronized void notify_playerDisconnected(GameModel gamemodel, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerDisconnected(new GameModelImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerDisconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerLeft method notifies that a player has left the game <br>
     * @param gameModel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that has left the game
     */
    public void notify_playerLeft(GameModel gameModel, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerLeft(new GameModelImmutable(gameModel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerLeft, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_columnShelfTooSmall method notifies that a column shelf is too small <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param column is the column that is too small
     */
    public synchronized void notify_columnShelfTooSmall(GameModel model, int column) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.columnShelfTooSmall(new GameModelImmutable(model), column);
            } catch (RemoteException e) {
                printAsync("During notification of notify_columnShelfTooSmall, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_onlyOnePlayerConnected method notifies that only one player is connected <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param secondsToWaitUntillGameEnded is the number of seconds to wait untill the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(GameModel model, int secondsToWaitUntillGameEnded) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.onlyOnePlayerConnected(new GameModelImmutable(model), secondsToWaitUntillGameEnded);
            } catch (RemoteException e) {
                printAsync("During notification of notify_onlyOnePlayerConnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The removeListener method removes a listener from the list of listeners <br>
     * @param lis is the listener to remove
     */
    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }




}
