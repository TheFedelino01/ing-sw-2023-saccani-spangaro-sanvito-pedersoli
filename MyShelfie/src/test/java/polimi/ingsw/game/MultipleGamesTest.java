package polimi.ingsw.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.controller.MainController;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.listener.ListenersHandler;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultipleGamesTest {
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
    Player p15 = new Player("15");
    Player p16 = new Player("16");
    Player p17 = new Player("17");
    Player p18 = new Player("18");
    Player p19 = new Player("19");



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

            @Override
            public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

            }

            @Override
            public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {

            }
        };
        new ListenersHandler().addListener(lis);
    }


    @Test
    @DisplayName("MultipleGames JoinFirst")
    void joinFirst() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p2.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p3.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(2).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p4.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(3).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p5.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(4).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p6.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(5).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p7.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(6).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p8.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(7).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p9.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(8).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p10.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(9).getNumOfOnlinePlayers());

        mainController.joinFirstAvailableGame(lis, p11.getNickname());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p12.getNickname());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p13.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p14.getNickname());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p15.getNickname());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p16.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p17.getNickname());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(2).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p18.getNickname());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(2).getNumOfOnlinePlayers());
        mainController.joinFirstAvailableGame(lis, p19.getNickname());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(2).getNumOfOnlinePlayers());
        mainController.leaveGame(lis,p1.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p2.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p3.getNickname(),runningGames.get(2).getGameId());
        mainController.leaveGame(lis,p4.getNickname(),runningGames.get(3).getGameId());
        mainController.leaveGame(lis,p5.getNickname(),runningGames.get(4).getGameId());
        mainController.leaveGame(lis,p6.getNickname(),runningGames.get(5).getGameId());
        mainController.leaveGame(lis,p7.getNickname(),runningGames.get(6).getGameId());
        mainController.leaveGame(lis,p8.getNickname(),runningGames.get(7).getGameId());
        mainController.leaveGame(lis,p9.getNickname(),runningGames.get(8).getGameId());
        mainController.leaveGame(lis,p10.getNickname(),runningGames.get(9).getGameId());
        mainController.leaveGame(lis,p11.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p12.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p13.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p14.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p15.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p16.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p17.getNickname(),runningGames.get(2).getGameId());
        mainController.leaveGame(lis,p18.getNickname(),runningGames.get(2).getGameId());
        mainController.leaveGame(lis,p19.getNickname(),runningGames.get(2).getGameId());
        //assertThrows(MaxPlayersInException.class,() -> mainController.joinFirstAvailableGame(lis, p14.getNickname()),"Game is full"); //?
    }

    @Test
    @DisplayName("MultipleGames JoinID")
    void joinID() throws RemoteException {
        runningGames.add((GameController) mainController.createGame(lis, p1.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p2.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p3.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(2).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p4.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(3).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p5.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(4).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p6.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(5).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p7.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(6).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p8.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(7).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p9.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(8).getNumOfOnlinePlayers());
        runningGames.add((GameController) mainController.createGame(lis, p10.getNickname()));
        assertEquals(1, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(9).getNumOfOnlinePlayers());

        mainController.joinGame(lis, p11.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p12.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p13.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p14.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p15.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getGameId());
        assertEquals(2, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p16.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getGameId());
        assertEquals(3, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p17.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.joinGame(lis, p18.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getGameId());
        assertEquals(4, runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(1).getNumOfOnlinePlayers());
        mainController.leaveGame(lis,p1.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p2.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p3.getNickname(),runningGames.get(2).getGameId());
        mainController.leaveGame(lis,p4.getNickname(),runningGames.get(3).getGameId());
        mainController.leaveGame(lis,p5.getNickname(),runningGames.get(4).getGameId());
        mainController.leaveGame(lis,p6.getNickname(),runningGames.get(5).getGameId());
        mainController.leaveGame(lis,p7.getNickname(),runningGames.get(6).getGameId());
        mainController.leaveGame(lis,p8.getNickname(),runningGames.get(7).getGameId());
        mainController.leaveGame(lis,p9.getNickname(),runningGames.get(8).getGameId());
        mainController.leaveGame(lis,p10.getNickname(),runningGames.get(9).getGameId());
        mainController.leaveGame(lis,p11.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p12.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p13.getNickname(),runningGames.get(0).getGameId());
        mainController.leaveGame(lis,p15.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p16.getNickname(),runningGames.get(1).getGameId());
        mainController.leaveGame(lis,p17.getNickname(),runningGames.get(1).getGameId());
        //assertThrows(MaxPlayersInException.class,() ->  mainController.joinGame(lis, p14.getNickname(), runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT))).toList().get(0).getGameId()),"Game is full"); //?
    }
}