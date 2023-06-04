package polimi.ingsw.view.gui;

import javafx.scene.Scene;
import polimi.ingsw.view.gui.controllers.GenericController;
import polimi.ingsw.view.gui.scenes.SceneEnum;
import polimi.ingsw.view.flow.utilities.inputReaderGUI;

/**
 * SceneInfo class.
 * This class is used to store information about a scene.
 */
public class SceneInfo {
    /**
     * Attributes.
     */
    private Scene scene;
    private SceneEnum sceneEnum;
    private GenericController genericController;

    /**
     * Constructor of the class.
     * @param scene the scene {@link Scene}
     * @param sceneEnum the scene enum {@link SceneEnum}
     * @param gc the generic controller {@link GenericController}
     */
    public SceneInfo(Scene scene, SceneEnum sceneEnum, GenericController gc) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.genericController=gc;
    }

    /**
     * Method to get the scene.
     * @return the scene {@link Scene}
     */
    public Scene getScene() {
        return scene;
    }


    /**
     * Method to get the scene enum.
     * @return the scene enum {@link SceneEnum}
     */
    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }

    /**
     * Method to set the input reader GUI.
     * @param inputReaderGUI the input reader GUI {@link inputReaderGUI}
     */
    public void setInputReaderGUI(inputReaderGUI inputReaderGUI){
        if(genericController!=null) {
            genericController.setInputReaderGUI(inputReaderGUI);
        }
    }

    /**
     * Method to get the generic controller.
     * @return the generic controller {@link GenericController}
     */
    public GenericController getGenericController(){
        return genericController;
    }
}
