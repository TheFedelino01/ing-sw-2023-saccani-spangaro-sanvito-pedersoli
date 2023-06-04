package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * NicknameController class.
 */
public class NicknameController extends GenericController{

    @FXML
    private TextField nickNameTextField;

    /**
     * Method to control the nickname.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionEnter(ActionEvent e) throws IOException {
        if(!nickNameTextField.getText().isEmpty()) {
            getInputReaderGUI().addTxt(nickNameTextField.getText());
        }
    }
}
