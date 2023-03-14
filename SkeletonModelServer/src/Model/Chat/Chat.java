package Model.Chat;

import java.util.List;

public class Chat {
    private List<Message> msgs;

    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    public String chatToString() {
        String result = "";
        for (Message msg : msgs) {
            result = result.concat(msg.getTime().toString())
                    .concat(" ")
                    .concat(msg.getSender().getNickname())
                    .concat(": ")
                    .concat(msg.getText())
                    .concat("\n");
        }
        return result;
    }
}
