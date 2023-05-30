package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.chat.Message;

import java.util.List;

public interface ChatIC {
    public List<Message> getMsgs();

    public String getLast();

    public Message getLastMessage();

    public String toString();

    public String toString(String privateMsgByNickname);
}
