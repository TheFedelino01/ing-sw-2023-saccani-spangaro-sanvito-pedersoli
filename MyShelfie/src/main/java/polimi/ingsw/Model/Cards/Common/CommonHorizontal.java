package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonHorizontal extends CardCommon{
    private static int param;

    public CommonHorizontal(CardCommonType type, int param) {
        super(type);
        this.param = param;
    }

    @Override
    public boolean verify(Shelf toCheck){
        int sum=0;
        switch(param){
            case(0): //eighth common goal in the rulebook
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    Map<TileType, Integer> rowCheck = new HashMap<>();
                    int ok = 0; //check single row
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (!toCheck.get(i,j).isSameType(TileType.NOT_USED))
                            rowCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + Optional.
                                ofNullable(rowCheck.get(t)).
                                orElse(0);
                    }
                    if (ok <= 3)
                        sum = sum + 1;
                    if (sum == 4) {
                        return true;
                    }
                }
                return false;
            case(1): //tenth common goal in the rulebook
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    Map<TileType, Integer> rowCheck = new HashMap<>();
                    int ok = 0; //check single row
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (!toCheck.get(i,j).isSameType(TileType.NOT_USED))
                            rowCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + Optional.
                                ofNullable(rowCheck.get(t)).
                                orElse(0);
                    }
                    if (ok == 5)
                        sum = sum + 1;
                    if (sum == 2) {
                        return true;
                    }
                }
                return false;
            default:
                System.out.println("Default error");
                return false;
        }
    }
}
