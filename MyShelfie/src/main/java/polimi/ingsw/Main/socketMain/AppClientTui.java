package polimi.ingsw.Main.socketMain;


import polimi.ingsw.View.networking.socket.client.ClientSocket;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;

import java.util.Scanner;

import java.io.IOException;

public class AppClientTui {
    private static ClientSocket client;
    private static Scanner keys;

    public static void main(String[] args) throws IOException {
        Flow gui = new GameFlow(ConnectionSelection.SOCKET, UISelection.TUI);
        client = new ClientSocket(gui);
    }
}
