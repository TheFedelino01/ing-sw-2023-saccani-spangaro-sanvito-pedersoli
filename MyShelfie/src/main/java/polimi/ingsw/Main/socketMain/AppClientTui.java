package polimi.ingsw.Main.socketMain;


import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UiSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.GameFlow;

import java.util.Scanner;

import java.io.IOException;

public class AppClientTui {
    private static ClientSocket client;
    private static Scanner keys;

    public static void main(String[] args) throws IOException {
        View gui = new GameFlow(ConnectionSelection.SOCKET, UiSelection.TUI);
        client = new ClientSocket(gui);
    }
}
