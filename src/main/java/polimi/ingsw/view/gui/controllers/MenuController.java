package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * MenuController class.
 */
public class MenuController extends GenericController{

    public MenuController() {
    }

    /**
     * Method to create a new game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionCreateANewGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("c");
        //System.out.println("c");
    }
    /**
     * Method to join the first available game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionJoinFirstAvailableGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("j");
        //System.out.println("j");
    }
    /**
     * Method to join to a specific game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionJoinToASpecificGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("js");
        //System.out.println("js");
    }
    /**
     * Method to reconnect to a game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionReconnect(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("x");
        //System.out.println("x");
    }

}
