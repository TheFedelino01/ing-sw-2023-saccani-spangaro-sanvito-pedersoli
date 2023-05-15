package polimi.ingsw.Model.Chat;

import com.googlecode.lanterna.terminal.ansi.TelnetTerminalServer;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

public class Message implements Serializable {
    private String text;
    private Player sender;
    private LocalTime time;

    public Message(String text, Player sender) {
        this.time = java.time.LocalTime.now();
        this.text = text;
        this.sender = sender;
    }

    public Message() {
        this.time = null;
        this.text = null;
        this.sender = null;
    }

    public String toString(int i, int len, boolean isPrivate) {
        String padding = " ".repeat(Math.max(0, (len - text.length())));
        String priv = "[Private] ";
        if (!isPrivate)
            priv = "";
        if (sender.getNickname().length() > 4)
            return String.valueOf(ansi().cursor(DefaultValue.row_chat + i + 1, DefaultValue.col_chat).a(priv + "[").a(this.time.getHour()).a(":").a(this.time.getMinute())
                    .a(":").a(this.time.getSecond()).a("] ")
                    .a(this.getSender().getNickname().substring(0, 4)).a(".").a(": ").a(this.text).a(padding));
        else
            return String.valueOf(ansi().cursor(DefaultValue.row_chat + i + 1, DefaultValue.col_chat).a(priv + "[").a(this.time.getHour()).a(":").a(this.time.getMinute())
                    .a(":").a(this.time.getSecond()).a("] ")
                    .a(this.getSender().getNickname()).a(": ").a(this.text).a(padding));
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

    public String whoIsReceiver() {
        return "*";
    }
}
