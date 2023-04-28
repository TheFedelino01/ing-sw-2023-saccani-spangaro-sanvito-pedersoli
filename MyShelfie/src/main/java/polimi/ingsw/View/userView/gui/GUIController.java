package polimi.ingsw.View.userView.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {

    Stage stage;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private DialogPane dialogPane;

    public void CreateANewGame_Button(ActionEvent e) throws IOException {
        System.out.println("CreateANewGame_Button");
        SceneController sceneController = new SceneController();
        sceneController.switchToCreateGameScene(e);
    }

    public void JoinARandomGame_Button(ActionEvent e) throws IOException {
        System.out.println("JoinARandomGame_Button");
        SceneController sceneController = new SceneController();
        sceneController.switchToJoinARandomGameScene(e);
    }

    public void Exit_Button(ActionEvent e) {
        System.out.println("Exit_Button");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You are about to exit the game");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }

    }

    public void JoinASpecificGame_Button(ActionEvent e) throws IOException {
        System.out.println("JoinASpecificGame_Button");
        SceneController sceneController = new SceneController();
        sceneController.switchToJoinASpecificGameScene(e);
    }

    public void Reconnect_Button(ActionEvent e) {
        System.out.println("Reconnect_Button");
    }

    public void Settings_Button(ActionEvent e) {
        System.out.println("Settings_Button");
        //show DialogPane
        dialogPane.setVisible(true);
    }

    public void CloseSettings_Button(ActionEvent e) {
        System.out.println("CloseSettings_Button");
        //hide DialogPane
        dialogPane.setVisible(false);
    }
}
