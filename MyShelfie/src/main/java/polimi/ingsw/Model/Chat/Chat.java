package polimi.ingsw.Model.Chat;

import polimi.ingsw.Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chat {
    private List<Message> msgs;

    public Chat(){
        msgs = new ArrayList<Message>();
    }
    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void addMsg(Message m){
        msgs.add(m);
    }
    public void addMsg(Player sender, String text){
        Message temp = new Message(text, sender);
        msgs.add(temp);
    }

    public String getLast(){
        return msgs.get(msgs.size()-1).toString();
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    public String chatToString() {
        return msgs.stream()
                .map(Message::toString)
                .collect(Collectors.joining());
    }
}
