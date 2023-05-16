package polimi.ingsw.model.chat;

import polimi.ingsw.model.Player;

public class MessagePrivate extends Message{

    private String receiverPrivate;
    public MessagePrivate(String text, Player sender, String receiver){
        super(text,sender);
        this.receiverPrivate=receiver;
    }

    @Override
    public String whoIsReceiver(){
        return receiverPrivate;
    }
}
