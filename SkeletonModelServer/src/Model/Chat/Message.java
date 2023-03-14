package Model.Chat;

import Model.Player;

import java.sql.Time;

public class Message {
    private String text;
    private Player sender;
    private Time time;

    public Message(String text, Player sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
