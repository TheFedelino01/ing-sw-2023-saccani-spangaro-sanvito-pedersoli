package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public CardCommon getCommonCard(CardCommonType type){
        switch (type){
            case COMMON1:
                return new CommonDiagonal(CardCommonType.COMMON1, 0);
            case COMMON2:
                return new CommonDiagonal(CardCommonType.COMMON2, 1);
            case COMMON3:
                return new CommonGroups(CardCommonType.COMMON3, 0);
            case COMMON4:
                return new CommonGroups(CardCommonType.COMMON4, 1);
            case COMMON5:
                return new CommonGroups(CardCommonType.COMMON5, 2);
            case COMMON6:
                return new CommonGroups(CardCommonType.COMMON6, 3);
            case COMMON7:
                return new CommonHorizontal(CardCommonType.COMMON7, 0);
            case COMMON8:
                return new CommonHorizontal(CardCommonType.COMMON8, 1);
            case COMMON9:
                return new CommonVertical(CardCommonType.COMMON9, 0);
            case COMMON10:
                return new CommonVertical(CardCommonType.COMMON10, 1);
            case COMMON11:
                return new CommonVertixes(CardCommonType.COMMON11);
            case COMMON12:
                return new CommonX(CardCommonType.COMMON12);
        }
        return null;

    }
}
