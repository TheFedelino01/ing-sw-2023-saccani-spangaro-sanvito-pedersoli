package polimi.ingsw.View.userView.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.userView.UI;
import polimi.ingsw.View.userView.gui.controllers.LobbyController;
import polimi.ingsw.View.userView.gui.controllers.NicknamePopupController;
import polimi.ingsw.View.userView.gui.controllers.PlayerLobbyCardController;
import polimi.ingsw.View.userView.utilities.inputReaderGUI;

public class GUI extends UI {

    private GUIApplication guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher=false;
    private boolean alreadyShowedLobby=false;

    public GUI(GUIApplication guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI=inputReaderGUI;
    }


    @Override
    public void init() {

    }
    public void callPlatformRunLater(Runnable r){
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    @Override
    protected void show_publisher() {
        callPlatformRunLater(()->this.guiApplication.setActiveScene(SceneEnum.PUBLISHER));


        PauseTransition pause = new PauseTransition(Duration.seconds(DefaultValue.time_publisher_showing_seconds));
        pause.setOnFinished(event -> {
            alreadyShowedPublisher=true;
            this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI);//So the controllers can add text to the buffer for the gameflow
            this.guiApplication.createNewWindowWithStyle();

            this.show_menuOptions();
        });
        pause.play();
    }

    @Override
    protected void show_menuOptions() {
        if(alreadyShowedPublisher) {
            callPlatformRunLater(()->this.guiApplication.setActiveScene(SceneEnum.MENU));
        }
    }

    @Override
    protected void show_insertNicknameMsg() {
        callPlatformRunLater(()->this.guiApplication.setActiveScene(SceneEnum.NICKNAME));
    }

    @Override
    public void show_choosenNickname(String nickname) {

    }
    private void show_popupInfoAndNickname(String nick,String text){
        callPlatformRunLater(()->((NicknamePopupController)this.guiApplication.getController(SceneEnum.NICKNAME_POPUP)).showNicknameAndText(nick,text));
        callPlatformRunLater(()->this.guiApplication.setActiveScene(SceneEnum.NICKNAME_POPUP));
    }


    @Override
    public void show_creatingNewGameMsg(String nickname) {
        show_popupInfoAndNickname(nickname,"Creating a new Game...");
    }

    @Override
    public void show_joiningFirstAvailableMsg(String nickname) {
        show_popupInfoAndNickname(nickname,"Joining to a Game...");
    }

    @Override
    public void show_joiningToGameIdMsg(int idGame,String nickname) {
        show_popupInfoAndNickname(nickname,"Joining to specific...");
    }

    @Override
    public void show_inputGameIdMsg() {
        callPlatformRunLater(()->this.guiApplication.setActiveScene(SceneEnum.JOIN_SPECIFIC));
    }



    @Override
    protected void show_playerJoined(GameModelImmutable gameModel, String nick) {
        if(!alreadyShowedLobby) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
                callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getController(SceneEnum.LOBBY)).setUsernameLabel(nick));


                callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.LOBBY));
                callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
                alreadyShowedLobby=true;
            });
            pause.play();

        }else{
            //The player is in lobby and another player has joined
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }


    }

    @Override
    protected void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
    }


    @Override
    protected void show_gameStarted(GameModelImmutable model) {

    }

    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {

    }

    @Override
    protected void show_gameEnded(GameModelImmutable model) {

    }



    @Override
    protected void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {

    }

    @Override
    protected void show_askNum(String msg, GameModelImmutable gameModel, String nickname) {

    }

    @Override
    protected void show_playerHand(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_grabbedTile(String nickname, GameModelImmutable model) {

    }

    @Override
    protected void show_commonCards(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_sentMessage(GameModelImmutable model, String nickname) {

    }

    @Override
    protected void show_grabbedTileMainMsg(GameModelImmutable model, String nickname) {

    }

    @Override
    public void show_whichTileToPlaceMsg() {

    }

    @Override
    public void show_wrongSelectionHandMsg() {

    }

    @Override
    protected void show_positionedTile(GameModelImmutable model, String nickname) {

    }

    @Override
    protected void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname) {

    }

    @Override
    public void show_NaNMsg() {

    }

    @Override
    public void show_returnToMenuMsg() {

    }

    @Override
    public void show_direction() {

    }

    @Override
    public void addImportantEvent(String imp) {

    }

    @Override
    protected int getLengthLongestMessage() {
        return 0;
    }

    @Override
    protected void addMessage(Message msg) {

    }

    @Override
    protected void resetChat() {

    }

    @Override
    protected void resetImportantEvents() {

    }
}
