package polimi.ingsw.View.userView.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class LobbyController {
    private String nickName;


    @FXML
    private Text usernameText;
    private SceneController sceneController = new SceneController();
    public void setAsReady(ActionEvent e) throws IOException {
        System.out.println("pronto!");
        usernameText.setText("ciao");
    }
}
