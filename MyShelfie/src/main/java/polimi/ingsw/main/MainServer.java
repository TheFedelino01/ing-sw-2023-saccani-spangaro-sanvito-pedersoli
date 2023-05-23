package polimi.ingsw.main;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.view.networking.RMI.RMIServer;
import polimi.ingsw.view.networking.socket.server.SocketWelcome;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainServer {

    public static void main(String[] args) throws IOException {

        String input;

        do {
            System.out.println(ansi().cursor(1, 0).a("""
                        Insert remote IP (leave empty for localhost)
                        """));
            input = new Scanner(System.in).nextLine();
        } while (!input.equals("") && !isValidIP(input));
        if (input.equals(""))
            System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        else
            System.setProperty("java.rmi.server.hostname", input);

        RMIServer.bind();

        SocketWelcome serverSOCKET = new SocketWelcome();
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

}
