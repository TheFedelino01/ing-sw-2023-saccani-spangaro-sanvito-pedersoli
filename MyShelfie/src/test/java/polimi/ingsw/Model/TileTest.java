package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.TileType;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
