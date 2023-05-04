package polimi.ingsw.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Listener.ListenersHandler;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.MaxPlayersInException;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultipleGames {
    List<GameController> runningGames;
    MainController mainController;
    GameListener lis;

    Player p1 = new Player("1");
    Player p2 = new Player("2");
    Player p3 = new Player("3");
    Player p4 = new Player("4");
    Player p5 = new Player("5");
    Player p6 = new Player("6");
    Player p7 = new Player("7");
    Player p8 = new Player("8");
    Player p9 = new Player("9");
    Player p10 = new Player("10");
    Player p11 = new Player("11");
    Player p12 = new Player("12");
    Player p13 = new Player("13");
    Player p14 = new Player("14");


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
            public void genericErrorWhenEntryingGame(String why) throws RemoteException {

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
            public void addedPoint(Player p, Point point) throws RemoteException {

            }

            @Override
            public void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException {

            }
        };
        new ListenersHandler().addListener(lis);
    }

    @Test
    @DisplayName("MultipleGames JoinFirst")
    void joinFirst() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p2.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p3.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p4.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p5.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p6.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p7.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p8.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p9.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p10.getNickname()));

        mainController.joinFirstAvailableGame(lis, p11.getNickname());
        mainController.joinFirstAvailableGame(lis, p12.getNickname());
        mainController.joinFirstAvailableGame(lis, p13.getNickname());
        mainController.joinFirstAvailableGame(lis, p14.getNickname());

        assertThrows(MaxPlayersInException.class,() -> mainController.joinFirstAvailableGame(lis, p14.getNickname()),"Game is full"); //?
    }

    @Test
    @DisplayName("MultipleGames JoinID")
    void joinID() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p2.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p3.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p4.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p5.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p6.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p7.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p8.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p9.getNickname()));
        runningGames.add((GameController) mainController.createGame(lis, p10.getNickname()));

        mainController.joinGame(lis, p11.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p12.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p13.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        mainController.joinGame(lis, p14.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());

        assertThrows(MaxPlayersInException.class,() ->  mainController.joinGame(lis, p14.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full"); //?
    }
}