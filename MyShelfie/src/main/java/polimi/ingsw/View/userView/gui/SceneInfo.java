package polimi.ingsw.View.userView.gui;

import javafx.scene.Scene;

public class SceneInfo {
    private Scene scene;
    private SceneEnum sceneEnum;

    public SceneInfo(Scene scene, SceneEnum sceneEnum) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
    }

    public Scene getScene() {
        return scene;
    }

    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }
}
