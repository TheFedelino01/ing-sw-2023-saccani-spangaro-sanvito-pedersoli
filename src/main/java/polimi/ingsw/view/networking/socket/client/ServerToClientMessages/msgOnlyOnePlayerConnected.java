package polimi.ingsw.view.networking.socket.client.ServerToClientMessages;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

public class msgOnlyOnePlayerConnected extends SocketServerGenericMessage {
    private GameModelImmutable model;
    private int secondsToWaintUntilGameEnded;

    public msgOnlyOnePlayerConnected(GameModelImmutable gamemodel,int secondsToWaintUntilGameEnded) {
        this.model = gamemodel;
        this.secondsToWaintUntilGameEnded=secondsToWaintUntilGameEnded;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.onlyOnePlayerConnected(model,secondsToWaintUntilGameEnded);
    }
}
