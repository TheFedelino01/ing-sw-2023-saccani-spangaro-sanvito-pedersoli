package polimi.ingsw.View.userView.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.GameFlow;
import polimi.ingsw.View.userView.gui.controllers.*;
import polimi.ingsw.View.userView.gui.scenes.SceneEnum;
import polimi.ingsw.View.userView.utilities.inputReaderGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIApplication extends Application {

    private GameFlow gameFlow;

    private Stage primaryStage, popUpStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;

    @Override
    public void start(Stage primaryStage) {
        gameFlow = new GameFlow(this, ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My Shelfie");

        root = new StackPane();
    }

    private void loadScenes() {
        //Loads all the scenes available to be showed during the game
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for (int i = 0; i < SceneEnum.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].value()));
            try {
                root = loader.load();
                gc = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new SceneInfo(new Scene(root), SceneEnum.values()[i], gc));
        }
    }

    public void setInputReaderGUItoAllControllers(inputReaderGUI inputReaderGUI) {
        for (SceneInfo s : scenes) {
            s.setInputReaderGUI(inputReaderGUI);
        }
    }

    public GenericController getController(SceneEnum scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return scenes.get(getSceneIndex(scene)).getGenericController();
        }
        return null;
    }

    public void setActiveScene(SceneEnum scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {

            switch (scene) {
                case NICKNAME_POPUP -> {
                    openPopup(scenes.get(getSceneIndex(scene)).getScene());
                    return;
                }
                case PUBLISHER -> {
                    this.primaryStage.initStyle(StageStyle.UNDECORATED);
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();
                }
                case MENU -> {
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                }
                default -> {
                    this.primaryStage.setAlwaysOnTop(false);
                }
            }

            this.primaryStage.setScene(scenes.get(getSceneIndex(scene)).getScene());
            this.primaryStage.show();
        }
    }

    public void showPlayerToLobby(GameModelImmutable model) {
        hidePanesInLobby();
        int i = 0;
        for (Player p : model.getPlayers()) {
            addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart());
            i++;
        }
    }

    private void addLobbyPanePlayer(String nick, int indexPlayer, boolean isReady) {
        SceneEnum se = null;
        switch (indexPlayer) {
            case 0 -> se = SceneEnum.PLAYER_LOBBY_CARD0;
            case 1 -> se = SceneEnum.PLAYER_LOBBY_CARD1;
            case 2 -> se = SceneEnum.PLAYER_LOBBY_CARD2;
            case 3 -> se = SceneEnum.PLAYER_LOBBY_CARD3;
        }
        Pane paneToLoad;
        SceneInfo sceneToLoad = scenes.get(getSceneIndex(se));
        paneToLoad = (Pane) sceneToLoad.getScene().getRoot();

        ((PlayerLobbyCardController) sceneToLoad.getGenericController()).setNickname(nick);

        Pane paneReady = (Pane) this.primaryStage.getScene().getRoot().lookup("#ready" + indexPlayer);
        paneReady.setVisible(isReady);

        Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + indexPlayer);
        panePlayerLobby.setVisible(true);

        panePlayerLobby.getChildren().clear();
        paneToLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(paneToLoad);
        StackPane.setAlignment(paneToLoad, Pos.CENTER);


        paneToLoad.prefWidthProperty().bind(panePlayerLobby.widthProperty());
        paneToLoad.prefHeightProperty().bind(panePlayerLobby.heightProperty());

        panePlayerLobby.getChildren().add(stackPane);


    }

    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);

            Pane paneReady = (Pane) this.primaryStage.getScene().getRoot().lookup("#ready" + i);
            paneReady.setVisible(false);
        }
    }

    private void openPopup(Scene scene) {
        popUpStage = new Stage();
        popUpStage.setTitle("Info");
        popUpStage.setResizable(false);
        popUpStage.setScene(scene);

        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(we -> System.exit(0));
        popUpStage.show();

        popUpStage.setX(primaryStage.getX() + (primaryStage.getWidth() - scene.getWidth()) * 0.5);
        popUpStage.setY(primaryStage.getY() + (primaryStage.getHeight() - scene.getHeight()) * 0.5);
    }

    public void closePopUpStage() {
        if (popUpStage != null)
            popUpStage.hide();
    }

    public void disableBtnReadyToStart() {
        //I set not visible the btn "Ready to start"
        ((LobbyController) scenes.get(getSceneIndex(SceneEnum.LOBBY)).getGenericController()).setVisibleBtnReady(false);
    }

    private int getSceneIndex(SceneEnum sceneName) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneEnum().equals(sceneName))
                return i;
        }
        return -1;
    }

    public void showInGameModel(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setNicknamesAndPoints(model,nickname);
        controller.setPlayground(model);
        controller.setCommonCards(model);
        controller.setPersonalCard(model,nickname);
        controller.setVisibleShelves(model);
        controller.setHandTiles(model,nickname);
        controller.setAllShefies(model, nickname);
    }


    public void showPlayerGrabbedTiles(GameModelImmutable model, String nickname){
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setPlayerGrabbedTiles(model, nickname);
    }
    public void showPlayerPositionedTile(GameModelImmutable model, String nickname){
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setHandTiles(model,nickname);
        controller.setAllShefies(model, nickname);
    }
    public void showMessageInGame(String msg, Boolean success){
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setMsgToShow(msg,success);
    }
    public void showSelectionColShelfie() {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.showSelectionColShelfie();
    }
    public void changeTurn(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setNicknamesAndPoints(model,nickname);
        controller.changeTurn(model,nickname);
    }
    public void showMessages(GameModelImmutable model, String myNickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setMessage(model.getChat().getMsgs(), myNickname);
    }

    public void showImportantEvents(List<String> importantEvents) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setImportantEvents(importantEvents);
    }

    public void createNewWindowWithStyle() {
        // Crea una nuova finestra con lo stile desiderato
        Stage newStage = new Stage();

        // Copia la scena dalla finestra precedente
        newStage.setScene(this.primaryStage.getScene());

        // Mostra la nuova finestra
        newStage.show();

        // Chiudi la finestra precedente
        this.primaryStage.close();

        // Imposta la nuova finestra come primaryStage
        this.primaryStage = newStage;
        this.primaryStage.centerOnScreen();
        this.primaryStage.setAlwaysOnTop(true);

        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }



}