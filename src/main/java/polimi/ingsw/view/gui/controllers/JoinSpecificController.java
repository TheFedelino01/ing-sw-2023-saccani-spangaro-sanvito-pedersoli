package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * JoinSpecificController class.
 */
public class JoinSpecificController extends GenericController{

    @FXML
    private TextField gameIdTextField;

    /**
     * Method to control the game id field
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionEnter(ActionEvent e) throws IOException {
        if(!gameIdTextField.getText().isEmpty()){
            getInputReaderGUI().addTxt(gameIdTextField.getText());
        }
    }
}
