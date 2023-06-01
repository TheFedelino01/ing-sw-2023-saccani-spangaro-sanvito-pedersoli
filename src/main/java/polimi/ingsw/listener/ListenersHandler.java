package polimi.ingsw.listener;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.GameModel;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
     * The addListener method adds a new GameListener {@link GameListener} to the List of GameListener {@link GameListener}
     * @param obj is the GameListener {@link GameListener} to add
     */
    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * The getListeners method returns the List of GameListener {@link GameListener}
     * @return the List of GameListener {@link GameListener}
     */
    public synchronized List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * The notify_playerJoined method notifies the view that a player has joined the game
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_playerJoined(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(GameModel model, String nickPlayerReconnected) {
        for (GameListener l : listeners) {
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full
     * @param playerWantedToJoin is the player that wanted to join the game
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because the nickname is already in use
     * @param playerWantedToJoin is the player that wanted to join the game {@link Player}
     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        for (GameListener l : listeners) {
            try {
                l.joinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        for (GameListener l : listeners) {
            try {
                l.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_GameStarted(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.gameStarted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_GameEnded(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_SentMessage(GameModel gameModel, Message msg) {
        for (GameListener l : listeners) {
            try {
                l.sentMessage(new GameModelImmutable(gameModel), msg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_grabbedTile(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.grabbedTile(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_positionedTile(GameModel model, TileType type, int collum) {
        for (GameListener l : listeners) {
            try {
                l.positionedTile(new GameModelImmutable(model), type, collum);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_nextTurn(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.nextTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_LastCircle(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.lastCircle(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_grabbedTileNotCorrect(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.grabbedTileNotCorrect(new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_extractedCommonCard(GameModel gamemodel) {
        for (GameListener l : listeners) {
            try {
                l.commonCardsExtracted(new GameModelImmutable(gamemodel));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_playerDisconnected(GameModel gamemodel, String nick) {
        for (GameListener l : listeners) {
            try {
                l.playerDisconnected(new GameModelImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void notify_playerLeft(GameModel gameModel, String nick) {
        for (GameListener l : listeners) {
            try {
                l.playerLeft(new GameModelImmutable(gameModel), nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_columnShelfTooSmall(GameModel model, int column) {
        for (GameListener l : listeners) {
            try {
                l.columnShelfTooSmall(new GameModelImmutable(model), column);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_onlyOnePlayerConnected(GameModel model, int secondsToWaitUntillGameEnded) {
        for (GameListener l : listeners) {
            try {
                l.onlyOnePlayerConnected(new GameModelImmutable(model), secondsToWaitUntillGameEnded);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }


}
