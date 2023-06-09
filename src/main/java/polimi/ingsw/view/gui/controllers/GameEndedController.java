package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.view.gui.Sound;

/**
 * This class is the controller for the game ended scene.
 */
public class GameEndedController extends GenericController{
    @FXML
    private Label player0;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    @FXML
    private Button btnMenu;

    /**
     * Show everything that is needed.
     * @param model the model {@link GameModelImmutable}
     */
    public void show(GameModelImmutable model) {
        player0.setVisible(false);
        player0.setTextFill(Color.YELLOW);

        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        btnMenu.setVisible(true);

        int i=0;
        Label tmp = null;
        for(PlayerIC p:model.getScoreboard()){
            switch (i){
                case 0-> tmp=player0;
                case 1-> tmp=player1;
                case 2-> tmp=player2;
                case 3-> tmp=player3;
            }

            tmp.setText(p.getNickname()+": "+p.getTotalPoints()+" points");
            tmp.setVisible(true);
            i++;
        }

        Sound.playSound("tada.wav");
    }

    /**
     * Show the button to return to the menu.
     */
    public void showBtnReturnToMenu() {
        btnMenu.setVisible(true);//not necessary
    }

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionReturnMenu(ActionEvent e){
        getInputReaderGUI().addTxt("a");
    }
}
