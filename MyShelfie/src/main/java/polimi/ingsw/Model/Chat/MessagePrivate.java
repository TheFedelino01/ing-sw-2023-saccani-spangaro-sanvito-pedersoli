package polimi.ingsw.Model.Chat;

import polimi.ingsw.Model.Player;

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
