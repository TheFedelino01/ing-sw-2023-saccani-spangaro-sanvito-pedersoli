package polimi.ingsw.view.userView.gui.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

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
