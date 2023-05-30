package polimi.ingsw.model.interfaces;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.TileGrabbedNotCorrectException;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public interface PlaygroundIC {
    public TileIC getTile_IC(int r, int c);

    public int getNumOfTileinTheBag();

    public boolean checkBeforeGrab(int r, int c, Direction direction, int num);

    public boolean allTileHaveAllFreeSide();

    public String toString();


}
