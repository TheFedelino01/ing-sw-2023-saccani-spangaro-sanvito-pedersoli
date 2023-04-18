package polimi.ingsw.Model;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Exceptions.TileGrabbedNotCorrectException;

import java.util.List;

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
            p.grabTile(0,0, Direction.UP, 1);
        });
    }
}
