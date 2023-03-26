package polimi.ingsw.Listener;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;

public interface GameListener {
    public void JoinUnableGameFull(GameModel gamemodel);
    public void JoinUnableNicknameAlreadyIn(String nick);
    public void PlayerIsReadyToStart(String nick);

    public void GameStarted(GameModel gamemodel);
    public void GameEnded(GameModel gamemodel);

    public void SentMessage(Message msg);
    public void grabbedTile(GameModel gamemodel);
    public void grabbedTileNotCorrect(GameModel gamemodel);
    public void positionedTile(GameModel gameModel);
    public void nextTurn(GameModel gameModel);

    public void addedPoint(Player p);
}
