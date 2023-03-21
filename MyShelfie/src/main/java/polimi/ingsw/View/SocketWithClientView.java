package polimi.ingsw.View;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

import java.net.Socket;

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
        createGame();
    }


    //todo tutti i controlli per la creazione del player
    private void createGame(){
        gameController = mainController.createGame(player);
        gameController.addPlayer(player);
    }
    private void joinFirstAvailableGame(){
        gameController = mainController.joinFirstGameAvailable(player);
        gameController.addPlayer(player);
    }
    private void joinGame(Integer idGame){
        gameController = mainController.joinGame(player,idGame);
        gameController.addPlayer(player);
    }
    private void iamReady(){
        gameController.playerIsReadyToStart(player);
    }

    @Override
    public void JoinUnableGameFull(GameModel gamemodel) {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(String nick) {

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
    public void grabbedTail(GameModel gamemodel) {

    }

    @Override
    public void positionedTail(GameModel gameModel) {

    }

    @Override
    public void nextTurn(GameModel gameModel) {

    }
}
