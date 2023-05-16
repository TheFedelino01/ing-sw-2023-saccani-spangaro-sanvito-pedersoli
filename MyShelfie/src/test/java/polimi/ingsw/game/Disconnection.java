package polimi.ingsw.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Disconnection {
    GameController gameController;
    MainController mainController;

    //Initialized like this because I don't want to show anything on TUI or GUI
    GameListener lis1= new GameListener() {
        @Override
        public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

        }

        @Override
        public void gameIdNotExists(int gameid) throws RemoteException {

        }

        @Override
        public void genericErrorWhenEnteringGame(String why) throws RemoteException {

        }

        @Override
        public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {

        }

        @Override
        public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {

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
        public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException {

        }

        @Override
        public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {

        }
    };
    GameListener lis2= new GameListener() {
        @Override
        public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

        }

        @Override
        public void gameIdNotExists(int gameid) throws RemoteException {

        }

        @Override
        public void genericErrorWhenEnteringGame(String why) throws RemoteException {

        }

        @Override
        public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {

        }

        @Override
        public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {

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
        public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException {

        }

        @Override
        public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {

        }
    };
    GameListener lis3= new GameListener() {
        @Override
        public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

        }

        @Override
        public void gameIdNotExists(int gameid) throws RemoteException {

        }

        @Override
        public void genericErrorWhenEnteringGame(String why) throws RemoteException {

        }

        @Override
        public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {

        }

        @Override
        public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {

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
        public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException {

        }

        @Override
        public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {

        }
    };
    GameListener lis4= new GameListener() {
        @Override
        public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {

        }

        @Override
        public void gameIdNotExists(int gameid) throws RemoteException {

        }

        @Override
        public void genericErrorWhenEnteringGame(String why) throws RemoteException {

        }

        @Override
        public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {

        }

        @Override
        public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {

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
        public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException {

        }

        @Override
        public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {

        }
    };


    Player p1 = new Player("1");
    Player p2 = new Player("2");
    Player p3 = new Player("3");
    Player p4 = new Player("4");


    @Disabled
    @Test
    @DisplayName("Disconnection with 4 players")

    void joinFirst() throws RemoteException {
        gameController = new GameController();
        mainController = MainController.getInstance();
        gameController = (GameController) mainController.createGame(lis1, p1.getNickname());
        mainController.joinFirstAvailableGame(lis2, p2.getNickname());
        mainController.joinFirstAvailableGame(lis3, p3.getNickname());
        mainController.joinFirstAvailableGame(lis4, p4.getNickname());
        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());
        gameController.playerIsReadyToStart(p4.getNickname());

        assert (gameController.getStatus() == GameStatus.RUNNING);

        mainController.leaveGame(lis2,p2.getNickname(),gameController.getGameId());

        assertEquals(3, gameController.getNumOfOnlinePlayers());


        mainController.reconnect(lis2, p2.getNickname(), gameController.getGameId());
        //non si riconnette


    }
}
