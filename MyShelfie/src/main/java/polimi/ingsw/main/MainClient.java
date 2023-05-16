package polimi.ingsw.main;

import javafx.application.Application;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.userView.ConnectionSelection;
import polimi.ingsw.view.userView.GameFlow;
import polimi.ingsw.view.userView.gui.GUIApplication;

import java.io.IOException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) throws Exception {
        clearCMD();
        Integer selection;

        if (!DefaultValue.DEBUG) {
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
        } else {
            selection = 2; //Default run configuration
        }


        //Get the Communication Protocol wanted
        ConnectionSelection conSel;
        if (selection == 1 || selection == 3) {
            conSel = ConnectionSelection.SOCKET;
        } else {
            conSel = ConnectionSelection.RMI;
        }

        //Starts the UI wanted
        if (selection == 1 || selection == 2) {
            //Starts the game with TUI
            //I can start directly here the GameFlow
            new GameFlow(conSel);
        } else {
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
