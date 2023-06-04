package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.chat.Message;

import java.util.List;

/**
 * This interface is implemented by Chat class
 */
public interface ChatIC {

    /**
     * This method adds a message to the chat
     * @return the message added {@link Message}
     */
    List<Message> getMsgs();

    /**
     * This method returns the last message
     * @return the last message
     */
    String getLast();

    /**
     * This method returns the last message
     * @return the last message {@link Message}
     */
    Message getLastMessage();

    /**
     * This method returns a string representation
     * @return the string representation
     */
    String toString();

    /**
     * This method returns a string representation of the private message
     * @param privateMsgByNickname nickname of the player
     * @return the string representation
     */
    String toString(String privateMsgByNickname);
}
