package polimi.ingsw.View.RMI;

import polimi.ingsw.Model.Player;

import java.rmi.Remote;

public interface RemoteResultInterface extends Remote {

    public ClientResponsesInterface getGameControllerInterface();
    public void setGameControllerInterface(ClientResponsesInterface c);

    public PlayerInterface getPlayerIdentity();
    public void setPlayerIdentity(PlayerInterface pi);
}
