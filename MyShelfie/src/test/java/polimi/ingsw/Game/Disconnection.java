package polimi.ingsw.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Controller.MainController;
import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Listener.ListenersHandler;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;

import java.io.IOException;
import java.rmi.RemoteException;

public class Disconnection {
    GameController gameController;
    MainController mainController;
    GameListener lis;

    Player p1 = new Player("1");
    Player p2 = new Player("2");
    Player p3 = new Player("3");
    Player p4 = new Player("4");

    @BeforeEach
    void setUp() {
        gameController = new GameController();
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
    @DisplayName("Disconnection with 4 players")

    void joinFirst() throws RemoteException {

        gameController = (GameController) mainController.createGame(lis, p1.getNickname());
        mainController.joinFirstAvailableGame(lis, p2.getNickname());
        mainController.joinFirstAvailableGame(lis, p3.getNickname());
        mainController.joinFirstAvailableGame(lis, p4.getNickname());
        gameController.playerIsReadyToStart(p1.getNickname());
        gameController.playerIsReadyToStart(p2.getNickname());
        gameController.playerIsReadyToStart(p3.getNickname());
        gameController.playerIsReadyToStart(p4.getNickname());

        assert (gameController.getStatus() == GameStatus.RUNNING);

        //come disconnettere p2?
        p2.removeListener(lis);

        mainController.reconnect(lis, p2.getNickname(), gameController.getGameId());


    }
}
