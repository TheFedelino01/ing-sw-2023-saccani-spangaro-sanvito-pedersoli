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

    public abstract void init();

    //----------------------
    //SHOW
    //----------------------
    protected abstract void show_allPlayers(GameModelImmutable model);

    protected abstract void show_titleMyShelfie();

    protected abstract void show_playerHand(GameModelImmutable gameModel);

    protected abstract void show_grabbedTile(String nickname, GameModelImmutable model);

    protected abstract void show_playground(GameModelImmutable model);

    protected abstract void show_allShelves(GameModelImmutable model);

    protected abstract void show_commonCards(GameModelImmutable gameModel);

    protected abstract void show_points(GameModelImmutable gameModel);

    protected abstract void show_goalCards(Player toShow);

    protected abstract void show_playerJoined(GameModelImmutable gameModel, String nick) throws IOException, InterruptedException;

    protected abstract void show_publisher() throws IOException, InterruptedException;

    protected abstract void show_important_events();

    protected abstract void show_messages();
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);
    protected abstract void show_gameEnded(GameModelImmutable model);

    protected abstract void show_alwaysShowForAll(GameModelImmutable model);

    protected abstract void show_alwaysShow(GameModelImmutable model, String nick);
    protected abstract void show_gameId(GameModelImmutable gameModel);

    protected abstract void show_nextTurn(GameModelImmutable gameModel);

    protected abstract void show_welcome(String nick);



    //----------------------
    //ACTIONS
    //----------------------
    public abstract void addImportantEvent(String imp);
    protected abstract void resize();

    protected abstract void clearScreen();
    protected abstract int getLengthLongestMessage();

    protected abstract void addMessage(Message msg);

    protected abstract void resetChat();

    protected abstract void resetImportantEvents();

    public abstract void show_direction();

    public abstract void removeInput(String msg);

    public abstract void show_returnToMenuMsg();

    public abstract void show_insertNicknameMsg();

    public abstract void show_choosenNickname(String nickname);

    public abstract void show_menuOptions();

    public abstract void show_inputGameIdMsg();

    public abstract void show_NaNMsg();

    public abstract void show_whichTileToPlaceMsg();

    public abstract void show_wrongSelectionMsg();

    public abstract void show_creatingNewGameMsg();

    public abstract void show_joiningFirstAvailableMsg();

    public abstract void show_joiningToGameIdMsg(int idGame);
}
