package polimi.ingsw.View.userView.gui.controllers;


import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LobbyController extends GenericController{

    @FXML
    private Text nicknameLable;


    public void setUsernameLabel(String nickname){
        nicknameLable.setText(nickname);
    }
}
