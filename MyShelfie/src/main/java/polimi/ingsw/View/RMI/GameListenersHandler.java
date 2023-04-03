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
        System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined!");
    }

    @Override
    public void JoinUnableGameFull(Player wantedToJoin, GameModel gamemodel) throws RemoteException {

    }

    @Override
    public void JoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        System.out.println(this.hashCode() + "> " + wantedToJoin.getNickname() + " has already in");
    }



    @Override
    public void PlayerIsReadyToStart(String nick) {
        System.out.println(this.hashCode() + "> " + nick + " ready to start!");
    }

    @Override
    public void GameStarted(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> Game Started: "+gamemodel.toString());
    }

    @Override
    public void GameEnded(GameModel gamemodel) {

    }

    @Override
    public void SentMessage(Message msg) {

    }

    @Override
    public void grabbedTile(GameModel gamemodel) {
        System.out.println(this.hashCode() + "> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles");
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
