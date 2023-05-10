package polimi.ingsw.View.userView;

import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    protected List<String> importantEvents; //events that needs to be showed always in screen
    protected Chat chat;

    public abstract void init();

    //----------------------
    //SHOW
    //----------------------
    protected abstract void show_publisher() throws IOException, InterruptedException;
    protected abstract void show_menuOptions();

    protected abstract void show_creatingNewGameMsg(String nickname);

    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    protected abstract void show_joiningToGameIdMsg(int idGame,String nickname);
    protected abstract void show_inputGameIdMsg();
    protected abstract void show_insertNicknameMsg();
    protected abstract void show_choosenNickname(String nickname);

    protected abstract void show_gameStarted(GameModelImmutable model);
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);
    protected abstract void show_gameEnded(GameModelImmutable model);


    protected abstract void show_playerJoined(GameModelImmutable gameModel, String nick);

    protected abstract void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou);

    protected abstract void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname);

    protected abstract void show_askNum(String msg, GameModelImmutable gameModel, String nickname);
    protected abstract void show_playerHand(GameModelImmutable gameModel);

    protected abstract void show_grabbedTile(String nickname, GameModelImmutable model);

    protected abstract void show_commonCards(GameModelImmutable gameModel);
    protected abstract void show_sentMessage(GameModelImmutable model, String nickname);
    protected abstract void show_grabbedTileMainMsg(GameModelImmutable model, String nickname);
    public abstract void show_whichTileToPlaceMsg();
    protected abstract void show_wrongSelectionHandMsg();
    protected abstract void show_positionedTile(GameModelImmutable model, String nickname);
    protected abstract void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname);


    protected abstract void show_NaNMsg();
    protected abstract void show_returnToMenuMsg();
    protected abstract void show_askColumnMainMsg();

    protected abstract void show_direction();
    protected abstract void show_askPickTilesMainMsg();

    //----------------------
    //ACTIONS
    //----------------------
    public abstract void addImportantEvent(String impt);

    protected abstract int getLengthLongestMessage();

    protected abstract void addMessage(Message msg);

    protected abstract void resetChat();

    protected abstract void resetImportantEvents();



}
