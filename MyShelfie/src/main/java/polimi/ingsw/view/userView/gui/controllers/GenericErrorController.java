package polimi.ingsw.view.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.awt.desktop.SystemEventListener;

public class GenericErrorController extends GenericController{
    @FXML
    private TextArea msg;

    @FXML
    private Button btn;
    private boolean needToExitApp;
    public void actionMenu(ActionEvent e){
        if(!needToExitApp) {
            getInputReaderGUI().addTxt("a");
        }else{
            System.exit(-1);
        }
    }



    public void setMsg(String msg, boolean needToExitApp){
        this.msg.setText(msg);
        if(needToExitApp){
            btn.setText("Close App");
        }
        this.needToExitApp=needToExitApp;
    }
}
