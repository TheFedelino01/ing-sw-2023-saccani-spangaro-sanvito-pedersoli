package polimi.ingsw.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.networking.rmi.RMIClient;
import polimi.ingsw.networking.rmi.RMIServer;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.flow.Flow;

import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServerImpl;
import javax.security.auth.Subject;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.*;
import static polimi.ingsw.networking.rmi.RMIServer.bind;

public class RMITest {
    private static RMIServer server;
    Player p1 = new Player("1");
    private RMIClient client;
    Flow flow = new Flow() {
        @Override
        public void noConnectionError() {

        }

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

    @Test
    @DisplayName("General Test RMI")
    public void testConnect() throws IOException, NotBoundException {
        server = RMIServer.bind();
        RMIClient client = new RMIClient(flow);
        client.createGame(p1.getNickname());
        Message message = new Message("Hello, server!", p1);
        client.sendMessage(message);
        //client.leave(p1.getNickname(), gameController.getGameId()); come trovo il gameID?
        //client.reconnect(p1.getNickname(), gameId); sempre problema gameID

    }
}
