package polimi.ingsw.Model.Enumeration;

public enum CardCommonType implements CardType {
    CommonGroup0, CommonVertix, CommonGroup1, CommonGroup2, CommonVertical0, CommonGroup3,
    CommonDiagonal0, CommonHorizontal0, CommonVertical1, CommonHorizontal1, CommonX, CommonDiagonal1;

    //for testing purposes
    public int getParam(CardCommonType card) {
        return switch (card) {
            case CommonGroup0, CommonVertical0, CommonDiagonal0, CommonHorizontal0 -> 0;
            case CommonGroup1, CommonVertical1, CommonHorizontal1, CommonDiagonal1 -> 1;
            case CommonGroup2 -> 2;
            case CommonGroup3 -> 3;
            //they will never be used, so just put a random number
            case CommonVertix, CommonX -> 4;
        };
    }

}
