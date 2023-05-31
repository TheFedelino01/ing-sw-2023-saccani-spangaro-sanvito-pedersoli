package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.Player;

import java.rmi.RemoteException;

public class msgJoinUnableNicknameAlreadyIn extends SocketServerGenericMessage {


    private Player wantedToJoin;

    public msgJoinUnableNicknameAlreadyIn(Player wantedToJoin) {
        this.wantedToJoin = wantedToJoin;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.joinUnableNicknameAlreadyIn(wantedToJoin);
    }
}
