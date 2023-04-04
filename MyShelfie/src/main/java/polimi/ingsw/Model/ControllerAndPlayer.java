package polimi.ingsw.Model;

import polimi.ingsw.View.RMI.ClientResponsesInterface;
import polimi.ingsw.View.RMI.PlayerInterface;
import polimi.ingsw.View.RMI.RemoteResultInterface;

import java.io.Serializable;
import java.rmi.Remote;

public class ControllerAndPlayer implements RemoteResultInterface,Serializable {
    private ClientResponsesInterface lis;
    private PlayerInterface p;

    public ControllerAndPlayer(ClientResponsesInterface lis, Player p) {
        this.lis = lis;
        this.p = p;
    }


    @Override
    public ClientResponsesInterface getGameControllerInterface() {
        return lis;
    }

    @Override
    public void setGameControllerInterface(ClientResponsesInterface c) {
        this.lis=c;
    }

    @Override
    public PlayerInterface getPlayerIdentity() {
        return p;
    }

    @Override
    public void setPlayerIdentity(PlayerInterface pi) {
        this.p=pi;
    }

}
