package polimi.ingsw.View.socket.client;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientGenericMessage implements Serializable {

    protected String nick;
    protected boolean isMessageForMainController;

    public abstract GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException;
    public abstract void execute(GameControllerInterface gameController) throws RemoteException;

    public boolean isMessageForMainController() {return isMessageForMainController;}
    public void setMessageForMainController(boolean messageForMainController) {isMessageForMainController = messageForMainController;}
}
