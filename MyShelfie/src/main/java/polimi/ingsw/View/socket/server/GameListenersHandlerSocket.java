package polimi.ingsw.View.socket.server;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class GameListenersHandlerSocket implements GameListener {

    private ObjectOutputStream out;
    public GameListenersHandlerSocket(ObjectOutputStream o){
        out=o;
    }
    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.writeObject(nickNewPlayer +" by socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void JoinUnableGameFull(Player p, GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

    }

    @Override
    public void PlayerIsReadyToStart(String nick) throws RemoteException {
        //System.out.println(nick +" ready to start by socket");
        try {
            out.writeObject(nick +" ready to start by socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commonCardsExtracted(CommonCard card) throws RemoteException {
        //System.out.println(card.getCommonType() +" common card extracted by socket");
        try {
            out.writeObject(card.getCommonType() +" common card extracted by socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void GameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.writeObject(gamemodel.getGameId() +" game started by socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
