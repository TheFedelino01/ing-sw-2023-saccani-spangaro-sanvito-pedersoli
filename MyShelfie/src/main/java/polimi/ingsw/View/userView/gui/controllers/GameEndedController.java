package polimi.ingsw.View.userView.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;

public class GameEndedController extends GenericController{
    @FXML
    private Label player0;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    public void show(GameModelImmutable model) {
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        int i=0;
        Label tmp = null;
        for(Player p:model.getPlayers()){
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
}
