package polimi.ingsw.View.socket.client.ServerToClientMessages;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;

import java.rmi.RemoteException;

public class msgCommonCardsExtracted extends SocketServerGenericMessage{
    private CommonCard card;

    public msgCommonCardsExtracted(CommonCard card) {
        this.card = card;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.commonCardsExtracted(card);
    }
}
