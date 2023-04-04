package polimi.ingsw.View.socket.client;

import polimi.ingsw.Controller.GameController;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public abstract class SocketClientMessage{

    private boolean isMessageForMainController;

    public abstract GameControllerInterface execute(MainControllerInterface mainController) throws RemoteException;
    public abstract void execute(GameControllerInterface mainController) throws RemoteException;

    public boolean isMessageForMainController() {
        return isMessageForMainController;
    }

    public void setMessageForMainController(boolean messageForMainController) {
        isMessageForMainController = messageForMainController;
    }
}
