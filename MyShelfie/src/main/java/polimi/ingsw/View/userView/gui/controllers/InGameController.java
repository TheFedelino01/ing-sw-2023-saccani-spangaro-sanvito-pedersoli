package polimi.ingsw.View.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Tile;

import java.io.IOException;

public class InGameController extends GenericController{

    @FXML
    private Label lablePlayer1;
    @FXML
    private Label lablePlayer2;
    @FXML
    private Label lablePlayer3;
    @FXML
    private Label lablePlayer4;

    @FXML
    private Pane tilesPane;

    @FXML
    private AnchorPane mainAnchor;

    private boolean firstClick=true;
    private Integer rowFirstTile,colFirstTile,rowSecondTile,colSecondTile;
    public void actionClickOnTile(MouseEvent e) throws IOException {
        final Node source = (Node) e.getSource();
        String id = source.getId();
        String rowCol = id.replaceAll("pg", "");
        Integer row = Integer.parseInt(String.valueOf(rowCol.charAt(0)));
        Integer col = Integer.parseInt(String.valueOf(rowCol.charAt(1)));;

        if(!firstClick) {
          //Second click so client selected first and last Tile in the playground
            rowSecondTile=row;
            colSecondTile=col;
            System.out.println(rowFirstTile+":"+colFirstTile+" - "+rowSecondTile+":"+colSecondTile);
        }else{
            rowFirstTile=row;
            colFirstTile=col;
        }
        firstClick=!firstClick;
    }

    public void setNickname(GameModelImmutable model){
        int playerNum = model.getPlayers().size();
        for (int i = 0; i < playerNum; i++) {
            switch (i){
                case 0->{
                    lablePlayer1.setText(model.getPlayers().get(i).getNickname());
                }
                case 1->{
                    lablePlayer2.setText(model.getPlayers().get(i).getNickname());
                }
                case 2->{
                    lablePlayer3.setText(model.getPlayers().get(i).getNickname());
                }
                case 3->{
                    lablePlayer4.setText(model.getPlayers().get(i).getNickname());
                }
            }
        }
    }

    public void setPlayground(GameModelImmutable model){
        Tile t;
        Pane tilePane;
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                t = model.getPg().getTile(r, c);

                tilePane = (Pane) tilesPane.lookup("#pg" + r + c);

                if (tilePane != null) {
                    if (!t.getType().equals(TileType.NOT_USED) && !t.getType().equals(TileType.USED) && !t.getType().equals(TileType.FINISHED_USING)) {
                        tilePane.getStyleClass().add(t.getType().getBackgroundClass());
                        tilePane.getStyleClass().add("tileHover");
                        tilePane.setVisible(true);
                    } else {
                        tilePane.setVisible(false);
                    }
                }

            }
        }
    }

    public void setCommonCards(GameModelImmutable model){
        Pane tilePane;
        tilePane = (Pane) mainAnchor.lookup("#cc0");

        tilePane.getStyleClass().add(model.getCommonCards().get(0).getCommonType().getBackgroundClass());
        tilePane.getStyleClass().add("tileHover");
        tilePane.setVisible(true);

        tilePane = (Pane) mainAnchor.lookup("#cc1");
        tilePane.getStyleClass().add(model.getCommonCards().get(1).getCommonType().getBackgroundClass());
        tilePane.getStyleClass().add("tileHover");
        tilePane.setVisible(true);
    }

    public void setInvisibleAllShelves(){
        Pane pane;
        for(int i=1;i<=DefaultValue.MaxNumOfPlayer;i++){
            pane = (Pane) mainAnchor.lookup("#player" + (i));
            pane.setVisible(false);
        }
    }

    public void setVisibleShelves(GameModelImmutable model){
        Pane pane;

        int playerNum = model.getPlayers().size();
        //Show personal goal and shelf of the player in game
        for (int i = 0; i < playerNum; i++) {
            pane = (Pane) mainAnchor.lookup("#player" + (i + 1));
            pane.setVisible(true);

            showPersonalCard(i+1,model.getPlayers().get(i).getSecretGoal().getGoalType().getBackgroundClass());
        }
    }

    private void showPersonalCard(int playerIndex, String backgroundClass) {
        Pane tilePane;
        tilePane = (Pane) mainAnchor.lookup("#pc" + playerIndex);
        tilePane.getStyleClass().add(backgroundClass);
        tilePane.getStyleClass().add("tileHover");
        tilePane.setVisible(true);

    }

}
