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

    public String chatToString(){
        String result = null;
        for (Message msg : msgs) {
            try {
                result.concat(msg.getTime().toString() + " " + msg.getSender().getNickname() + ": " +
                        msg.getText() + "\n");
            } catch (NullPointerException e) {
                System.err.println("Null concat value " + e.getMessage());
            }
        }
        return result;
    }
}
