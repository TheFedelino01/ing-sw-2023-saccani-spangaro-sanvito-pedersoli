package polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import polimi.ingsw.view.gui.Sound;

import java.io.IOException;

/**
 * MenuController class.
 */
public class MenuController extends GenericController{

    @FXML
    Pane sound;


    /**
     * Method to create a new game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionCreateANewGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("c");
        Sound.playSound("placeTile.wav");
        //System.out.println("c");
    }
    /**
     * Method to join the first available game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionJoinFirstAvailableGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("j");
        Sound.playSound("placeTile.wav");
        //System.out.println("j");
    }
    /**
     * Method to join to a specific game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionJoinToASpecificGame(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("js");
        Sound.playSound("placeTile.wav");
        //System.out.println("js");
    }
    /**
     * Method to reconnect to a game.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionReconnect(ActionEvent e) throws IOException {
        getInputReaderGUI().addTxt("x");
        Sound.playSound("placeTile.wav");
        //System.out.println("x");
    }

    /**
     * Show Credit popup message
     * @param e the action event
     */
    public void actionShowInfo(MouseEvent e){
        Alert a = new Alert(Alert.AlertType.NONE,
                "Credit of the Game MyShelfie developed by Saccani Federico, Spangaro Francesco, Pedersoli Luca and Sanvito Luca. Last update 12/09/2023.", ButtonType.CLOSE);
        a.show();
        Sound.playSound("placeTile.wav");
    }

    /**
     * Change the sound icon
     * @param e event
     */
    public void actionSound(MouseEvent e){
        if(e!=null) {
            Sound.play = !Sound.play;
        }
        if(Sound.play){
            sound.getStyleClass().remove("soundOFF");
            if(!sound.getStyleClass().contains("soundON")){
                sound.getStyleClass().add("soundON");
                Sound.playSound("clickmenu.wav");
            }
        }else{
            sound.getStyleClass().remove("soundON");
            if(!sound.getStyleClass().contains("soundOFF")){
                sound.getStyleClass().add("soundOFF");
            }
        }
    }



}
