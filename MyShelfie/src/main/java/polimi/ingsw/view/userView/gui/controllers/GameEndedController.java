package polimi.ingsw.view.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;

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

    public void show(GameModelImmutable model) {
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        btnMenu.setVisible(true);//default visible todo need to check if player is choosing to grab tiles etc

        int i=0;
        Label tmp = null;
        for(PlayerIC p:model.getPlayers()){
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
    }

    public void showBtnReturnToMenu() {
        btnMenu.setVisible(true);//not necessary
    }

    public void actionReturnMenu(ActionEvent e){
        getInputReaderGUI().addTxt("a");
    }
}
