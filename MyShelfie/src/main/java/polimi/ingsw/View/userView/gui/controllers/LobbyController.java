package polimi.ingsw.View.userView.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class LobbyController extends GenericController{

    @FXML
    private Text nicknameLable;
    @FXML
    private Button btnReady;

    public void actionIamReady(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("y");
    }
    public void setUsernameLabel(String nickname){
        nicknameLable.setText(nickname);
    }
    public void setVisibleBtnReady(boolean visibility){
        btnReady.setVisible(visibility);
    }
}
