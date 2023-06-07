package polimi.ingsw.main;

import javafx.application.Application;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.flow.ConnectionSelection;
import polimi.ingsw.view.flow.GameFlow;
import polimi.ingsw.view.gui.GUIApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) {
        clearCMD();
        int selection;

        //Disable javaFX logger
        killLoggers();

        if (!DefaultValue.DEBUG) {
            String input;

            do {
                System.out.println(ansi().cursor(1, 0).a("""
                        Insert remote IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
                if(!input.equals("") && !isValidIP(input)){
                    System.out.println("Not valid");
                }
            } while (!input.equals("") && !isValidIP(input));
            if (!input.equals(""))
                DefaultValue.serverIp = input;

            clearCMD();

            do {
                System.out.println(ansi().cursor(1, 0).a("""
                        Insert your IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
            } while (!input.equals("") && !isValidIP(input));
            if (!input.equals(""))
                System.setProperty("java.rmi.server.hostname", input);


            clearCMD();
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
                try {
                    selection = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    selection = -1;
                    System.out.println("Nan");
                }
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

        System.out.println("Starting the game!");

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

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static void killLoggers(){
        com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();
    }

}
