package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * This class is the controller for the generic error popup.
 */
public class GenericErrorController extends GenericController{
    @FXML
    private TextArea msg;

    @FXML
    private Button btn;
    private boolean needToExitApp;

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionMenu(ActionEvent e){
        if(!needToExitApp) {
            getInputReaderGUI().addTxt("a");
        }else{
            System.exit(-1);
        }
    }



    /**
     * Method to set the message
     * @param msg the message
     * @param needToExitApp true if the app needs to be closed
     */
    public void setMsg(String msg, boolean needToExitApp){
        this.msg.setText(msg);
        if(needToExitApp){
            btn.setText("Close App");
        }
        this.needToExitApp=needToExitApp;
    }
}
