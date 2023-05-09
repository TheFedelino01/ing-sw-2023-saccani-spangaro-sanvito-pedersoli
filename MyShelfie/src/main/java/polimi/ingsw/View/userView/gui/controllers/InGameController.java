package polimi.ingsw.View.userView.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.userView.gui.intRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        intRecord rowCol = getRowColFrom(e);
        Integer row = rowCol.row();
        Integer col = rowCol.col();

        if(!firstClick) {
          //Second click so client selected first and last Tile in the playground
            rowSecondTile=row;
            colSecondTile=col;
            System.out.println(rowFirstTile+":"+colFirstTile+" - "+rowSecondTile+":"+colSecondTile);
            checkAlignment(rowFirstTile,colFirstTile,rowSecondTile,colSecondTile);
        }else{
            rowFirstTile=row;
            colFirstTile=col;
        }
        firstClick=!firstClick;
    }
    private intRecord getRowColFrom(MouseEvent e){
        final Node source = (Node) e.getSource();
        String id = source.getId();
        String rowCol = id.replaceAll("pg", "");
        int row = Integer.parseInt(String.valueOf(rowCol.charAt(0)));
        int col = Integer.parseInt(String.valueOf(rowCol.charAt(1)));;
        return new intRecord(row,col);
    }

    public void actionMouseEnteredTile(MouseEvent e) throws IOException{
        if(rowFirstTile!=null && colFirstTile!=null && rowSecondTile==null && colSecondTile==null){
            makeTilesNotSelectedExpectTheFirstOne();
            //I make visible the selected tiles until second click
            Pane tilePane;
            intRecord destinPoint = getRowColFrom(e);
            List<intRecord> points = getPointsBetween(rowFirstTile,colFirstTile,destinPoint.row(),destinPoint.col());
            for(intRecord p:points){
                tilePane = (Pane) tilesPane.lookup("#pg"+p.row()+p.col());
                tilePane.getStyleClass().add("selected");
            }
        }
    }
    public void actionMouseEnteredPlayground(MouseEvent e){
        makeTilesNotSelectedExpectTheFirstOne();
    }

    private void makeTilesNotSelectedExpectTheFirstOne(){
        String selector = ".selected";
        tilesPane.lookupAll(selector).forEach(element -> {
            element.getStyleClass().remove("selected");
        });

        if(rowFirstTile!=null && colFirstTile!=null){
            ((Pane) tilesPane.lookup("#pg"+rowFirstTile+colFirstTile)).getStyleClass().add("selected");
        }
    }
    private List<intRecord> getPointsBetween(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile) {
        List<intRecord> points = new ArrayList<>();

        if (rowFirstTile == rowSecondTile || colFirstTile == colSecondTile) {
            int minRow = Math.min(rowFirstTile, rowSecondTile);
            int maxRow = Math.max(rowFirstTile, rowSecondTile);
            int minCol = Math.min(colFirstTile, colSecondTile);
            int maxCol = Math.max(colFirstTile, colSecondTile);

            if (rowFirstTile == rowSecondTile) {
                for (int col = minCol; col <= maxCol; col++) {
                    points.add(new intRecord(rowFirstTile, col));
                }
            } else {
                for (int row = minRow; row <= maxRow; row++) {
                    points.add(new intRecord(row, colFirstTile));
                }
            }
        }

        return points;
    }


    private void checkAlignment(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile) {
        Direction dir = null;
        Integer distance =null;

        if (rowFirstTile == rowSecondTile && Math.abs(colFirstTile - colSecondTile) <= 3) {
            distance = Math.abs(colFirstTile - colSecondTile) + 1;

            if(distance!=1) {
                if (colFirstTile < colSecondTile) {
                    dir=Direction.RIGHT;
                } else {
                    dir=Direction.LEFT;
                }
            }
        } else if (colFirstTile == colSecondTile && Math.abs(rowFirstTile - rowSecondTile) <= 3) {
            distance = Math.abs(rowFirstTile - rowSecondTile) + 1;

            if (rowFirstTile < rowSecondTile) {
                dir=Direction.DOWN;
            } else {
                dir=Direction.UP;
            }
        }
        //else return "false";


        if(dir!=null){
            getInputReaderGUI().addTxt(String.valueOf(distance));

            getInputReaderGUI().addTxt(String.valueOf(rowFirstTile));
            getInputReaderGUI().addTxt(String.valueOf(colFirstTile));

            if(distance!=1){
                getInputReaderGUI().addTxt(dir.toString());
            }
            this.rowFirstTile=null;
            this.colFirstTile=null;
            this.rowSecondTile=null;
            this.colSecondTile=null;
            makeTilesNotSelectedExpectTheFirstOne();
        }

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
