package polimi.ingsw.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.*;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Exceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
    Player p;
    @BeforeEach
    void setUp() {
        //Set up the player point
        p = new Player("Test");


    }

    @Test
    @DisplayName("Test player points")
    void testPlayerPoints() {
        //Test the player points
        Point point = new Point(0, CardCommonType.CommonX);
        p.addPoint(point);
        assertEquals(0, p.getTotalPoints());
    }
}
