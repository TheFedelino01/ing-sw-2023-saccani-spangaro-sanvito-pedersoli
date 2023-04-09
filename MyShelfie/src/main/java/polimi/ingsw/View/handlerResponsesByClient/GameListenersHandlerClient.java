package polimi.ingsw.View.handlerResponsesByClient;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;
import polimi.ingsw.View.userView.View;

import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenersHandlerClient implements GameListener, Serializable {


    private View gui;

    public GameListenersHandlerClient(View gui){
        this.gui=gui;
    }
    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {
       // System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined! [by Socket]");
        gui.playerJoined(nickNewPlayer);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> " + wantedToJoin+" tried to entry but the game is full! [by Socket]");
        gui.joinUnableGameFull(wantedToJoin,gamemodel);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
       // System.out.println(this.hashCode() + "> " + wantedToJoin.getNickname() + " has already in [by Socket]");
        gui.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void playerIsReadyToStart(String nick) throws RemoteException {
        //System.out.println(this.hashCode() + "> " + nick + " ready to start! [by Socket]");
        gui.playerIsReadyToStart(nick);
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> " + card.getCommonType() + " card common extracted! [by Socket]");
        gui.commonCardsExtracted(gamemodel);
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
        gui.gameStarted(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        /*System.out.println(this.hashCode() + "> "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: todo [by Socket]");*/
        gui.gameEnded(gamemodel);
    }

    @Override
    public void sentMessage(Message msg) throws RemoteException {
        //System.out.println(this.hashCode() + "> new Message: \""+msg.toString()+"\" [by Socket]");
        gui.sentMessage(msg);
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles: "+gamemodel.getHandOfCurrentPlaying().toString()+" [by Socket]");
        gui.grabbedTile(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) throws RemoteException {
       // System.out.println(this.hashCode() + "> a set of non grabbable tiles have been required [by Socket]");
        gui.grabbedTileNotCorrect(gamemodel);
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf! [by Socket]");
        gui.positionedTile(gamemodel,type,column);
        //setModel(gamemodel);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(this.hashCode() + "> Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying()+" [by Socket]");
        gui.nextTurn(gamemodel);
    }

    @Override
    public void addedPoint(Player p, Point point) throws RemoteException {
        //System.out.println(this.hashCode() + "> Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo()+" [by Socket]");
        gui.addedPoint(p,point);
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
       // System.out.println(this.hashCode() + "> Player "+nick+" just disconnected [by Socket]");
        gui.playerDisconnected(nick);
    }



}
