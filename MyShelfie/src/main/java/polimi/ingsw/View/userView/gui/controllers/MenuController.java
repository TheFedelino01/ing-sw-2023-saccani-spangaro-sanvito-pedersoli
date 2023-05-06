package polimi.ingsw.View.userView.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController extends GenericController{

    public MenuController() {
    }

    public void actionCreateANewGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("c");
        //System.out.println("c");
    }
    public void actionJoinFirstAvailableGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("j");
        //System.out.println("j");
    }
    public void actionJoinToASpecificGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("js");
        //System.out.println("js");
    }
    public void actionReconnect(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("x");
        //System.out.println("x");
    }
}
