package polimi.ingsw.view.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import polimi.ingsw.view.gui.Sound;

import java.io.IOException;

/**
 * LobbyController class.
 */
public class LobbyController extends GenericController{

    @FXML
    private Text nicknameLable;
    @FXML
    private Button btnReady;

    @FXML
    private Text gameidLabel;

    /**
     * Method to control the ready button.
     *
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionIamReady(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("y");
        Sound.playSound("ding.wav");
    }

    /**
     * Method to set the nickname label
     *
     * @param nickname the nickname
     */
    public void setUsernameLabel(String nickname){
        nicknameLable.setText(nickname);
    }

    /**
     * Method to set game id lable
     *
     * @param id the id to show
     */
    public void setGameid(int id) {
        gameidLabel.setText("GameID: "+id);
    }

    /**
     * method to set the visibility of the ready button
     *
     * @param visibility the visibility
     */
    public void setVisibleBtnReady(boolean visibility){
        btnReady.setVisible(visibility);
    }


}
