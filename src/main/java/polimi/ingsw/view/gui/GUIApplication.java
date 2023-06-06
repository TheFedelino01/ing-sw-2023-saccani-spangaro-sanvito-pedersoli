package polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.view.gui.controllers.*;
import polimi.ingsw.view.gui.scenes.SceneEnum;
import polimi.ingsw.view.flow.ConnectionSelection;
import polimi.ingsw.view.flow.GameFlow;
import polimi.ingsw.view.gui.controllers.*;
import polimi.ingsw.view.flow.utilities.inputReaderGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main class of the GUI, it extends Application and it is used to start the GUI. It contains all the
 * methods to change the scene and to get the controller of a specific scene.
 *
 */
public class GUIApplication extends Application {

    /**
     * Attributes.
     */
    private GameFlow gameFlow;

    private Stage primaryStage, popUpStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;

    /**
     * Method to set the scene index
     * @param primaryStage the primary stage {@link Stage}
     */
    @Override
    public void start(Stage primaryStage) {
        gameFlow = new GameFlow(this, ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My Shelfie");

        root = new StackPane();


    }

    /**
     * This method use the FXMLLoader to load the scene and the controller of the scene.
     */
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

    /**
     * This method set the input reader GUI to all the controllers.
     * @param inputReaderGUI the input reader GUI {@link inputReaderGUI}
     */
    public void setInputReaderGUItoAllControllers(inputReaderGUI inputReaderGUI) {
        loadScenes();
        for (SceneInfo s : scenes) {
            s.setInputReaderGUI(inputReaderGUI);
        }
    }

    /**
     * This method is use to get a controller of a specific scene.
     * @param scene the scene {@link SceneEnum}
     * @return the controller of the scene {@link GenericController}
     */
    public GenericController getController(SceneEnum scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return scenes.get(getSceneIndex(scene)).getGenericController();
        }
        return null;
    }

    /**
     * This method is used to set the active scene.
     * @param scene the scene {@link SceneEnum}
     */
    public void setActiveScene(SceneEnum scene) {
        resizing=false;
        int index = getSceneIndex(scene);
        if (index != -1) {

            switch (scene) {
                case INGAME,GENERIC_ERROR -> {
                    this.closePopUpStage();
                }
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

        widthOld=primaryStage.getScene().getWidth();
        heightOld=primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale();
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rescale();
        });
        resizing=true;

        primaryStage.getIcons().add(new Image(GUIApplication.class.getClassLoader().getResourceAsStream("img/img/Publisher_material/Icon_50x50px.png")));
    }

    private double widthOld, heightOld;
    private boolean resizing=true;

    /**
     * This method is used to rescale the scene.
     */
    public void rescale() {
        if(resizing) {
            double widthWindow = primaryStage.getScene().getWidth();
            double heightWindow = primaryStage.getScene().getHeight();


            double w = widthWindow / widthOld;  // your window width
            double h = heightWindow / heightOld;  // your window height

            widthOld = widthWindow;
            heightOld = heightWindow;
            Scale scale = new Scale(w, h, 0, 0);
            //primaryStage.getScene().getRoot().getTransforms().add(scale);
            primaryStage.getScene().lookup("#content").getTransforms().add(scale);
        }
    }

