package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @Test
    @DisplayName("Check tile type")
    void testType() {
        boolean found = false;
        for (int i = 0; i < TileType.values().length && !found; i++) {
            if (TileType.values()[i].equals(tile.getType()))
                found = true;
        }
        assertTrue(found, "Tile type corrent");
    }

    @Test
    void directionTest() {
        Direction dUp = Direction.UP;
        Direction dDown = Direction.DOWN;
        Direction dLeft = Direction.LEFT;
        Direction dRight = Direction.RIGHT;
        assertEquals(Direction.getDirection("up"), dUp);
        assertEquals(Direction.getDirection("down"), dDown);
        assertEquals(Direction.getDirection("left"), dLeft);
        assertEquals(Direction.getDirection("right"), dRight);
        assertNull(Direction.getDirection("f"));

    }

}
