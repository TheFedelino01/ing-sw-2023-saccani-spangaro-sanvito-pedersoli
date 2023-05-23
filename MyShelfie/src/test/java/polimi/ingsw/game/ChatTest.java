package polimi.ingsw.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.chat.Chat;
import polimi.ingsw.model.chat.Message;

import java.util.List;

public class ChatTest {

    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player("p1");
        player2 = new Player("p2");
    }

    @Test
    @DisplayName("Test for the chat")
    void chatTest() {
        Chat chat = new Chat();
        Message message = new Message("Test", player1);
        chat.addMsg(message);
        assert (chat.getLastMessage().getText().equals("Test"));
        assert (chat.getLastMessage().getSender().getNickname().equals("p1"));
        assert (chat.getLastMessage().getTime() != null);
        chat.addMsg(player2, "Test2");
        assert (chat.getLastMessage().getText().equals("Test2"));
        assert (chat.getLastMessage().getSender().getNickname().equals("p2"));
        assert (chat.getLastMessage().getTime() != null);

        chat.addMsg(player2, "Test3");
        chat.addMsg(player2, "Test4");
        chat.addMsg(player2, "Test5");
        chat.addMsg(player2, "Test6");

        assert(chat.getLastMessage().getText().equals("Test6"));
        assert(chat.getLast().length() > 0);

        assert(chat.toString().length()>0);
        assert (chat.toString(player1.getNickname()).length() > 0);

    }
}
