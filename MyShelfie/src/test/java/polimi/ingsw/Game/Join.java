package polimi.ingsw.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.ListenersHandler;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.MaxPlayersInException;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Point;

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
        };
        new ListenersHandler().addListener(lis);
    }

    @Test
    @DisplayName("JoinAsFirstAvaible")

    void joinFirst() throws RemoteException {

        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        mainController.joinFirstAvailableGame(lis, p2.getNickname());
        mainController.joinFirstAvailableGame(lis, p3.getNickname());
        mainController.joinFirstAvailableGame(lis, p4.getNickname());
        mainController.joinFirstAvailableGame(lis, p5.getNickname());
        mainController.joinFirstAvailableGame(lis, p6.getNickname());

        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        //assertThrows(MaxPlayersInException.class,() -> mainController.joinFirstAvailableGame(lis, p5.getNickname()),"Game is full"); //?
        //assertThrows(RemoteException.class,() -> mainController.joinFirstAvailableGame(lis, p6.getNickname()),"Game is full2"); //?

    }

    @Test
    @DisplayName("JoinById")
    void joinID() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        mainController.joinGame(lis, p2.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p3.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p4.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p5.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p6.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());

        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());

        //assertThrows(MaxPlayersInException.class,() -> mainController.joinGame(lis, p5.getNickname(),runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full"); //?
        //assertThrows(RemoteException.class,() -> mainController.joinGame(lis, p6.getNickname(),runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full2"); //?
    }
}
