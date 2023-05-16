package polimi.ingsw.view.networking.socket.client;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.view.networking.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientGenericMessage implements Serializable {

    protected String nick;
    protected boolean isMessageForMainController;

    public abstract GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException;

    public abstract void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException;

    public boolean isMessageForMainController() {
        return isMessageForMainController;
    }

    public void setMessageForMainController(boolean messageForMainController) {
        isMessageForMainController = messageForMainController;
    }

    public String getNick() {
        return nick;
    }
}
