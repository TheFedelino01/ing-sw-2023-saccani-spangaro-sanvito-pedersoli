package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.CardCommonType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
    Player p1;
    Player p2;

    @BeforeEach
    void setUp() {
        //Set up the player point
        p1 = new Player("Test");
        p2 = new Player("Test2");
    }

    @Test
    @DisplayName("Test player points")
    void testPlayerPoints() {
        //Test the player points
        Point point = new Point(0, CardCommonType.CommonX);
        p1.addPoint(point);
        assertEquals(0, p1.getTotalPoints());
        p2.addPoint(point);
        assertEquals(0, p2.getTotalPoints());

    }

    @Test
    @DisplayName("Check who wins?")
    void testWhoWins() {
        //Check who wins
        Point point = new Point(0, CardCommonType.CommonX);
        p1.addPoint(point);
        p2.addPoint(point);
        assertEquals(0, p1.getTotalPoints());
        assertEquals(0, p2.getTotalPoints());
        int x = p1.getTotalPoints();
        int y = p2.getTotalPoints();
        if (x > y) {
            System.out.println("Player 1 wins!");
        } else if (x < y) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Draw!");
        }
    }
}
