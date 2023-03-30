package polimi.ingsw.Model;

import polimi.ingsw.View.RMI.ClientResponsesInterface;

import java.io.Serializable;

public class ControllerAndPlayer implements Serializable {
    private ClientResponsesInterface lis;
    private Player p;

    public ControllerAndPlayer(ClientResponsesInterface lis, Player p) {
        this.lis = lis;
        this.p = p;
    }

    public ClientResponsesInterface getGameControllerInterface() {
        return lis;
    }

    public Player getPlayerIdentity() {
        return p;
    }
}
