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
    private Pane tilesPane;

    @FXML
    private AnchorPane mainAnchor;


    @FXML
    private Label youNickname;
    @FXML
    private Label youPoints;
    @FXML
    private Pane youPersonal;


    @FXML
    private Label pgTilesTotal;


    @FXML
    private Label playerLabel1;
    @FXML
    private Label player1Points;


    @FXML
    private Label playerLabel2;
    @FXML
    private Label player2Points;


    @FXML
    private Label playerLabel3;
    @FXML
    private Label player3Points;


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

    public void setNicknamesAndPoints(GameModelImmutable model, String nickname){
        youNickname.setText(nickname);
        youPoints.setText(String.valueOf(model.getPlayerEntity(nickname).getTotalPoints()));

        int playerNum = model.getPlayers().size();
        String otherNick;

        for (int i = 0; i < playerNum; i++) {
            otherNick =model.getPlayers().get(i).getNickname();

            if(!otherNick.equals(nickname)) {
                switch (i) {
                    case 1 -> {
                        playerLabel1.setText(otherNick);
                        player1Points.setText(String.valueOf(model.getPlayers().get(i).getTotalPoints()));
                    }
                    case 2 -> {
                        playerLabel2.setText(otherNick);
                        player2Points.setText(String.valueOf(model.getPlayers().get(i).getTotalPoints()));
                    }
                    case 3 -> {
                        playerLabel3.setText(otherNick);
                        player3Points.setText(String.valueOf(model.getPlayers().get(i).getTotalPoints()));
                    }
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
        tilePane.setVisible(true);

        tilePane = (Pane) mainAnchor.lookup("#cc1");
        tilePane.getStyleClass().add(model.getCommonCards().get(1).getCommonType().getBackgroundClass());
        tilePane.setVisible(true);
    }


    public void setVisibleShelves(GameModelImmutable model){
        Pane pane;

        setInvisibleAllShelves();

        int playerNum = model.getPlayers().size();
        for (int i = 1; i < playerNum; i++) {
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(true);

            //showPersonalCard(i+1,model.getPlayers().get(i).getSecretGoal().getGoalType().getBackgroundClass());
        }
    }
    private void setInvisibleAllShelves(){
        Pane pane;
        for(int i=1;i<=DefaultValue.MaxNumOfPlayer-1;i++){
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(false);
        }
    }


    public void setPersonalCard(GameModelImmutable model,String nickname) {
        Pane tilePane;
        tilePane = (Pane) mainAnchor.lookup("#youPersonal");
        tilePane.getStyleClass().add(model.getPlayerEntity(nickname).getSecretGoal().getGoalType().getBackgroundClass());
        tilePane.setVisible(true);
    }

    public void setHandTiles(GameModelImmutable model, String nickname) {
        if(model.getNicknameCurrentPlaying().equals(nickname)){
            Pane pane;
            int i=0;
            setEmptyHand();
            for(Tile t: model.getHandOfCurrentPlaying()){
                pane = (Pane) mainAnchor.lookup("#pgGrab"+i);
                pane.getStyleClass().add(t.getType().getBackgroundClass());
                pane.setVisible(true);
                i++;
            }
        }else{
            setEmptyHand();
        }
    }

    private void setEmptyHand(){
        Pane pane = (Pane) mainAnchor.lookup("#pgGrab0");
        pane.getStyleClass().add("tileEmpty");
    }
}
