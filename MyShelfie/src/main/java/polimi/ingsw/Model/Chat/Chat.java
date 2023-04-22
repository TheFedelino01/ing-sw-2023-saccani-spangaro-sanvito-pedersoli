package polimi.ingsw.Model.Chat;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Chat implements Serializable {
    private List<Message> msgs;

    public Chat() {
        msgs = new ArrayList<Message>();
    }

    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void addMsg(Message m) {
        if (msgs.size() > DefaultValue.max_messagesShown)
            msgs.remove(0);
        msgs.add(m);
    }

    public void addMsg(Player sender, String text) {
        Message temp = new Message(text, sender);
        if (msgs.size() > DefaultValue.max_messagesShown)
            msgs.remove(0);
        msgs.add(temp);
    }

    public String getLast() {
        return msgs.get(msgs.size() - 1).toString();
    }

    public Message getLastMessage() {
        return msgs.get(msgs.size() - 1);
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int len = this.getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> b.length() > a.length() ? b : a)
                .toString().length();
        for (Message msg : msgs) {
            ret.append(msg.toString(i, len));
            i++;
        }
        return ret.toString();
    }
}
