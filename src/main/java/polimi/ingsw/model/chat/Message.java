package polimi.ingsw.model.chat;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.interfaces.PlayerIC;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

public class Message implements Serializable {
    private String text;
    private PlayerIC sender;
    private LocalTime time;

    /**
     * Constructor
     * @param text
     * @param sender
     */
    public Message(String text, PlayerIC sender) {
        this.time = java.time.LocalTime.now();
        this.text = text;
        this.sender = sender;
    }

    /**
     * Constructor
     */
    public Message() {
        this.time = null;
        this.text = null;
        this.sender = null;
    }

    /**
     * Returns the message in string format
     * @param i
     * @param len
     * @param isPrivate
     * @return
     */
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

    /**
     *
     * @return the message's text content
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text in the message to the param
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return the message's sender
     */
    public PlayerIC getSender() {
        return sender;
    }

    /**
     * Sets the message's sender
     * @param sender sender
     */
    public void setSender(Player sender) {
        this.sender = sender;
    }

    /**
     *
     * @return the message's time of sending
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the message time to the parameter
     * @param time param
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     *
     * @return * (everyone is a receiver)
     */
    public String whoIsReceiver() {
        return "*";
    }
}
