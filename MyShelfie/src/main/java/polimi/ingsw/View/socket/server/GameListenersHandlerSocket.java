package polimi.ingsw.View.socket.server;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;
import polimi.ingsw.View.socket.client.ServerToClientMessages.*;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenersHandlerSocket implements GameListener, Serializable {

    private ObjectOutputStream out;
    public GameListenersHandlerSocket(ObjectOutputStream o){
        out=o;
    }
    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.reset();
            out.writeObject(new msgPlayerJoined(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {

    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable model, String nick) throws RemoteException {
        //System.out.println(nick +" ready to start by socket");
       try {
           out.reset();
           out.writeObject(new msgPlayerIsReadyToStart(model,nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(card.getCommonType() +" common card extracted by socket");
        try {
            out.reset();
            out.writeObject(new msgCommonCardsExtracted(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.reset();
            out.writeObject(new msgGameStarted(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGameEnded(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sentMessage(Message msg) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgSentMessage(msg));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset(); //Else the object is not updated!!
            out.writeObject(new msgGrabbedTile(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgGrabbedTileNotCorrect(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgPositionedTile(gamemodel,type,column));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgNextTurn(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addedPoint(Player p, Point point) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgAddedPoint(p,point));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        try {
            out.reset();
            out.writeObject(new msgPlayerDisconnected(nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
