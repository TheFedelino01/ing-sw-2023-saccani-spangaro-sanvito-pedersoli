package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;

//This class handles all the responses that the server RMI sends
public class GameListenersHandler implements GameListener {


    @Override
    public void playerJoined(String nickNewPlayer) {
        System.out.println(nickNewPlayer+" has just joined!");
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
    public void grabbedTile(GameModel gamemodel) {

    }

    @Override
    public void grabbedTileNotCorrect(GameModel gamemodel) {

    }

    @Override
    public void positionedTile(GameModel gameModel) {

    }

    @Override
    public void nextTurn(GameModel gameModel) {

    }

    @Override
    public void addedPoint(Player p) {

    }
}
