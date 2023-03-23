package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public CardCommon getCommonCard(CardCommonType type){
        return switch (type) {
            case COMMON1 -> new CommonDiagonal(CardCommonType.COMMON1, 0);
            case COMMON2 -> new CommonDiagonal(CardCommonType.COMMON2, 1);
            case COMMON3 -> new CommonGroups(CardCommonType.COMMON3, 0);
            case COMMON4 -> new CommonGroups(CardCommonType.COMMON4, 1);
            case COMMON5 -> new CommonGroups(CardCommonType.COMMON5, 2);
            case COMMON6 -> new CommonGroups(CardCommonType.COMMON6, 3);
            case COMMON7 -> new CommonHorizontal(CardCommonType.COMMON7, 0);
            case COMMON8 -> new CommonHorizontal(CardCommonType.COMMON8, 1);
            case COMMON9 -> new CommonVertical(CardCommonType.COMMON9, 0);
            case COMMON10 -> new CommonVertical(CardCommonType.COMMON10, 1);
            case COMMON11 -> new CommonVertixes(CardCommonType.COMMON11);
            case COMMON12 -> new CommonX(CardCommonType.COMMON12);
        };

    }
}
