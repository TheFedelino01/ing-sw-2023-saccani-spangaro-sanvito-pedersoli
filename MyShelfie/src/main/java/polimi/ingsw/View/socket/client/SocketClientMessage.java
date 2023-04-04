package polimi.ingsw.View.socket.client;

import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public interface SocketClientMessage{
    void execute(MainControllerInterface mainController) throws RemoteException;
}
