package polimi.ingsw.View.socket.server;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.rmi.RemoteException;

public class GameListenersHandlerSocket implements GameListener {
    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {
        System.out.println(nickNewPlayer +" by socket");
    }

    @Override
    public void JoinUnableGameFull(Player p, GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

    }

    @Override
    public void PlayerIsReadyToStart(String nick) throws RemoteException {
        System.out.println(nick +" ready to start by socket");
    }

    @Override
    public void commonCardsExtracted(CommonCard card) throws RemoteException {
        System.out.println(card.getCommonType() +" common card extracted by socket");
    }

    @Override
    public void GameStarted(GameModelImmutable gamemodel) throws RemoteException {
        System.out.println(gamemodel.getGameId() +" game started by socket");
    }

    @Override
    public void GameEnded(GameModelImmutable gamemodel) throws RemoteException {

    }

    @Override
    public void SentMessage(Message msg) throws RemoteException {

    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {

    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {

    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {

    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {

    }

    @Override
    public void addedPoint(Player p, Point point) throws RemoteException {

    }
}
