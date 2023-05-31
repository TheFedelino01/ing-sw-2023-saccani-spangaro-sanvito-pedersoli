package polimi.ingsw.view.userView;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;

import java.io.IOException;
import java.util.List;

/**
 * This class is father to both the TUI and GUI implementation<br>
 * it implements methods that everyone needs to have<br>
 */
public abstract class UI {
    protected List<String> importantEvents; //events that needs to be showed always in screen

    /**
     * Initialises GUI or TUI
     */
    public abstract void init();

    //----------------------
    //SHOW
    //----------------------

    /**
     * Show's Cranio Creation's logo
     *
     * @throws IOException
     * @throws InterruptedException
     */
    protected abstract void show_publisher() throws IOException, InterruptedException;

    /**
     * Shows menu options
     */
    protected abstract void show_menuOptions();

    /**
     * Shows the creating new game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_creatingNewGameMsg(String nickname);

    /**
     * Shows the join first available game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    /**
     * Shows the join to specific game message
     *
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    /**
     * Message that asks to insert specific game id
     */
    protected abstract void show_inputGameIdMsg();

    /**
     * Asks the player for his nickname
     */
    protected abstract void show_insertNicknameMsg();

    /**
     * Shows the player's chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    protected abstract void show_chosenNickname(String nickname);

    /**
     * Shows game started message
     *
     * @param model model where the game has started
     */
    protected abstract void show_gameStarted(GameModelImmutable model);

    /**
     * Shows error message when there are no games available for joining
     *
     * @param msgToVisualize message that needs visualisation
     */
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    /**
     * Shows the game ended message
     *
     * @param model where the game is ended
     */
    protected abstract void show_gameEnded(GameModelImmutable model);

    /**
     * Shows the players that have joined
     *
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    protected abstract void show_playerJoined(GameModelImmutable gameModel, String nick);

    /**
     * Shows that the playing player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nicknameofyou player's nickname
     */
    protected abstract void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou);

    /**
     * Show the message for next turn or reconnected player
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    protected abstract void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname);

    /**
     * Message that asks to pick up tiles
     *
     * @param msg       message to be shown
     * @param gameModel model where the message needs to be shown
     * @param nickname  nickname of the player that needs to have the message shown
     */
    protected abstract void show_askNum(String msg, GameModelImmutable gameModel, String nickname);

    /**
     * Message that shows the player's hand
     *
     * @param gameModel the model that has the player hand that needs to be shown
     */
    protected abstract void show_playerHand(GameModelImmutable gameModel);

    /**
     * Shows player's grabbed tiles
     *
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    protected abstract void show_grabbedTile(String nickname, GameModelImmutable model);

    /**
     * Shows common cards extracted
     *
     * @param gameModel the model that has the common cards to show
     */
    protected abstract void show_commonCards(GameModelImmutable gameModel);

    /**
     * Shows the message that has been sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    protected abstract void show_sentMessage(GameModelImmutable model, String nickname);

    /**
     * Shows player's grabbed tiles
     *
     * @param model    the model that called the event
     * @param nickname the player that grabbed the tiles
     */
    protected abstract void show_grabbedTileMainMsg(GameModelImmutable model, String nickname);

    /**
     * Message that asks to pick a tile to place
     */
    public abstract void show_whichTileToPlaceMsg();

    /**
     * Error message on tile in hand selection
     */
    protected abstract void show_wrongSelectionHandMsg();

    /**
     * Shows the updated player's shelf
     *
     * @param model    the model in which the player is found
     * @param nickname the player who positioned the tile
     */
    protected abstract void show_positionedTile(GameModelImmutable model, String nickname);

    /**
     * Shows error message on wrong tile selection
     *
     * @param model    the model where to show the tiles grabbed weren't correct
     * @param nickname the player who tried to grab the wrong tiles
     */
    protected abstract void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname);

    /**
     * Shows generic error message
     */
    protected abstract void show_NaNMsg();

    /**
     * Shows the message that asks to return to the main menu
     */
    protected abstract void show_returnToMenuMsg();

    /**
     * Shows the message that asks for column to be chosen
     */
    protected abstract void show_askColumnMainMsg();

    /**
     * Shows the message that asks for direction to be chosen
     */
    protected abstract void show_direction();

    /**
     * Shows the message that asks for tiles to be picked
     */
    protected abstract void show_askPickTilesMainMsg();

    /**
     * Shows the added point message
     *
     * @param p         the player to whom the point was added
     * @param point     the point added to that player
     * @param gameModel the model in which the player and point exist
     */
    protected abstract void show_addedPoint(Player p, Point point, GameModelImmutable gameModel);

    /**
     * Shows error message when chosen column has too little space
     *
     * @param model the model to check
     */
    protected abstract void columnShelfTooSmall(GameModelImmutable model);

    //----------------------
    //ACTIONS
    //----------------------

    /**
     * Shows message on important event added
     * @param input the string of the important event to add
     */
    public abstract void addImportantEvent(String input);

    /**
     * @param model the model in which search for the longest message
     * @return the length of the longest message registered in chat
     */
    protected abstract int getLengthLongestMessage(GameModelImmutable model);

    /**
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    protected abstract void addMessage(Message msg, GameModelImmutable model);

    /**
     * Resets the important events
     */
    protected abstract void resetImportantEvents();

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();

}
