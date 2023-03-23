package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonVerticalCard extends CommonCard {
    private static int param;

    public CommonVerticalCard(CardCommonType type, int param) {
        super(type);
        CommonVerticalCard.param = param;
    }

    @Override
    public boolean verify(Shelf toCheck){
        int sum=0;
        switch (param) {
            case (0) -> { //fifth common goal in the rulebook
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    Map<TileType, Integer> colCheck = new HashMap<>();
                    int ok = 0; //check single column
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED))
                            colCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + Optional.
                                ofNullable(colCheck.get(t)).
                                orElse(0);
                    }
                    if (ok <= 3)
                        sum = sum + 1;
                    if (sum == 3) {
                        return true;
                    }
                }
                return false;
            }
            case (1) -> { //ninth common goal in the rulebook
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    Map<TileType, Integer> colCheck = new HashMap<>();
                    int ok = 0; //check single column
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (!toCheck.get(i, j).isSameType(TileType.NOT_USED))
                            colCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + Optional.
                                ofNullable(colCheck.get(t)).
                                orElse(0);
                    }
                    if (ok == 6)
                        sum = sum + 1;
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
}
