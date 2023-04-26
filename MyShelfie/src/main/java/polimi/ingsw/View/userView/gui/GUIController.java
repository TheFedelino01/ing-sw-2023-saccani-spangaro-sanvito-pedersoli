package polimi.ingsw.View.userView.gui;

import javafx.event.ActionEvent;

import java.io.IOException;

public class GUIController {
    public void CreateANewGame_Button(ActionEvent e) throws IOException {
        System.out.println("CreateANewGame_Button");
        SceneController sceneController = new SceneController();
        sceneController.switchToScene1(e);
    }
    public void JoinARandomGame_Button(ActionEvent e) {
        System.out.println("JoinARandomGame_Button");
    }
    public void Exit_Button(ActionEvent e) {
        System.out.println("Exit_Button");
    }
    public void JoinASpecificGame_Button(ActionEvent e) {
        System.out.println("JoinASpecificGame_Button");
    }
    public void Reconnect_Button(ActionEvent e) {
        System.out.println("Reconnect_Button");
    }
    public void Settings_Button(ActionEvent e) {
        System.out.println("Settings_Button");
    }

}
