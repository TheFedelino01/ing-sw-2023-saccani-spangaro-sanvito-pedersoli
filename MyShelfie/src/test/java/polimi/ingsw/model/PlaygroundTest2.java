package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.exceptions.TileGrabbedNotCorrectException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaygroundTest2 {
    Playground p;

    @BeforeEach
    void setUp() {
        p = new Playground(4);
        p.initialisePlayground();
    }

    @Test
    @DisplayName("Grab a tile")
    void testGrab() throws TileGrabbedNotCorrectException {
        assertThrows(TileGrabbedNotCorrectException.class, () -> {
            p.grabTile(0, 0, Direction.UP, 1);
        });
    }

    @Test
    @DisplayName("All tile have free side")
    void testFreeSide() {
        Playground p = new Playground(2);
        p.initialisePlayground();
        assertFalse(
                p.allTileHaveAllFreeSide()
        );
        p.setPlayground();
        p.checkBeforeGrab(0, 0, Direction.UP, 1);
        assertFalse(
                p.allTileHaveAllFreeSide()
        );
    }
}
