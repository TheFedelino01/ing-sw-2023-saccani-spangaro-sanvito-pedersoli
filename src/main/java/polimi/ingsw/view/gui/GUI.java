package polimi.ingsw.view.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.gui.controllers.LobbyController;
import polimi.ingsw.view.gui.controllers.NicknamePopupController;
import polimi.ingsw.view.gui.scenes.SceneEnum;
import polimi.ingsw.view.flow.UI;
import polimi.ingsw.view.flow.utilities.inputReaderGUI;

import java.util.ArrayList;

/**
 * GUI class.
 * This class is the GUI implementation of the UI abstract class and it manages all the GUI-related operations.
 */
public class GUI extends UI {


    private GUIApplication guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false;
    private boolean alreadyShowedLobby = false;

    private String nickname;

    /**
     * Constructor of the class.
     *
     * @param guiApplication the GUI application
     * @param inputReaderGUI the input reader
     */
    public GUI(GUIApplication guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI = inputReaderGUI;
        nickname = null;
        init();
    }


    /**
     * The init method is used to initialize the GUI.
     */
    @Override
    public void init() {
        importantEvents = new ArrayList<>();
    }

    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    /**
     * The show method is used to show the GUI, and set the active scene to the publisher.
     */
    @Override
    protected void show_publisher() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.PUBLISHER));


        PauseTransition pause = new PauseTransition(Duration.seconds(DefaultValue.time_publisher_showing_seconds));
        pause.setOnFinished(event -> {
            alreadyShowedPublisher = true;

            this.show_menuOptions();
        });
        pause.play();
    }

    /**
     * The show method is used to show the GUI, and set the active scene to the menu.
     */
    @Override
    protected void show_menuOptions() {
        if (alreadyShowedPublisher) {
            callPlatformRunLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI));//So the controllers can add text to the buffer for the gameflow
            callPlatformRunLater(() -> this.guiApplication.createNewWindowWithStyle());
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.MENU));
        }
    }

    /**
     * The show method is used to show the GUI, and set the active scene to the nickname.
     */
    @Override
    protected void show_insertNicknameMsg() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.NICKNAME));
    }


    @Override
    public void show_chosenNickname(String nickname) {

    }

    /**
     * This method show the info about the chosen nickname.
     *
     * @param nick the nickname
     * @param text the info
     */
    private void show_popupInfoAndNickname(String nick, String text) {
        callPlatformRunLater(() -> ((NicknamePopupController) this.guiApplication.getController(SceneEnum.NICKNAME_POPUP)).showNicknameAndText(nick, text));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.NICKNAME_POPUP));
        nickname = nick;
    }


    /**
     * This method show a popup with the chosen text
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_creatingNewGameMsg(String nickname) {
        show_popupInfoAndNickname(nickname, "Creating a new Game...");
    }

    /**
     * This method show a popup with the chosen text
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningFirstAvailableMsg(String nickname) {
        show_popupInfoAndNickname(nickname, "Joining to a Game...");
    }

    /**
     * This method show a popup with the chosen text
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningToGameIdMsg(int idGame, String nickname) {
        show_popupInfoAndNickname(nickname, "Joining to specific...");
    }

    /**
     * This method show the game id
     */
    @Override
    public void show_inputGameIdMsg() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.JOIN_SPECIFIC));
    }


    /**
     * This method show all the player who are in the lobby
     *
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    @Override
    protected void show_playerJoined(GameModelImmutable gameModel, String nick) {
        if (!alreadyShowedLobby) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
                callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getController(SceneEnum.LOBBY)).setUsernameLabel(nick));
                callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getController(SceneEnum.LOBBY)).setGameid(gameModel.getGameId()));

                callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.LOBBY));
                callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
                alreadyShowedLobby = true;
            });
            pause.play();

        } else {
            //The player is in lobby and another player has joined
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }


    }

    /**
     * this method show that the player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nicknameofyou player's nickname
     */
    @Override
    protected void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
    }


    /**
     * This method show that the game has started
     *
     * @param model model where the game has started
     */
    @Override
    protected void show_gameStarted(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.INGAME));
        callPlatformRunLater(() -> this.guiApplication.showInGameModel(model, nickname));//Add all info of the model to the scene
    }

    /**
     * This method show that there are no available games to join
     *
     * @param msgToVisualize message that needs visualisation
     */
    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric(msgToVisualize));
    }

    /**
     * This method show that the game has ended
     *
     * @param model model where the game has ended
     */
    @Override
    protected void show_gameEnded(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.GAME_ENDED));
        callPlatformRunLater(() -> this.guiApplication.showLeaderBoard(model));
    }


    /**
     * This method show that the player has reconnected to the game or that the turn has changed
     *
     * @param model    model where events happen
     * @param nickname player's nickname
     */
    @Override
    protected void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {
        if (!alreadyShowedLobby) {
            show_gameStarted(model);
            alreadyShowedLobby = true;
        }
        callPlatformRunLater(() -> this.guiApplication.changeTurn(model, nickname));
    }

    @Override
    protected void show_askNum(String msg, GameModelImmutable gameModel, String nickname) {
        //callPlatformRunLater(() -> this.guiApplication.showMessageInGame(msg,null));
    }

    /**
     * This method show the player hand
     *
     * @param gameModel the model that has the player hand that needs to be shown
     */
    @Override
    protected void show_playerHand(GameModelImmutable gameModel) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Player hand updated!", null));
    }

    /**
     * This method show the grabbed tiles
     *
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    @Override
    protected void show_grabbedTile(String nickname, GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Player " + nickname + " has grabbed some tiles", null));
    }

    @Override
    protected void show_commonCards(GameModelImmutable gameModel) {

    }

    /**
     * This method show the sent message
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    @Override
    protected void show_sentMessage(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessages(model, this.nickname));
    }

    /**
     * This method show the player's tile
     *
     * @param model    the model that called the event
     * @param nickname the player that grabbed the tiles
     */
    @Override
    protected void show_grabbedTileMainMsg(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showPlayerGrabbedTiles(model, nickname));
        if (model.getNicknameCurrentPlaying().equals(this.nickname)) {
            callPlatformRunLater(() -> this.guiApplication.showSelectionColShelfie());
        }
    }

    /**
     * This method show a message about placing a tile
     */
    @Override
    public void show_whichTileToPlaceMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Select one of your Tile to place it into the shelfie", null));
    }

    /**
     * This method show a message about a wrong selection of a tile in the hand
     */
    @Override
    public void show_wrongSelectionHandMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Wrong selection of tile in hand!", false));
    }

    /**
     * This method show the positioned tiles
     *
     * @param model    the model in which the player is found
     * @param nickname the player who positioned the tile
     */
    @Override
    protected void show_positionedTile(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showPlayerPositionedTile(model, nickname));
    }

    /**
     * This method show a message about a wrong grab of tiles
     */
    @Override
    protected void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Tiles grabbed not valid!", false));
    }

    /**
     * This method show a message
     */
    @Override
    public void show_NaNMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("NaN", false));
    }

    /**
     * This method make a button visible to return to the menu
     */
    @Override
    public void show_returnToMenuMsg() {
        callPlatformRunLater(() -> this.guiApplication.showBtnReturnToMenu());
    }

    /**
     * This method show a message about the column selection
     */
    @Override
    protected void show_askColumnMainMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Select one column to place all the tiles", null));
    }

    @Override
    public void show_direction() {
    }

    /**
     * This method show a message about the grab of tiles
     */
    @Override
    protected void show_askPickTilesMainMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Grab some tiles from the playground", true));
    }

    /**
     * This method update and show the point
     *
     * @param p     the player that has the points
     * @param point the points that need to be shown
     */
    @Override
    protected void show_addedPoint(Player p, Point point, GameModelImmutable gameModel) {
        callPlatformRunLater(() -> this.guiApplication.showPointsUpdated(gameModel, p, this.nickname, point));
    }

    /**
     * This method show a message about the selection of the column
     */
    @Override
    protected void columnShelfTooSmall(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("The selected column is too small to hold all tiles", false));
        if (model.getNicknameCurrentPlaying().equals(this.nickname)) {
            callPlatformRunLater(() -> this.guiApplication.showSelectionColShelfie());
        }
    }

    /**
     * This method add an important event to the list of important events, and show it
     * @param input the string of the important event to add
     */
    @Override
    public void addImportantEvent(String input) {
        importantEvents.add(input);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.importantEvents));
    }

    @Override
    protected int getLengthLongestMessage(GameModelImmutable model) {
        return 0;
    }

    /**
     * This method send a message
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    @Override
    protected void addMessage(Message msg, GameModelImmutable model) {
        show_sentMessage(model, model.getChat().getLastMessage().getSender().getNickname());
    }


    /**
     * This method reset the important events
     */
    @Override
    protected void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
        this.nickname = null;
        alreadyShowedPublisher = true;
        alreadyShowedLobby = false;
    }

    /**
     * This method show a message about a no connection error
     */
    @Override
    protected void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Connection to server lost!", true));
    }
}
