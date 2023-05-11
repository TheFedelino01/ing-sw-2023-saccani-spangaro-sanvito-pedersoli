package polimi.ingsw.View.userView.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.userView.gui.intRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InGameController extends GenericController {

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
    private Label lableGameId;
    @FXML
    private  Label labelMessage;


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


    private boolean firstClick = true;
    private Integer rowFirstTile, colFirstTile, rowSecondTile, colSecondTile;
    private boolean needToDetectColSelection=false, needToDetectTileInHandGrabbing=false;

    public void actionClickOnTile(MouseEvent e) throws IOException {
        if (e.getButton() == MouseButton.PRIMARY) {
            intRecord rowCol = getRowColFrom(e,"pg");
            Integer row = rowCol.row();
            Integer col = rowCol.col();

            if (!firstClick) {
                //Second click so client selected first and last Tile in the playground
                rowSecondTile = row;
                colSecondTile = col;
                System.out.println(rowFirstTile + ":" + colFirstTile + " - " + rowSecondTile + ":" + colSecondTile);
                checkAlignment(rowFirstTile, colFirstTile, rowSecondTile, colSecondTile);
            } else {
                rowFirstTile = row;
                colFirstTile = col;
            }
            firstClick = !firstClick;
        } else {
            this.rowFirstTile = null;
            this.colFirstTile = null;
            this.rowSecondTile = null;
            this.colSecondTile = null;
            firstClick = true;
            makeTilesNotSelectedExpectTheFirstOne();
        }

    }

    public void actionHandTileClick(MouseEvent e) {
        if(needToDetectTileInHandGrabbing){
            Integer indexTileHandToPlace = getRowColFrom(e, "pgGrab").col();
            getInputReaderGUI().addTxt(indexTileHandToPlace.toString());
        }
    }

    private intRecord getRowColFrom(MouseEvent e, String prefixToRemove) {
        final Node source = (Node) e.getSource();
        String id = source.getId();
        String rowCol = id.replaceAll(prefixToRemove, "");
        int row = Integer.parseInt(String.valueOf(rowCol.charAt(0)));
        int col = Integer.parseInt(String.valueOf(rowCol.charAt(1)));

        return new intRecord(row, col);
    }

    public void actionMouseEnteredTile(MouseEvent e) throws IOException {
        if (rowFirstTile != null && colFirstTile != null && rowSecondTile == null && colSecondTile == null) {
            makeTilesNotSelectedExpectTheFirstOne();
            //I make visible the selected tiles until second click
            Pane tilePane;
            intRecord destinPoint = getRowColFrom(e,"pg");
            List<intRecord> points = getPointsBetween(rowFirstTile, colFirstTile, destinPoint.row(), destinPoint.col());
            for (intRecord p : points) {
                tilePane = (Pane) tilesPane.lookup("#pg" + p.row() + p.col());
                if (!tilePane.getStyleClass().contains("selected")) {
                    tilePane.getStyleClass().add("selected");
                }

            }
        }
    }

    public void actionMouseEnteredPlayground(MouseEvent e) {
        makeTilesNotSelectedExpectTheFirstOne();
    }

    private void makeTilesNotSelectedExpectTheFirstOne() {
        String selector = ".selected";
        tilesPane.lookupAll(selector).forEach(element -> {
            element.getStyleClass().remove("selected");
        });

        if (rowFirstTile != null && colFirstTile != null) {
            ((Pane) tilesPane.lookup("#pg" + rowFirstTile + colFirstTile)).getStyleClass().add("selected");
        }
    }

    public void actionTileShelfieClick(MouseEvent e){
        if(needToDetectColSelection) {
            Integer colToPlaceTiles = getRowColFrom(e, "youShelf").col();
            needToDetectColSelection=false;
            needToDetectTileInHandGrabbing=true;
            getInputReaderGUI().addTxt(colToPlaceTiles.toString());
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
        Integer distance = null;

        if (rowFirstTile == rowSecondTile && Math.abs(colFirstTile - colSecondTile) <= 3) {
            distance = Math.abs(colFirstTile - colSecondTile) + 1;

            if (distance != 1) {
                if (colFirstTile < colSecondTile) {
                    dir = Direction.RIGHT;
                } else {
                    dir = Direction.LEFT;
                }
            }
        } else if (colFirstTile == colSecondTile && Math.abs(rowFirstTile - rowSecondTile) <= 3) {
            distance = Math.abs(rowFirstTile - rowSecondTile) + 1;

            if (rowFirstTile < rowSecondTile) {
                dir = Direction.DOWN;
            } else {
                dir = Direction.UP;
            }
        }
        //else return "false";


        if (dir != null) {
            getInputReaderGUI().addTxt(String.valueOf(distance));

            getInputReaderGUI().addTxt(String.valueOf(rowFirstTile));
            getInputReaderGUI().addTxt(String.valueOf(colFirstTile));

            if (distance != 1) {
                getInputReaderGUI().addTxt(dir.toString());
            }
            this.rowFirstTile = null;
            this.colFirstTile = null;
            this.rowSecondTile = null;
            this.colSecondTile = null;
            makeTilesNotSelectedExpectTheFirstOne();
        }

    }

    public void setNicknamesAndPoints(GameModelImmutable model, String nickname) {
        youNickname.setTextFill(Color.WHITE);
        playerLabel1.setTextFill(Color.WHITE);
        playerLabel2.setTextFill(Color.WHITE);
        playerLabel3.setTextFill(Color.WHITE);
        Integer refToGui;
        Label labelNick = null, labelPoints = null;

        for (Player p : model.getPlayers()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());
            switch (refToGui) {
                case 0 -> {
                    labelNick = youNickname;
                    labelPoints = youPoints;
                }
                case 1 -> {
                    labelNick = playerLabel1;
                    labelPoints = player1Points;
                }
                case 2 -> {
                    labelNick = playerLabel2;
                    labelPoints = player2Points;
                }
                case 3 -> {
                    labelNick = playerLabel3;
                    labelPoints = player3Points;
                }
            }

            labelNick.setText(p.getNickname());
            labelPoints.setText(String.valueOf(p.getTotalPoints()));
            if (model.getNicknameCurrentPlaying().equals(p.getNickname())) {
                labelNick.setTextFill(Color.YELLOW);
            }
        }

    }

    private Integer getReferringPlayerIndex(GameModelImmutable model, String personalNickname, String nickToGetRef) {
        if (personalNickname.equals(nickToGetRef))
            return 0;

        int offset = 1;
        int playerNum = model.getPlayers().size();
        String otherNick;

        for (int i = 0; i < playerNum; i++) {
            otherNick = model.getPlayers().get(i).getNickname();

            if (!otherNick.equals(personalNickname)) {
                if (otherNick.equals(nickToGetRef)) {
                    return offset;
                }
                offset++;
            }

        }
        return null;
    }


    public void setPlayground(GameModelImmutable model) {
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

        pgTilesTotal.setText(String.valueOf(model.getPg().getNumOfTileinTheBag()));
    }

    public void setCommonCards(GameModelImmutable model) {
        Pane tilePane;
        tilePane = (Pane) mainAnchor.lookup("#cc0");

        tilePane.getStyleClass().add(model.getCommonCards().get(0).getCommonType().getBackgroundClass());
        tilePane.setVisible(true);

        tilePane = (Pane) mainAnchor.lookup("#cc1");
        tilePane.getStyleClass().add(model.getCommonCards().get(1).getCommonType().getBackgroundClass());
        tilePane.setVisible(true);
    }


    public void setVisibleShelves(GameModelImmutable model) {
        Pane pane;

        setInvisibleAllShelves();

        int playerNum = model.getPlayers().size();
        for (int i = 1; i < playerNum; i++) {
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(true);

            //showPersonalCard(i+1,model.getPlayers().get(i).getSecretGoal().getGoalType().getBackgroundClass());
        }

        lableGameId.setText(model.getGameId().toString());
    }

    private void setInvisibleAllShelves() {
        Pane pane;
        for (int i = 1; i <= DefaultValue.MaxNumOfPlayer - 1; i++) {
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(false);
        }
    }


    public void setPersonalCard(GameModelImmutable model, String nickname) {
        youPersonal.getStyleClass().add(model.getPlayerEntity(nickname).getSecretGoal().getGoalType().getBackgroundClass());
    }

    public void setHandTiles(GameModelImmutable model, String nickname) {
        if (model.getNicknameCurrentPlaying().equals(nickname)) {
            Pane pane;
            int i = 0;
            setEmptyHand();
            for (Tile t : model.getHandOfCurrentPlaying()) {
                pane = (Pane) mainAnchor.lookup("#pgGrab0" + i);
                pane.getStyleClass().add(t.getType().getBackgroundClass());
                pane.setOpacity(1);
                i++;
            }
        } else {
            setEmptyHand();
        }
    }

    public void setPlayerGrabbedTiles(GameModelImmutable model, String nickname) {
        setPlayground(model);
        this.rowFirstTile = null;
        this.colFirstTile = null;
        this.rowSecondTile = null;
        this.colSecondTile = null;
        makeTilesNotSelectedExpectTheFirstOne();
        setHandTiles(model, nickname);
    }

    private void setEmptyHand() {
        Pane pane = (Pane) mainAnchor.lookup("#pgGrab00");
        pane.getStyleClass().add("tileEmpty");
        pane.setOpacity(0.9);
        pane.setVisible(true);
    }

    public void setAllShefies(GameModelImmutable model, String nickname){
        String prefixIdPane=null;
        Integer refToGui;
        //setInvisibleAllShelfies();

        for (Player p : model.getPlayers()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());

            switch (refToGui) {
                case 0 -> {
                    prefixIdPane="#youShelf";
                }
                case 1 -> {
                    prefixIdPane="#player1Shelf";
                }
                case 2 -> {
                    prefixIdPane="#player2Shelf";
                }
                case 3 -> {
                    prefixIdPane="#player3Shelf";
                }
            }

            setShelfie(p.getShelf(),prefixIdPane);

        }
    }
    private void setInvisibleAllShelfies(){
        Pane paneTile;
        for(int r=0; r<DefaultValue.NumOfRowsShelf;r++){
            for(int c=0; c<DefaultValue.NumOfColumnsShelf;c++){
                paneTile = (Pane) mainAnchor.lookup("#youShelf"+r+c);
                paneTile.setVisible(false);

                paneTile = (Pane) mainAnchor.lookup("#player1Shelf"+r+c);
                paneTile.setVisible(false);

                paneTile = (Pane) mainAnchor.lookup("#player2Shelf"+r+c);
                paneTile.setVisible(false);

                paneTile = (Pane) mainAnchor.lookup("#player3Shelf"+r+c);
                paneTile.setVisible(false);
            }
        }
    }

    private void setShelfie(Shelf shelf, String prefixIdPane){
        Pane paneTile;
        for(int r=0; r<DefaultValue.NumOfRowsShelf;r++){
            for(int c=0; c<DefaultValue.NumOfColumnsShelf;c++){
                paneTile = (Pane) mainAnchor.lookup(prefixIdPane+r+c);
                if(!(shelf.get(r,c).getType().equals(TileType.NOT_USED) || shelf.get(r,c).getType().equals(TileType.FINISHED_USING))){
                    paneTile.getStyleClass().add(shelf.get(r,c).getType().getBackgroundClass());
                }

            }
        }
    }

    public void setMsgToShow(String msg,Boolean success) {
        labelMessage.setText(msg);
        if(success==null){
            labelMessage.setTextFill(Color.WHITE);
        }else if(success){
            labelMessage.setTextFill(Color.GREEN);
        }else{
            labelMessage.setTextFill(Color.RED);
        }
    }
    public void changeTurn(GameModelImmutable model, String nickname) {
        needToDetectTileInHandGrabbing=false;
    }


    public void showSelectionColShelfie() {
        //Now the client can select a col from his shelfie to position all tiles
        needToDetectColSelection=true;
    }


}