    /**
     * Show the player in the lobby
     * @param model the model {@link GameModelImmutable}
     */
    public void showPlayerToLobby(GameModelImmutable model) {
        hidePanesInLobby();
        int i = 0;
        for (PlayerIC p : model.getPlayers()) {
            addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart());
            i++;
        }
    }

    /**
     * This method is used to show the player in the lobby.
     * @param nick the nickname of the player
     * @param indexPlayer the index of the player
     * @param isReady if the player is ready
     */
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

    /**
     * This method is used to hide the panes in the lobby.
     */
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);

            Pane paneReady = (Pane) this.primaryStage.getScene().getRoot().lookup("#ready" + i);
            paneReady.setVisible(false);
        }
    }

    /**
     * This method is used to open the popup.
     * @param scene the scene {@link Scene}
     */
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

    /**
     * This method is used to close the popup.
     */
    public void closePopUpStage() {
        if (popUpStage != null)
            popUpStage.hide();
    }

    /**
     * This method hide the btn "Ready to start".
     */
    public void disableBtnReadyToStart() {
        //I set not visible the btn "Ready to start"
        ((LobbyController) scenes.get(getSceneIndex(SceneEnum.LOBBY)).getGenericController()).setVisibleBtnReady(false);
    }

    /**
     * This methos return the index of the scene.
     * @param sceneName the scene name {@link SceneEnum}
     * @return the index of the scene
     */
    private int getSceneIndex(SceneEnum sceneName) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneEnum().equals(sceneName))
                return i;
        }
        return -1;
    }

    /**
     * This method is used to set the controller value equal to the model value.
     * @param model the model {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void showInGameModel(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setNicknamesAndPoints(model, nickname);
        controller.setPlayground(model);
        controller.setCommonCards(model);
        controller.setPersonalCard(model, nickname);
        controller.setVisibleShelves(model);
        controller.setHandTiles(model, nickname);
        controller.setAllShefies(model, nickname);
    }


    /**
     * This method is used to show the player grabbed tiles.
     * @param model the model {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void showPlayerGrabbedTiles(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setPlayerGrabbedTiles(model, nickname);
    }

    /**
     * This method is used to show the player positioned tiles.
     * @param model the model {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void showPlayerPositionedTile(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setHandTiles(model, nickname);
        controller.setAllShefies(model, nickname);
    }

    /**
     * This method show a message in the game.
     * @param msg the message
     * @param success true if the message is a success message, false otherwise
     */
    public void showMessageInGame(String msg, Boolean success) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setMsgToShow(msg, success);
    }

    /**
     * This method is used to show the selection col shelfie.
     */
    public void showSelectionColShelfie() {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.showSelectionColShelfie();
    }

    /**
     * This method is used to change the turn.
     * @param model the model {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void changeTurn(GameModelImmutable model, String nickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setNicknamesAndPoints(model, nickname);
        controller.changeTurn(model, nickname);
    }

    /**
     * This method is used to show the message in the game.
     * @param model the model {@link GameModelImmutable}
     * @param myNickname the nickname of the player
     */
    public void showMessages(GameModelImmutable model, String myNickname) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setMessage(model.getChat().getMsgs(), myNickname);
    }

    /**
     * This method is used to show all the important events.
     * @param importantEvents the list of the important events
     */
    public void showImportantEvents(List<String> importantEvents) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setImportantEvents(importantEvents);
    }

    /**
     * This method is used to show the points updated.
     * @param model the model {@link GameModelImmutable}
     * @param playerPointChanged the player that has changed the points
     * @param myNickname the nickname of the player
     * @param p the points
     */
    public void showPointsUpdated(GameModelImmutable model, Player playerPointChanged, String myNickname, Point p) {
        InGameController controller = (InGameController) scenes.get(getSceneIndex(SceneEnum.INGAME)).getGenericController();
        controller.setPointsUpdated(model, playerPointChanged, myNickname, p);
    }

    /**
     * This method show the leader board.
     * @param model the model {@link GameModelImmutable}
     */
    public void showLeaderBoard(GameModelImmutable model) {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneEnum.GAME_ENDED)).getGenericController();
        controller.show(model);
    }

    /**
     * This method make visible the button return to menu.
     */
    public void showBtnReturnToMenu() {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneEnum.GAME_ENDED)).getGenericController();
        controller.showBtnReturnToMenu();
    }

    /**
     * This method is used to show a generic error.
     * @param msg the message
     */
    public void showErrorGeneric(String msg) {
        GenericErrorController controller = (GenericErrorController) scenes.get(getSceneIndex(SceneEnum.GENERIC_ERROR)).getGenericController();
        controller.setMsg(msg,false);
    }

    /**
     * This method is used to show a generic error.
     * @param msg the message
     * @param needToExitApp true if the app need to exit, false otherwise
     */
    public void showErrorGeneric(String msg, boolean needToExitApp) {
        GenericErrorController controller = (GenericErrorController) scenes.get(getSceneIndex(SceneEnum.GENERIC_ERROR)).getGenericController();
        controller.setMsg(msg,needToExitApp);
    }

    /**
     * This method is used to create a window with a specific style.
     */
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