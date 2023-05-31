package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.chat.Message;

import java.util.List;

public interface ChatIC {
    List<Message> getMsgs();

    String getLast();

    Message getLastMessage();

    String toString();

    String toString(String privateMsgByNickname);
}
