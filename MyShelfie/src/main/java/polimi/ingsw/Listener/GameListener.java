package polimi.ingsw.Listener;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {
    void playerJoined(String nickNewPlayer) throws RemoteException;;
    void JoinUnableGameFull(Player p,GameModel gamemodel) throws RemoteException;
    void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;
    void PlayerIsReadyToStart(String nick) throws RemoteException;

    void commonCardsExtracted(CommonCard card) throws RemoteException;

    void GameStarted(GameModel gamemodel) throws RemoteException;
    void GameEnded(GameModel gamemodel) throws RemoteException;

    void SentMessage(Message msg) throws RemoteException;
    void grabbedTile(GameModel gamemodel) throws RemoteException;
    void grabbedTileNotCorrect(GameModel gamemodel) throws RemoteException;
    void positionedTile(GameModel gamemodel, TileType type, int column) throws RemoteException;
    void nextTurn(GameModel gamemodel) throws RemoteException;

    void addedPoint(Player p, Point point) throws RemoteException;
}
