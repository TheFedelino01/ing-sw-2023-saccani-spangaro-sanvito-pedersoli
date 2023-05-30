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

public class ListenersHandler {
    private List<GameListener> listeners;

    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    public synchronized List<GameListener> getListeners() {
        return listeners;
    }

    public synchronized void notify_playerJoined(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_playerReconnected(GameModel model, String nickPlayerReconnected) {
        for (GameListener l : listeners) {
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
