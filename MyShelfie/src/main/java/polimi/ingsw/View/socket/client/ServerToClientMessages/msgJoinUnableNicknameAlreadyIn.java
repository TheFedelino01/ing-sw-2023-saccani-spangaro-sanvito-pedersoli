package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Player;

import java.rmi.RemoteException;

public class msgJoinUnableNicknameAlreadyIn extends SocketServerGenericMessage{


    private Player wantedToJoin;

    public msgJoinUnableNicknameAlreadyIn(Player wantedToJoin) {
        this.wantedToJoin = wantedToJoin;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.joinUnableNicknameAlreadyIn(wantedToJoin);
    }
}
