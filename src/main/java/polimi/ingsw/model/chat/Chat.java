package polimi.ingsw.model.chat;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.ChatIC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class Chat implements Serializable, ChatIC {
    private List<Message> msgs;

    /**
     * Constructor
     */
    public Chat() {
        msgs = new ArrayList<Message>();
    }

    /**
     * Constructor
     * @param msgs
     */
    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     *
     * @return the list of messages
     */
    public List<Message> getMsgs() {
        return msgs;
    }

    /**
     * Adds a message
     * @param m message param
     */
    public void addMsg(Message m) {
        if (msgs.size() > DefaultValue.max_messagesShown)
            msgs.remove(0);
        msgs.add(m);
    }

    /**
     * Adds a message
     * @param sender message param
     * @param text message param
     */
    public void addMsg(Player sender, String text) {
        Message temp = new Message(text, sender);
        if (msgs.size() > DefaultValue.max_messagesShown)
            msgs.remove(0);
        msgs.add(temp);
    }

    /**
     *
     * @return the last message in string form
     */
    public String getLast() {
        return msgs.get(msgs.size() - 1).toString();
    }

    /**
     * @return the last message in message form
     */
    public Message getLastMessage() {
        return msgs.get(msgs.size() - 1);
    }

    /**
     * Sets the chat messages
     *
     * @param msgs messages
     */
    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     * @return the chat in string form
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int len = this.getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> b.length() > a.length() ? b : a)
                .toString().length();
        for (Message msg : msgs) {
            ret.append(msg.toString(i, len, false));
            i++;
        }
        return ret.toString();
    }

    /**
     * @param privateMsgByNickname
     * @return the private chat in string form
     */
    public String toString(String privateMsgByNickname) {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int len = this.getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> b.length() > a.length() ? b : a)
                .toString().length();

        for (Message msg : msgs) {
            if (!msg.whoIsReceiver().equals("*") && ((msg.getSender().getNickname().equals(privateMsgByNickname) || msg.whoIsReceiver().equals(privateMsgByNickname)))) {
                ret.append(msg.toString(i, len, true));
            } else {
                ret.append(msg.toString(i, len, false));
            }

            i++;
        }
        return ret.toString();
    }
}
