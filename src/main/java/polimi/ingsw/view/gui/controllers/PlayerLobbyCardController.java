package polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerLobbyCardController extends GenericController{
    @FXML
    private Text nicknameLable;

    public void setNickname(String nickname){
        nicknameLable.setText(nickname);
    }
}
