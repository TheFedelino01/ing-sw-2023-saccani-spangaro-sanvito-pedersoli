package polimi.ingsw.model.chat;

import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;

public class MessagePrivate extends Message{

    private String receiverPrivate;

    /**
     * Constructor
     * @param text
     * @param sender
     * @param receiver
     */
    public MessagePrivate(String text, PlayerIC sender, String receiver){
        super(text,sender);
        this.receiverPrivate=receiver;
    }

    /**
     *
     * @return the designed receiver for the private message
     */
    @Override
    public String whoIsReceiver(){
        return receiverPrivate;
    }
}
