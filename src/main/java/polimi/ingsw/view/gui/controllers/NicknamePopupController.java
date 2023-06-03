package polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class NicknamePopupController extends GenericController{
    @FXML
    private Text nicknameLable;
    @FXML
    private Text textLable;

    public void showNicknameAndText(String nickname,String txt){
        nicknameLable.setText(nickname);
        textLable.setText(txt);
    }


}
