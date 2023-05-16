package polimi.ingsw.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.ListenersHandler;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Join {

    List<GameController> runningGames;
    GameController gameController;
    MainController mainController;
    GameListener lis;

    Player p1 = new Player("1");
    Player p2 = new Player("2");
    Player p3 = new Player("3");
    Player p4 = new Player("4");
    Player p5 = new Player("5");
    Player p6 = new Player("6");

    @BeforeEach
    void setUp() {
        runningGames = new ArrayList<GameController>();
        mainController = MainController.getInstance();
        lis = new GameListener() {
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
        new ListenersHandler().addListener(lis);
    }

    @Test
    @DisplayName("JoinAsFirstAvaible")

    void joinFirst() throws RemoteException {

        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p2.getNickname());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p3.getNickname());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p4.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p5.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p6.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.leaveGame(lis,p1.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p2.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p3.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p4.getNickname(),runningGames.get(0).getGameId());
    }

    @Test
    @DisplayName("JoinById")
    void joinID() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p2.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p3.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p4.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p5.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p6.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());

        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.leaveGame(lis,p1.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p2.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p3.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p4.getNickname(),runningGames.get(0).getGameId());


        //assertThrows(MaxPlayersInException.class,() -> mainController.joinGame(lis, p5.getNickname(),runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full"); //?
        //assertThrows(RemoteException.class,() -> mainController.joinGame(lis, p6.getNickname(),runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full2"); //?
    }
}
