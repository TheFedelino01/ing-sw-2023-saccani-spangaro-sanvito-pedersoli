package polimi.ingsw.View.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GenericErrorController extends GenericController{
    @FXML
    private TextArea msg;

    public void actionMenu(ActionEvent e){
        getInputReaderGUI().addTxt("a");
    }

    public void setMsg(String msg){
        this.msg.setText(msg);
    }
}
