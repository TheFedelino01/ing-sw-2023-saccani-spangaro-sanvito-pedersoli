package polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * PlayerLobbyCardController class.
 */
public class PlayerLobbyCardController extends GenericController{
    @FXML
    private Text nicknameLable;

    /**
     * Method to set the nickname.
     * @param nickname the nickname
     */
    public void setNickname(String nickname){
        nicknameLable.setText(nickname);
    }
}
