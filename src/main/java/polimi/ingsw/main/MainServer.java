package polimi.ingsw.main;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.networking.rmi.RMIServer;
import polimi.ingsw.networking.socket.server.Server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainServer {

    public static void main(String[] args) throws IOException {

        String input;

        do {
            clearCMD();
            System.out.println(ansi().cursor(1, 0).a("""
                    Insert remote IP (leave empty for localhost)
                    """));
            input = new Scanner(System.in).nextLine();
        } while (!input.equals("") && !isValidIP(input));
        if (input.equals(""))
            System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        else{
            DefaultValue.serverIp = input;
            System.setProperty("java.rmi.server.hostname", input);
        }

        RMIServer.bind();

        Server serverSOCKET = new Server();
        serverSOCKET.start(DefaultValue.Default_port_Socket);

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


    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.print("\033\143");   //for Mac
        }
    }

}
