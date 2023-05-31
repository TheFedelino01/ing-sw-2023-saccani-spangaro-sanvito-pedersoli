package polimi.ingsw.view.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NicknameController extends GenericController{

    @FXML
    private TextField nickNameTextField;
    public void actionEnter(ActionEvent e) throws IOException {
        if(!nickNameTextField.getText().isEmpty()) {
            getInputReaderGUI().addTxt(nickNameTextField.getText());
        }
    }
}
