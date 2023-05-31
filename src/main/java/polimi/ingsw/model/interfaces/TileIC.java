package polimi.ingsw.model.interfaces;

import polimi.ingsw.model.enumeration.TileType;

public interface TileIC {
    public TileType getType();

    public boolean hasFreeSide();
    public boolean isSameType(TileType type);
    public String toString();

    public String getBackground();
}
