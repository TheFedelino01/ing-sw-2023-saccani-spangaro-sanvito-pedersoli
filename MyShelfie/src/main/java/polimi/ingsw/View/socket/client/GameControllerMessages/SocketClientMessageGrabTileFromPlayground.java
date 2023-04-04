package polimi.ingsw.View.socket.client.GameControllerMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;
import polimi.ingsw.View.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageGrabTileFromPlayground extends SocketClientGenericMessage {
    private int x,y,num;
    private Direction direction;

    public SocketClientMessageGrabTileFromPlayground(String nick, int x, int y, Direction direction,int num) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.direction = direction;
        this.nick = nick;
        this.isMessageForMainController=false;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        gameController.grabTileFromPlayground(nick,x,y,direction,num);
    }

}
