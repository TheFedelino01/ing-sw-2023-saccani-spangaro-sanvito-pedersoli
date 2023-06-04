package polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * NicknamePopupController class.
 */
public class NicknamePopupController extends GenericController{
    @FXML
    private Text nicknameLable;
    @FXML
    private Text textLable;

    /**
     * Method to show the nickname and the text.
     * @param nickname the nickname
     * @param txt the text
     */
    public void showNicknameAndText(String nickname,String txt){
        nicknameLable.setText(nickname);
        textLable.setText(txt);
    }


}
