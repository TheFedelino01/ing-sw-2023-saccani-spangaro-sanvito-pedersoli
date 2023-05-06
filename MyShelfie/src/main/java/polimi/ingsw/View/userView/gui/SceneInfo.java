package polimi.ingsw.View.userView.gui;

import javafx.scene.Scene;
import polimi.ingsw.View.userView.gui.controllers.GenericController;
import polimi.ingsw.View.userView.utilities.inputReaderGUI;

public class SceneInfo {
    private Scene scene;
    private SceneEnum sceneEnum;
    private GenericController genericController;

    public SceneInfo(Scene scene, SceneEnum sceneEnum, GenericController gc) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.genericController=gc;
    }

    public Scene getScene() {
        return scene;
    }

    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }

    public void setInputReaderGUI(inputReaderGUI inputReaderGUI){
        if(genericController!=null) {
            genericController.setInputReaderGUI(inputReaderGUI);
        }
    }

    public GenericController getGenericController(){
        return genericController;
    }
}
