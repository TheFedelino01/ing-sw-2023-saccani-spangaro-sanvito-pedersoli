package polimi.ingsw.View.socket.server;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.rmi.RemoteException;

public class GameListenersHandlerSocket implements GameListener {
    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {

    }

    @Override
    public void JoinUnableGameFull(Player p, GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

    }

    @Override
    public void PlayerIsReadyToStart(String nick) throws RemoteException {

    }

    @Override
    public void commonCardsExtracted(CommonCard card) throws RemoteException {

    }

    @Override
    public void GameStarted(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void GameEnded(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void SentMessage(Message msg) throws RemoteException {

    }

    @Override
    public void grabbedTile(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void grabbedTileNotCorrect(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void positionedTile(GameModel gamemodel, TileType type, int column) throws RemoteException {

    }

    @Override
    public void nextTurn(GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void addedPoint(Player p, Point point) throws RemoteException {

    }
}
