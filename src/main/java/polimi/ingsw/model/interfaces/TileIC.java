package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.TileType;

public interface TileIC {
    TileType getType();

    boolean hasFreeSide();
    boolean isSameType(TileType type);
    String toString();

    String getBackground();
}
