package polimi.ingsw.View.userView.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.View.userView.UI;
import polimi.ingsw.View.userView.utilities.inputReaderGUI;

public class GUI extends UI {

    private GUIApplication guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher=false;

    public GUI(GUIApplication guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI=inputReaderGUI;
    }


    @Override
    public void init() {

    }

    @Override
    protected void show_publisher() {
        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        pause2.setOnFinished(event -> {
            this.guiApplication.setActiveScene(SceneEnum.PUBLISHER);
        });
        pause2.play();


        PauseTransition pause = new PauseTransition(Duration.seconds(3));
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
            this.guiApplication.setActiveScene(SceneEnum.MENU);
            //this.guiApplication.changeScene("scene2");
        }
    }

    @Override
    protected void show_insertNicknameMsg() {
        this.guiApplication.setActiveScene(SceneEnum.NICKNAME);
    }

    @Override
    public void show_creatingNewGameMsg() {

    }

    @Override
    public void show_joiningFirstAvailableMsg() {

    }

    @Override
    public void show_joiningToGameIdMsg(int idGame) {

    }

    @Override
    public void show_inputGameIdMsg() {

    }



    @Override
    public void show_choosenNickname(String nickname) {

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
    protected void show_playerJoined(GameModelImmutable gameModel, String nick) {

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
