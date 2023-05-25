package polimi.ingsw.model.chat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Player;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MessagePrivateTest {
    @Test
    @DisplayName("Test whoIsReceiver() method1")
    void testWhoIsReceiver1() {
        String expectedReceiver = "JohnDoe";
        String messageText = "Hello, how are you?";
        Player sender = new Player("Alice");

        MessagePrivate message = new MessagePrivate(messageText, sender, expectedReceiver);

        String actualReceiver = message.whoIsReceiver();

        assertEquals(expectedReceiver, actualReceiver, "Receiver does not match");
    }   @Test
    @DisplayName("Test getMessageText()")
    void testGetMessageText() {
        String expectedText = "Hello, how are you?";
        Player sender = new Player("Alice");

        Message message = new Message(expectedText, sender);

        String actualText = message.getText();

        assertEquals(expectedText, actualText, "Message text does not match");
    }

    @Test
    @DisplayName("Test getSender()")
    void testGetSender() {
        String messageText = "Hello, how are you?";
        Player expectedSender = new Player("Alice");

        Message message = new Message(messageText, expectedSender);

        Player actualSender = message.getSender();

        assertEquals(expectedSender, actualSender, "Sender does not match");
    }

    @Test
    @DisplayName("Test getTime()")
    void testGetTime() {
        String messageText = "Hello, how are you?";
        Player sender = new Player("Alice");

        Message message = new Message(messageText, sender);

        LocalTime currentTime = LocalTime.now();
        LocalTime actualTime = message.getTime();

        // Comparing the hour, minute, and second of the current time and actual time.
        assertEquals(currentTime.getHour(), actualTime.getHour(), "Hour does not match");
        assertEquals(currentTime.getMinute(), actualTime.getMinute(), "Minute does not match");
        assertEquals(currentTime.getSecond(), actualTime.getSecond(), "Second does not match");
    }

    @Test
    @DisplayName("Test whoIsReceiver()")
    void testWhoIsReceiver() {
        String messageText = "Hello, how are you?";
        Player sender = new Player("Alice");

        Message message = new Message(messageText, sender);

        String expectedReceiver = "*";
        String actualReceiver = message.whoIsReceiver();

        assertEquals(expectedReceiver, actualReceiver, "Receiver does not match");
    }

    @Test
    @DisplayName("Test empty constructor")
    void testEmptyConstructor() {
        Message message = new Message();

        assertNull(message.getText(), "Text should be null");
        assertNull(message.getSender(), "Sender should be null");
        assertNull(message.getTime(), "Time should be null");
    }

}