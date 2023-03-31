package polimi.ingsw.Listener;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ListenersHandler {
    public transient List<GameListener> listeners;

    public ListenersHandler(){
        listeners = new ArrayList<>();
    }

    public void addListener(GameListener obj) {
        listeners.add(obj);
    }

    public void notify_playerJoined(String nick){
        for (GameListener l : listeners) {
            try {
                l.playerJoined(nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_JoinUnableGameFull(Player playerWantedToJoin,GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.JoinUnableGameFull(playerWantedToJoin,model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        for (GameListener l : listeners) {
            try {
                l.JoinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_PlayerIsReadyToStart(String nick) {
        for (GameListener l : listeners) {
            try {
                l.PlayerIsReadyToStart(nick);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_GameStarted(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.GameStarted(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_GameEnded(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.GameEnded(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_SentMessage(Message msg) {
        for (GameListener l : listeners) {
            try {
                l.SentMessage(msg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_grabbedTile(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.grabbedTile(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_positionedTile(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.positionedTile(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_nextTurn(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.nextTurn(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_grabbedTileNotCorrect(GameModel model) {
        for (GameListener l : listeners) {
            try {
                l.grabbedTileNotCorrect(model);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeListener(GameListener lis) {
        listeners.remove(lis);
    }
}
