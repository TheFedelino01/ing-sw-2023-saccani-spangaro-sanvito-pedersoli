package polimi.ingsw.view.userView.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.userView.UI;
import polimi.ingsw.view.userView.gui.controllers.LobbyController;
import polimi.ingsw.view.userView.gui.controllers.NicknamePopupController;
import polimi.ingsw.view.userView.gui.scenes.SceneEnum;
import polimi.ingsw.view.userView.utilities.inputReaderGUI;

import java.util.ArrayList;

public class GUI extends UI {

    private GUIApplication guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher=false;
    private boolean alreadyShowedLobby=false;

    private String nickname;

    public GUI(GUIApplication guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI=inputReaderGUI;
        nickname=null;
        init();
    }


    @Override
    public void init() {
        importantEvents = new ArrayList<>();
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

            this.show_menuOptions();
        });
        pause.play();
    }

    @Override
    protected void show_menuOptions() {
        if(alreadyShowedPublisher) {
            callPlatformRunLater(()->this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI));//So the controllers can add text to the buffer for the gameflow
            callPlatformRunLater(()->this.guiApplication.createNewWindowWithStyle());
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
        nickname=nick;
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
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.INGAME));
        callPlatformRunLater(() -> this.guiApplication.showInGameModel(model, nickname));//Add all info of the model to the scene
    }

    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric(msgToVisualize));
    }

    @Override
    protected void show_gameEnded(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.GAME_ENDED));
        callPlatformRunLater(() -> this.guiApplication.showLeaderBoard(model));
    }



    @Override
    protected void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {
        if(!alreadyShowedLobby){
            show_gameStarted(model);
            alreadyShowedLobby=true;
        }
        callPlatformRunLater(() -> this.guiApplication.changeTurn(model, nickname));
    }

    @Override
    protected void show_askNum(String msg, GameModelImmutable gameModel, String nickname) {
        //callPlatformRunLater(() -> this.guiApplication.showMessageInGame(msg,null));
    }

    @Override
    protected void show_playerHand(GameModelImmutable gameModel) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Player hand updated!",null));
    }

    @Override
    protected void show_grabbedTile(String nickname, GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Player "+nickname+" has grabbed some tiles",null));
    }

    @Override
    protected void show_commonCards(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_sentMessage(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessages(model,this.nickname));
    }

    @Override
    protected void show_grabbedTileMainMsg(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showPlayerGrabbedTiles(model,nickname));
        if(model.getNicknameCurrentPlaying().equals(this.nickname)){
            callPlatformRunLater(() -> this.guiApplication.showSelectionColShelfie());
        }
    }

    @Override
    public void show_whichTileToPlaceMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Select one of your Tile to place it into the shelfie",null));
    }

    @Override
    public void show_wrongSelectionHandMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Wrong selection of tile in hand!",false));
    }

    @Override
    protected void show_positionedTile(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showPlayerPositionedTile(model,nickname));
    }

    @Override
    protected void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Tiles grabbed not valid!",false));
    }

    @Override
    public void show_NaNMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("NaN",false));
    }

    @Override
    public void show_returnToMenuMsg() {
        callPlatformRunLater(() -> this.guiApplication.showBtnReturnToMenu());
    }

    @Override
    protected void show_askColumnMainMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Select one column to place all the tiles",null));
    }

    @Override
    public void show_direction() {
      }

    @Override
    protected void show_askPickTilesMainMsg() {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("Grab some tiles from the playground",true));
    }

    @Override
    protected void show_addedPoint(Player p, Point point, GameModelImmutable gamemodel) {
        callPlatformRunLater(() -> this.guiApplication.showPointsUpdated(gamemodel,p,this.nickname,point));
    }

    @Override
    protected void columnShelfTooSmall(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.showMessageInGame("The selected column is too small to hold all tiles",false));
        if(model.getNicknameCurrentPlaying().equals(this.nickname)){
            callPlatformRunLater(() -> this.guiApplication.showSelectionColShelfie());
        }
    }

    @Override
    public void addImportantEvent(String imp) {
        importantEvents.add(imp);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.importantEvents));
    }

    @Override
    protected int getLengthLongestMessage(GameModelImmutable model) {
        return 0;
    }

    @Override
    protected void addMessage(Message msg, GameModelImmutable model) {
        show_sentMessage(model,model.getChat().getLastMessage().getSender().getNickname());
    }



    @Override
    protected void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
        this.nickname=null;
        alreadyShowedPublisher=true;
        alreadyShowedLobby=false;
    }
}
