package polimi.ingsw.Main.socketMain;


import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;
import java.util.Scanner;

import java.io.IOException;

public class AppClient {
    private static ClientSocket client;
    private static Scanner keys;
    public static void main(String[] args) throws IOException {
        View gui = new TextUI(ConnectionSelection.SOCKET);
        client = new ClientSocket(gui);
    }
}
