package polimi.ingsw.View.userView;

import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;

import java.io.IOException;
import java.util.List;

public abstract class UI {
    protected List<String> importantEvents; //events that needs to be showed always in screen
    protected Chat chat;
    protected polimi.ingsw.View.userView.text.inputParser inputParser;
    protected polimi.ingsw.View.userView.text.inputReader inputReader;

    public abstract void init(GameFlow gameFlow);

    public abstract void addImportantEvent(String imp);

    //TODO: remove these two methods from here by implementing
    // them into the Tui class, in a method ad-hoc
    protected abstract void resize();

    protected abstract void clearCMD();

    protected abstract void show_allPlayers(GameModelImmutable model);

    protected abstract void show_titleMyShelfie();

    protected abstract void show_playerHand(GameModelImmutable gameModel);

    protected abstract void show_grabbedTile(String nickname, GameModelImmutable model);

    protected abstract void show_playground(GameModelImmutable model);

    protected abstract void showAllShelves(GameModelImmutable model);

    protected abstract void showCommonCards(GameModelImmutable gameModel);

    protected abstract void showPoints(GameModelImmutable gameModel);

    protected abstract void showGoalCards(Player toShow);

    protected abstract void showPlayerJoined(GameModelImmutable gameModel, String nick) throws IOException, InterruptedException;

    protected abstract void show_Publisher() throws IOException, InterruptedException;

    protected abstract void show_important_events();

    protected abstract void showMessages();

    protected abstract int getLengthLongestMessage();

    protected abstract void addMessage(Message msg);

    protected abstract void showNoAvailableGamesToJoin(String msgToVisualize);

    protected abstract void showGameEnded(GameModelImmutable model);

    protected abstract void alwaysShowForAll(GameModelImmutable model);

    protected abstract void showGameId(GameModelImmutable gameModel);

    protected abstract void showNextTurn(GameModelImmutable gameModel);

    protected abstract void showWelcome(String nick);

    protected abstract void resetChat();

    protected abstract void resetImportantEvents();

    protected abstract void alwaysShow(GameModelImmutable model, String nick);

    protected abstract String askNickname();

    protected abstract boolean askSelectGame(GameFlow gameFlow);

    protected abstract Integer askGameId();

    protected abstract void askReadyToStart(GameFlow gameFlow);

    protected abstract Integer askNum(String msg, GameModelImmutable gameModel, GameFlow gameFlow);

    protected abstract void askPickTiles(GameModelImmutable gameModel, GameFlow gameFlow);

    protected abstract Integer askColumn(GameModelImmutable model, GameFlow gameFlow);

    protected abstract void askWhichTileToPlace(GameModelImmutable model, GameFlow gameFlow);
}
