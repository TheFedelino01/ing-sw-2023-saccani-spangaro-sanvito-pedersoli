package polimi.ingsw.Main;

import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;
import polimi.ingsw.View.userView.UISelection;

import java.io.IOException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) throws Exception {
        String UISelectionStr, protocolSelectionStr;
        Flow view;
        boolean debug=false;

        if(!debug) {
            do{
                clearCMD();
                System.out.println(ansi().cursor(1, 0).a("""
                        Select view:
                        \t (1) TUI
                        \t (2) GUI
                        """));
                UISelectionStr = new Scanner(System.in).nextLine();
            } while (!UISelectionStr.equals("1") && !UISelectionStr.equals("2"));

            do {
                clearCMD();
                System.out.println(ansi().cursor(1, 0).a("""
                        Select communication protocol:
                        \t (1) Socket
                        \t (2) RMI
                        """));
                protocolSelectionStr = new Scanner(System.in).nextLine();
            } while (!protocolSelectionStr.equals("1") && !protocolSelectionStr.equals("2"));
        }else{
            UISelectionStr="2";
            protocolSelectionStr="1";
        }

        ConnectionSelection conSel=null;
        if(Integer.parseInt(protocolSelectionStr)==1){
            conSel=ConnectionSelection.SOCKET;
        }else if (Integer.parseInt(protocolSelectionStr)==2){
            conSel=ConnectionSelection.RMI;
        }

        UISelection uiSel=null;
        if(Integer.parseInt(UISelectionStr)==1){
            uiSel= UISelection.TUI;
        }else if (Integer.parseInt(UISelectionStr)==2){
            uiSel=UISelection.GUI;
        }

        new GameFlow(conSel,uiSel);


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
