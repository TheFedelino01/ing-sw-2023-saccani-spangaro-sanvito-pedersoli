package polimi.ingsw.View.userView.gui;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.userView.UI;

import java.io.IOException;

public class GUI extends UI {

    private MainWindowsJavaFX mainWindow = new MainWindowsJavaFX();
    public GUI() {
    }

    @Override
    public void init() {

    }

    @Override
    protected void show_publisher() throws IOException, InterruptedException {

    }

    @Override
    protected void show_menuOptions() {

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
    protected void show_insertNicknameMsg() {

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
