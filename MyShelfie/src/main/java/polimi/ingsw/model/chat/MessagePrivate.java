package polimi.ingsw.model.chat;

import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;

public class MessagePrivate extends Message{

    private String receiverPrivate;
    public MessagePrivate(String text, PlayerIC sender, String receiver){
        super(text,sender);
        this.receiverPrivate=receiver;
    }

    @Override
    public String whoIsReceiver(){
        return receiverPrivate;
    }
}
