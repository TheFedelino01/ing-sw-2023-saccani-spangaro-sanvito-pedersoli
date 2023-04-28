package polimi.ingsw.View.userView.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ClientGameController {
    private String Nickname;
    @FXML
    Text NickNameClient1;

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public void GUIInit()
    {
        setNickname("test");
        NickNameClient1.setText(Nickname);
    }
    public void initialize(){
        GUIInit();
    }
}
