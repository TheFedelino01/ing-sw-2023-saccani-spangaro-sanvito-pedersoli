package polimi.ingsw.Model.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonHorizontalCard extends CommonCard {
    private static int param;

    public CommonHorizontalCard(CardCommonType type, int param) {
        super(type);
        CommonHorizontalCard.param = param;
    }

    @Override
    public boolean verify(Shelf toCheck){
        param = super.getCommonType().compareTo(CardCommonType.CommonHorizontal0)> 0 ? 1 : 0;
        int sum=0;
        switch (param) {
            case (0) -> { //eighth common goal in the rulebook
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    int ok = check(toCheck, i);
                    if (ok <= 3)
                        sum++;
                    if (sum == 4) {
                        return true;
                    }
                }
                return false;
            }
            case (1) -> { //tenth common goal in the rulebook
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    int ok = check(toCheck, i);
                    if (ok == 5)
                        sum++;
                    if (sum == 2) {
                        return true;
                    }
                }
                return false;
            }
            default -> {
                System.out.println("Default error");
                return false;
            }
        }
    }

    private int check(Shelf toCheck, int i){
        Map<TileType, Integer> rowCheck = new HashMap<>();
        int ok = 0;
        int count = 0;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (!toCheck.get(i, j).isSameType(TileType.NOT_USED))
                rowCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
            else
                count++;
        }
        for (TileType t : TileType.values()) {
            ok = ok + Optional.
                    ofNullable(rowCheck.get(t)).
                    orElse(0);
        }
        if(count>0)
            ok=4;
        return ok;
    }
}
