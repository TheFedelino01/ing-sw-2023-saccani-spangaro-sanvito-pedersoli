package polimi.ingsw.Listener;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {
    public void playerJoined(String nickNewPlayer) throws RemoteException;;
    public void JoinUnableGameFull(Player p,GameModel gamemodel) throws RemoteException;
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;
    public void PlayerIsReadyToStart(String nick) throws RemoteException;

    public void GameStarted(GameModel gamemodel) throws RemoteException;
    public void GameEnded(GameModel gamemodel) throws RemoteException;

    public void SentMessage(Message msg) throws RemoteException;
    public void grabbedTile(GameModel gamemodel) throws RemoteException;
    public void grabbedTileNotCorrect(GameModel gamemodel) throws RemoteException;
    public void positionedTile(GameModel gamemodel) throws RemoteException;
    public void nextTurn(GameModel gamemodel) throws RemoteException;

    public void addedPoint(Player p) throws RemoteException;
}
