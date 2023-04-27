package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.io.IOException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class actualMain {

    public static void main(String[] args) {
        String choiceVisual, choiceComms;
        View view;
        boolean debug=true;
        if(!debug) {
            do {
                clearCMD();
                System.out.println(ansi().cursor(1, 0).a("""
                        Select view:
                        \t (1) TextUI
                        \t (2) GraphicUI
                        """));
                choiceVisual = new Scanner(System.in).nextLine();
            } while (!choiceVisual.equals("1") && !choiceVisual.equals("2"));

            do {
                clearCMD();
                System.out.println(ansi().cursor(1, 0).a("""
                        Select communication protocol:
                        \t (1) Socket
                        \t (2) RMI
                        """));
                choiceComms = new Scanner(System.in).nextLine();
            } while (!choiceComms.equals("1") && !choiceComms.equals("2"));
        }else{
            choiceVisual="1";
            choiceComms="1";
        }
        switch (Integer.parseInt(choiceComms)) {
            case 1 -> {
                switch (choiceVisual){
                    case "1" -> view = new TextUI(ConnectionSelection.SOCKET);
                    //case "2" -> view = new graphicUI(ConnectionSelection.SOCKET);
                    default -> view = null;
                }
                ClientSocket clientSocket = new ClientSocket(view);
            }
            case 2 -> {
                switch (choiceVisual){
                    case "1" -> view = new TextUI(ConnectionSelection.RMI);
                    //case "2" -> view = new graphicUI(ConnectionSelection.RMI);
                    default -> view = null;
                }
                RMIClient clientRMI = new RMIClient(view);
            }
        }

    }


    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            //if not on a Windows machine
        } catch (IOException | InterruptedException e) {
            //for mac
            System.out.print("\033\143");

            /*This might work too, but exec is deprecated
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

             */
        }
    }
}
