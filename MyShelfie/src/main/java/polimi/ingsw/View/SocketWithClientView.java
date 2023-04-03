package polimi.ingsw.View;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.net.Socket;
import java.rmi.RemoteException;

public class SocketWithClientView implements Runnable, GameListener {
    private Socket clientSocket;
    private MainController mainController; //Controller principale che gestisce tutti i game
    private Player player=null; //L'entitá Player della socket
    private GameController gameController; //Il controller del game dentro al quale gioca


    public SocketWithClientView(Socket client){
        clientSocket=client;
    }

    @Override
    public void run() {
        //....

    }


    private void iamReady(){
        gameController.playerIsReadyToStart(player.getNickname());
    }

    @Override
    public void playerJoined(String nickNewPlayer) throws RemoteException {

    }

    @Override
    public void JoinUnableGameFull(Player wantedToJoin, GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

    }


    @Override
    public void PlayerIsReadyToStart(String nick) {

    }

    @Override
    public void GameStarted(GameModel gamemodel) {

    }

    @Override
    public void GameEnded(GameModel gamemodel) {

    }

    @Override
    public void SentMessage(Message msg) {

    }

    @Override
    public void grabbedTile(GameModel gamemodel) {

    }

    @Override
    public void grabbedTileNotCorrect(GameModel gamemodel) {

    }

    @Override
    public void positionedTile(GameModel gamemodel, TileType type, int column) {

    }

    @Override
    public void nextTurn(GameModel gameModel) {

    }

    @Override
    public void addedPoint(Player p, Point point) {

    }
}
