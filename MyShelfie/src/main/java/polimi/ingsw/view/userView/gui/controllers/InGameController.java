package polimi.ingsw.view.userView.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;
import polimi.ingsw.view.userView.gui.IntRecord;

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
    private Label labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;
    @FXML
    private ListView importantEventsList;



    @FXML
    private ComboBox comboBoxMessage;


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
    private boolean needToDetectColSelection = false, needToDetectTileInHandGrabbing = false;

    public void actionClickOnTile(MouseEvent e) {
        if(!needToDetectColSelection && !needToDetectTileInHandGrabbing) {
            if (e.getButton() == MouseButton.PRIMARY) {
                IntRecord rowCol = getRowColFrom(e, "pg");
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

    }

    public void actionHandTileClick(MouseEvent e) {
        if (needToDetectTileInHandGrabbing) {
            Integer indexTileHandToPlace = getRowColFrom(e, "pgGrab").col();
            getInputReaderGUI().addTxt(indexTileHandToPlace.toString());
        }
    }

    private IntRecord getRowColFrom(MouseEvent e, String prefixToRemove) {
        final Node source = (Node) e.getSource();
        String id = source.getId();
        String rowCol = id.replaceAll(prefixToRemove, "");
        int row = Integer.parseInt(String.valueOf(rowCol.charAt(0)));
        int col = Integer.parseInt(String.valueOf(rowCol.charAt(1)));

        return new IntRecord(row, col);
    }

    public void actionMouseEnteredTile(MouseEvent e) {
        if (rowFirstTile != null && colFirstTile != null && rowSecondTile == null && colSecondTile == null) {
            makeTilesNotSelectedExpectTheFirstOne();
            //I make visible the selected tiles until second click
            Pane tilePane;
            IntRecord destinPoint = getRowColFrom(e, "pg");
            List<IntRecord> points = getPointsBetween(rowFirstTile, colFirstTile, destinPoint.row(), destinPoint.col());
            for (IntRecord p : points) {
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

    public void actionTileShelfieClick(MouseEvent e) {
        if (needToDetectColSelection && !needToDetectTileInHandGrabbing) {
            deselectAllCols();
            Integer colToPlaceTiles = getRowColFrom(e, "youShelf").col();
            needToDetectColSelection = false;

            needToDetectTileInHandGrabbing = true;
            changeCursorOnTilesPlayground(Cursor.DEFAULT);
            changeCursorOnTilesMyShelf(Cursor.DEFAULT);
            changeCursorOnInHandTiles(Cursor.HAND);

            getInputReaderGUI().addTxt(colToPlaceTiles.toString());
        }
    }

    public void actionSendMessage(MouseEvent e) {
        if (!messageText.getText().isEmpty()) {
            if (comboBoxMessage.getValue().toString().isEmpty()) {
                getInputReaderGUI().addTxt("/c " + messageText.getText());
            } else {
                //Player wants to send a private message
                getInputReaderGUI().addTxt("/cs " + comboBoxMessage.getValue().toString() + " " + messageText.getText());
                comboBoxMessage.getSelectionModel().selectFirst();
            }
            messageText.setText("");
        }
    }

    public void actionKeyPressedOnTextMessage(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            actionSendMessage(null);
        }
    }

    public void actionMouseEntered(MouseEvent e){
        if(needToDetectColSelection) {
            Pane tilePane;
            deselectAllCols();
            IntRecord rowCol = getRowColFrom(e, "youShelf");
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
                tilePane = (Pane) mainAnchor.lookup("#youShelf"+r+rowCol.col());
                tilePane.getStyleClass().add("selectedCols");
            }
        }
    }
    public void actionMouseExited(MouseEvent e){
        deselectAllCols();
    }


    public void actionMouseEnteredCommonCard(MouseEvent e){
        final Node source = (Node) e.getSource();
        String id = source.getId();
        switch (id){
            case "cc0"->{
                ((Pane)source).setMinWidth(490);
                ((Pane)source).setMinHeight(310);
            }
            case "cc1"->{
                ((Pane)source).setMinWidth(490);
                ((Pane)source).setMinHeight(310);
            }
            case "youPersonal"->{
                ((Pane)source).setMinWidth(260);
                ((Pane)source).setMinHeight(400);
            }
        }
    }
    public void actionMouseExitedCommonCard(MouseEvent e){
        final Node source = (Node) e.getSource();
        String id = source.getId();
        switch (id){
            case "cc0"->{
                ((Pane)source).setMinWidth(190);
                ((Pane)source).setMinHeight(110);
            }
            case "cc1"->{
                ((Pane)source).setMinWidth(190);
                ((Pane)source).setMinHeight(110);
            }
            case "youPersonal"->{
                ((Pane)source).setMinWidth(130);
                ((Pane)source).setMinHeight(200);
            }
        }
    }

    private void deselectAllCols(){
        Pane tilePane;
        for(int r=0; r<DefaultValue.NumOfRowsShelf;r++){
            for(int c=0; c<DefaultValue.NumOfColumnsShelf;c++){
                tilePane = (Pane) mainAnchor.lookup("#youShelf"+r+c);
                if (tilePane.getStyleClass().contains("selectedCols")) {
                    tilePane.getStyleClass().remove("selectedCols");
                }
            }
        }
    }



    private List<IntRecord> getPointsBetween(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile) {
        List<IntRecord> points = new ArrayList<>();

        if (rowFirstTile == rowSecondTile || colFirstTile == colSecondTile) {
            int minRow = Math.min(rowFirstTile, rowSecondTile);
            int maxRow = Math.max(rowFirstTile, rowSecondTile);
            int minCol = Math.min(colFirstTile, colSecondTile);
            int maxCol = Math.max(colFirstTile, colSecondTile);

            if (rowFirstTile == rowSecondTile) {
                for (int col = minCol; col <= maxCol; col++) {
                    points.add(new IntRecord(rowFirstTile, col));
                }
            } else {
                for (int row = minRow; row <= maxRow; row++) {
                    points.add(new IntRecord(row, colFirstTile));
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
        }else{
            return;
        }


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

        if (comboBoxMessage.getItems().size() == 0) {
            comboBoxMessage.getItems().add("");
            for (Player p : model.getPlayers()) {

                if (!p.getNickname().equals(nickname))
                    comboBoxMessage.getItems().add(p.getNickname());
            }
            comboBoxMessage.getSelectionModel().selectFirst();
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

                removeallBackgroundClass(tilePane);

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

    private void removeallBackgroundClass(Pane tilePane) {
        if (tilePane != null) {
            for (TileType t : TileType.values()) {
                if (tilePane.getStyleClass().contains(t.getBackgroundClass())) {
                    tilePane.getStyleClass().remove(t.getBackgroundClass());
                }
            }
        }
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
        float opacity = 0.5f;

        if (model.getNicknameCurrentPlaying().equals(nickname)) {
            opacity = 1;
        }

        Pane pane;
        int i = 0;
        setEmptyHand();
        for (Tile t : model.getHandOfCurrentPlaying()) {
            pane = (Pane) mainAnchor.lookup("#pgGrab0" + i);
            pane.getStyleClass().remove(pane.getStyleClass().get(0));
            pane.getStyleClass().add(t.getType().getBackgroundClass());
            pane.setOpacity(opacity);
            i++;
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
        for (int i = 0; i < DefaultValue.maxNumOfGrabbableTiles; i++) {
            Pane pane = (Pane) mainAnchor.lookup("#pgGrab0" + i);
            pane.getStyleClass().remove(pane.getStyleClass().get(0));
            pane.getStyleClass().add("tileEmpty");
            pane.setOpacity(0.9);
            pane.setVisible(true);
        }
    }

    public void setAllShefies(GameModelImmutable model, String nickname) {
        String prefixIdPane = null;
        Integer refToGui;
        //setInvisibleAllShelfies();

        for (Player p : model.getPlayers()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());

            switch (refToGui) {
                case 0 -> {
                    prefixIdPane = "#youShelf";
                }
                case 1 -> {
                    prefixIdPane = "#player1Shelf";
                }
                case 2 -> {
                    prefixIdPane = "#player2Shelf";
                }
                case 3 -> {
                    prefixIdPane = "#player3Shelf";
                }
            }

            setShelfie(p.getShelf(), prefixIdPane);

        }
    }


    private void setShelfie(Shelf shelf, String prefixIdPane) {
        Pane paneTile;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                paneTile = (Pane) mainAnchor.lookup(prefixIdPane + r + c);
                if (!(shelf.get(r, c).getType().equals(TileType.NOT_USED) || shelf.get(r, c).getType().equals(TileType.FINISHED_USING))) {
                    paneTile.getStyleClass().add(shelf.get(r, c).getType().getBackgroundClass());
                }

            }
        }
    }

    public void setMsgToShow(String msg, Boolean success) {
        labelMessage.setText(msg);
        if (success == null) {
            labelMessage.setTextFill(Color.WHITE);
        } else if (success) {
            labelMessage.setTextFill(Color.GREEN);
        } else {
            labelMessage.setTextFill(Color.RED);
        }
    }



    public void changeTurn(GameModelImmutable model, String nickname) {
        needToDetectTileInHandGrabbing = false;

        setMsgToShow("Next turn is up to: "+model.getNicknameCurrentPlaying(),true);
        changeCursorOnTilesPlayground(Cursor.HAND);
        changeCursorOnTilesMyShelf(Cursor.DEFAULT);
        changeCursorOnInHandTiles(Cursor.DEFAULT);
    }


    public void showSelectionColShelfie() {
        //Now the client can select a col from his shelfie to position all tiles
        needToDetectColSelection = true;
        needToDetectTileInHandGrabbing=false;
        changeCursorOnTilesPlayground(Cursor.DEFAULT);
        changeCursorOnTilesMyShelf(Cursor.HAND);
        changeCursorOnInHandTiles(Cursor.DEFAULT);
    }

    private void changeCursorOnTilesPlayground(Cursor cu){
        Pane tilePane;
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                tilePane = (Pane) tilesPane.lookup("#pg" + r + c);

                if (tilePane != null) {
                        tilePane.setCursor(cu);
                }

            }
        }
    }
    private void changeCursorOnTilesMyShelf(Cursor cu){
        Pane paneTile;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                paneTile = (Pane) mainAnchor.lookup("#youShelf" + r + c);
                if(paneTile!=null){
                    paneTile.setCursor(cu);
                }
            }
        }
    }
    private void changeCursorOnInHandTiles(Cursor cu){
        Pane paneTile;
        paneTile = (Pane) mainAnchor.lookup("#pgGrab00");
        paneTile.setCursor(cu);

        paneTile = (Pane) mainAnchor.lookup("#pgGrab01");
        paneTile.setCursor(cu);

        paneTile = (Pane) mainAnchor.lookup("#pgGrab02");
        paneTile.setCursor(cu);

    }


    public void setMessage(List<Message> msgs, String myNickname) {
        chatList.getItems().clear();
        for (Message m : msgs) {
            String r = "[" + m.getTime().getHour() + ":" + m.getTime().getMinute() + ":" + m.getTime().getSecond() + "] "+m.getSender().getNickname() +": "+  m.getText();

            if (m.whoIsReceiver().equals("*")) {
                chatList.getItems().add(r);
            } else if (m.whoIsReceiver().toUpperCase().equals(myNickname.toUpperCase()) || m.getSender().getNickname().toUpperCase().equals(myNickname.toUpperCase())) {
                //Message private
                chatList.getItems().add("[Private] "+r);
            }
        }
    }

    public void setImportantEvents(List<String> importantEvents) {
        importantEventsList.getItems().clear();
        for(String s:importantEvents){
            importantEventsList.getItems().add(s);
        }
        importantEventsList.scrollTo(importantEventsList.getItems().size());
    }

    public void setPointsUpdated(GameModelImmutable model, Player playerPointChanged, String myNickname) {
        setNicknamesAndPoints(model,myNickname);
    }
}
