package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public CommonCard getCommonCard(CardCommonType type){
        return switch (type) {
            case CommonGroup0 -> new CommonGroupsCard(CardCommonType.CommonGroup0, 0);
            case CommonVertix -> new CommonVertixesCard(CardCommonType.CommonVertix);
            case CommonGroup1 -> new CommonGroupsCard(CardCommonType.CommonGroup1, 1);
            case CommonGroup2 -> new CommonGroupsCard(CardCommonType.CommonGroup2, 2);
            case CommonVertical0 -> new CommonVerticalCard(CardCommonType.CommonVertical0, 0);
            case CommonGroup3 -> new CommonGroupsCard(CardCommonType.CommonGroup3, 3);
            case CommonDiagonal0 -> new CommonDiagonalCard(CardCommonType.CommonDiagonal0, 0);
            case CommonHorizontal0 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal0, 0);
            case CommonVertical1 -> new CommonVerticalCard(CardCommonType.CommonVertical1, 1);
            case CommonHorizontal1 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal1, 1);
            case CommonX -> new CommonXCard(CardCommonType.CommonX);
            case CommonDiagonal1 -> new CommonDiagonalCard(CardCommonType.CommonDiagonal1, 1);
        };

    }
}
