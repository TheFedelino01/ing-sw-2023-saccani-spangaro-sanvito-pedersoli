package Model.Chat;

import Model.Player;

import java.time.LocalTime;
import java.time.ZoneId;

public class Message {
    private String text;
    private Player sender;
    private LocalTime time;

    public Message(String text, Player sender) {
        this.time = java.time.LocalTime.now();
        this.text = text;
        this.sender = sender;
    }

    @Override
    public String toString(){
        return this.time.toString().concat(" ")
                .concat(this.getSender().getNickname())
                .concat(": ")
                .concat(this.text)
                .concat("\n");
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
