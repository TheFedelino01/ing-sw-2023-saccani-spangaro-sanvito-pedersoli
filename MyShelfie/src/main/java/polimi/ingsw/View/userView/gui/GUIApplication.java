package polimi.ingsw.View.userView.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.GameFlow;
import polimi.ingsw.View.userView.gui.controllers.GenericController;
import polimi.ingsw.View.userView.utilities.inputReaderGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GUIApplication extends Application {

    private GameFlow gameFlow;

    private Stage primaryStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;

    @Override
    public void start(Stage primaryStage) {
        gameFlow=new GameFlow(this,ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My App");

        root = new StackPane();
    }

    private void loadScenes(){
        //Loads all the scenes available to be showed during the game
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for(int i=0; i<SceneEnum.values().length;i++){
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].value()));
            try {
                root = loader.load();
                gc=loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new SceneInfo(new Scene(root),SceneEnum.values()[i],gc));
        }
    }

    public void setInputReaderGUItoAllControllers(inputReaderGUI inputReaderGUI) {
        for(SceneInfo s:scenes){
            s.setInputReaderGUI(inputReaderGUI);
        }
    }


    public void setActiveScene(SceneEnum scene){
        int index = getSceneIndex(scene);
        if(index!=-1) {

            switch (scene){
                case PUBLISHER -> {
                    this.primaryStage.initStyle(StageStyle.UNDECORATED);
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();
                }
                default -> {
                    this.primaryStage.setAlwaysOnTop(false);
                }
            }

            this.primaryStage.setScene(scenes.get(getSceneIndex(scene)).getScene());
            this.primaryStage.show();
        }
    }

    private int getSceneIndex(SceneEnum sceneName) {
        for(int i = 0; i < scenes.size(); i++){
            if(scenes.get(i).getSceneEnum().equals(sceneName))
                return i;
        }
        return -1;
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
    }



    public static void main(String[] args) {
        launch(args);
    }



}