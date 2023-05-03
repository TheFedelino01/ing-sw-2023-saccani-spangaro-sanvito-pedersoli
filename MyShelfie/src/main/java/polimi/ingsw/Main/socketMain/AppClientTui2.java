package polimi.ingsw.Main.socketMain;

import polimi.ingsw.View.networking.socket.client.ClientSocket;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.UISelection;
import polimi.ingsw.View.userView.Flow;
import polimi.ingsw.View.userView.GameFlow;

import java.io.IOException;

public class AppClientTui2 {
    private static ClientSocket client2;

    public static void main(String[] args) throws IOException {
        Flow gui = new GameFlow(ConnectionSelection.SOCKET, UISelection.TUI);
        client2 = new ClientSocket(gui);

    }
}
