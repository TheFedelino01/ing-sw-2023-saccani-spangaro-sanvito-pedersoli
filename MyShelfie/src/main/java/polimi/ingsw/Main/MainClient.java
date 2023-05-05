package polimi.ingsw.Main;

import javafx.application.Application;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.gui.GUIApplication;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) throws Exception {
        clearCMD();
        Integer selection;

        if(!DefaultValue.DEBUG) {
            String input = null;

            do {
                System.out.println(ansi().cursor(1, 0).a("""
                        Select option:
                        \t (1) TUI + Socket
                        \t (2) TUI + RMI
                        \t
                        \t (3) GUI + Socket
                        \t (4) GUI + RMI
                        """));
                input = new Scanner(System.in).nextLine();
                selection = Integer.parseInt(input);
            } while (selection != 1 && selection != 2 && selection != 3 && selection != 4);
        }else{
            selection=2; //Default run configuration
        }


        //Get the Communication Protocol wanted
        ConnectionSelection conSel=null;
        if(selection==1 || selection==3){
            conSel=ConnectionSelection.SOCKET;
        }else if(selection==2 || selection==4){
            conSel=ConnectionSelection.RMI;
        }

        //Starts the UI wanted
        if(selection==1 || selection==2){
            //Starts the game with TUI
            //I can start directly here the GameFlow
            new GameFlow(conSel);
        }else if (selection==3 || selection==4){
            //Starts the game with GUI
            //For doing so, I need to start the Main of GUI (GameFlow needs to be started inside the thread of GUI)
            Application.launch(GUIApplication.class, conSel.toString());
        }

    }


    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.print("\033\143");   //for Mac
        }
    }
}
