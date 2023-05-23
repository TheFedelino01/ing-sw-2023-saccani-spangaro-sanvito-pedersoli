package polimi.ingsw.game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.userView.gui.IntRecord;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisconnectionTest {
    GameController gameController;
    MainController mainController;
    Direction[] directions = Direction.values();
    IntRecord[] matrix2D = new IntRecord[29];
    IntRecord[] matrix3D = new IntRecord[36];


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

        @Override
        public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

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

        @Override
        public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

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

        @Override
        public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

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

        @Override
        public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }
    };
    GameListener lis5= new GameListener() {
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

        @Override
        public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }
    };


    Player p1 = new Player("1");
    Player p2 = new Player("2");
    Player p3 = new Player("3");
    Player p4 = new Player("4");
    Player p5 = new Player("5");


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

        //disocnnessione semplice
        gameController.disconnectPlayer(p2.getNickname(),lis2);
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        mainController.reconnect(lis2, p2.getNickname(), gameController.getGameId());
        assertEquals(4, gameController.getNumOfOnlinePlayers());

        //player non era connesso a nesuna partita
        gameController.disconnectPlayer(p2.getNickname(),lis2);
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        mainController.reconnect(lis5, p5.getNickname(), gameController.getGameId());
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        mainController.reconnect(lis2, p2.getNickname(), gameController.getGameId());
        assertEquals(4, gameController.getNumOfOnlinePlayers());

        //timer disconnessione
        gameController.disconnectPlayer(p1.getNickname(),lis1);
        gameController.disconnectPlayer(p2.getNickname(),lis2);
        gameController.disconnectPlayer(p3.getNickname(),lis3);
        assertEquals(1, gameController.getNumOfOnlinePlayers());


    }

    @Test
    @DisplayName("Disconnection and finshed game")

    void Disc() throws RemoteException, GameEndedException {
        matrix2D[0] = new IntRecord(1, 3);
        matrix2D[1] = new IntRecord(1, 4);
        matrix2D[2] = new IntRecord(2, 3);
        matrix2D[3] = new IntRecord(2, 4);
        matrix2D[4] = new IntRecord(2, 5);
        matrix2D[5] = new IntRecord(3, 2);
        matrix2D[6] = new IntRecord(3, 3);
        matrix2D[7] = new IntRecord(3, 4);
        matrix2D[8] = new IntRecord(3, 5);
        matrix2D[9] = new IntRecord(3, 6);
        matrix2D[10] = new IntRecord(4, 1);
        matrix2D[11] = new IntRecord(4, 2);
        matrix2D[12] = new IntRecord(4, 3);
        matrix2D[13] = new IntRecord(4, 4);
        matrix2D[14] = new IntRecord(4, 5);
        matrix2D[15] = new IntRecord(4, 6);
        matrix2D[16] = new IntRecord(4, 7);
        matrix2D[17] = new IntRecord(5, 1);
        matrix2D[18] = new IntRecord(5, 2);
        matrix2D[19] = new IntRecord(5, 3);
        matrix2D[20] = new IntRecord(5, 4);
        matrix2D[21] = new IntRecord(5, 5);
        matrix2D[22] = new IntRecord(5, 6);
        matrix2D[23] = new IntRecord(6, 3);
        matrix2D[24] = new IntRecord(6, 4);
        matrix2D[25] = new IntRecord(6, 5);
        matrix2D[26] = new IntRecord(7, 4);
        matrix2D[27] = new IntRecord(7, 5);
        matrix2D[28] = new IntRecord(3, 7);

        for (int i = 0; i < 28; i++) {
            matrix3D[i] = new IntRecord(matrix2D[i].row(), matrix2D[i].col());
        }

        matrix3D[28] = new IntRecord(0, 3);
        matrix3D[29] = new IntRecord(2, 6);
        matrix3D[30] = new IntRecord(3, 8);
        matrix3D[31] = new IntRecord(5, 0);
        matrix3D[32] = new IntRecord(6, 2);
        matrix3D[33] = new IntRecord(6, 6);
        matrix3D[34] = new IntRecord(8, 5);

        gameController = new GameController();
        mainController = MainController.getInstance();
        int control=0;
        int i = 0;
        int index = 0;
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        gameController.addPlayer(p1);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 1);
        gameController.playerIsReadyToStart(p1.getNickname());
        //check if the player is ready
        gameController.addPlayer(p2);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 2);
        gameController.playerIsReadyToStart(p2.getNickname());


        //Check that the game status is running, otherwise fail the test
        assert (gameController.getStatus().equals(GameStatus.RUNNING));

        while (gameController.getStatus().equals(GameStatus.RUNNING) || gameController.getStatus().equals(GameStatus.LAST_CIRCLE)) {

            do {
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix2D[index].row(), matrix2D[index].col(), Direction.DOWN, 1);
                index++;
                if (index == 28) {
                    index = 0;
                }
            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);

            //check if the tile is correctly added to the player's hand
            assert (gameController.whoIsPlaying().getInHandTile().size() == 1);
            if (i == 5) {
                i = 0;
            }
            Player p = gameController.whoIsPlaying();
            int freeSpace = p.getShelf().getFreeSpace();
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());

            //check if the tile is correctly added to the shelf
            assert (p.getShelf().getFreeSpace() == freeSpace - 1);
            i = i + 1;
            if(gameController.getStatus().equals(GameStatus.LAST_CIRCLE)){
                gameController.disconnectPlayer(p2.getNickname(),lis2);
                assertEquals(1, gameController.getNumOfOnlinePlayers());
            }
        }
        assert (p1.getShelf().getFreeSpace() == 0);
        assert (p2.getShelf().getFreeSpace() == 0);
        assert (gameController.getStatus().equals(GameStatus.ENDED));
        mainController.reconnect(lis2,p2.getNickname(),gameController.getGameId());
        control = gameController.getNumOnlinePlayers();
        assertEquals(1, control);
    }
}
