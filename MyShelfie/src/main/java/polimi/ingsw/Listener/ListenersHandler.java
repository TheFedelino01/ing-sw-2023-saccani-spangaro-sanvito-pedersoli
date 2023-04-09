package polimi.ingsw.Listener;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class  ListenersHandler {
    public List<GameListener> listeners;

    public ListenersHandler(){
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    public synchronized void notify_playerJoined(String nick){
        for (GameListener l : listeners) {
            try {
                l.playerJoined(nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin,GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.joinUnableGameFull(playerWantedToJoin,new GameModelImmutable(model));
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

    public synchronized void notify_PlayerIsReadyToStart(String nick) {
        for (GameListener l : listeners) {
            try {
                l.playerIsReadyToStart(nick);
            } catch (RemoteException e) {
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

    public synchronized void notify_SentMessage(Message msg) {
        for (GameListener l : listeners) {
            try {
                l.sentMessage(msg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void notify_grabbedTile(GameModel model) {
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
                l.positionedTile(new GameModelImmutable(model),type,collum);
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
    public synchronized void notify_playerDisconnected(String nick) {
        for (GameListener l : listeners) {
            try {
                l.playerDisconnected(nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }


}
