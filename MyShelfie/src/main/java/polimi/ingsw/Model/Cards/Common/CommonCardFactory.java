package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public CardCommon getCommonCard(CardCommonType type){
        return switch (type) {
            case CommonGroup0 -> new CommonGroups(CardCommonType.CommonGroup0, 0);
            case CommonVertix -> new CommonVertixes(CardCommonType.CommonVertix);
            case CommonGroup1 -> new CommonGroups(CardCommonType.CommonGroup1, 1);
            case CommonGroup2 -> new CommonGroups(CardCommonType.CommonGroup2, 2);
            case CommonVertical0 -> new CommonVertical(CardCommonType.CommonVertical0, 0);
            case CommonGroup3 -> new CommonGroups(CardCommonType.CommonGroup3, 3);
            case CommonDiagonal0 -> new CommonDiagonal(CardCommonType.CommonDiagonal0, 0);
            case CommonHorizontal0 -> new CommonHorizontal(CardCommonType.CommonHorizontal0, 0);
            case CommonVertical1 -> new CommonVertical(CardCommonType.CommonVertical1, 1);
            case CommonHorizontal1 -> new CommonHorizontal(CardCommonType.CommonHorizontal1, 1);
            case CommonX -> new CommonX(CardCommonType.CommonX);
            case CommonDiagonal1 -> new CommonDiagonal(CardCommonType.CommonDiagonal1, 1);
        };

    }
}
