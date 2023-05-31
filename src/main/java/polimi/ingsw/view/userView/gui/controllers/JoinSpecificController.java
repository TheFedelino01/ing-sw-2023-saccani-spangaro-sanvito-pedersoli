package polimi.ingsw.view.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class JoinSpecificController extends GenericController{

    @FXML
    private TextField gameIdTextField;

    public void actionEnter(ActionEvent e) throws IOException {
        if(!gameIdTextField.getText().isEmpty()){
            getInputReaderGUI().addTxt(gameIdTextField.getText());
        }
        //System.out.println("c");
    }
}
