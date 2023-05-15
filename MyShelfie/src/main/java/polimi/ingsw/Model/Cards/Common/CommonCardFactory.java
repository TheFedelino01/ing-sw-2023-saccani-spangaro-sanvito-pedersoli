package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public static CommonCard getCommonCard(CardCommonType type) {
        return switch (type) {
            //Six groups of 2 tiles of the same type (tile type can change between groups)
            case CommonSixGroups -> new CommonSixGroups(CardCommonType.CommonSixGroups);

            //Same tiles in the shelf vertexes
            case CommonVertex -> new CommonVertixesCard(CardCommonType.CommonVertex);

            //Four groups of 4 tiles of the same type (tile type can change between groups)
            case CommonFourGroups -> new CommonFourGroups(CardCommonType.CommonFourGroups);

            //Two groups of 2x2 BOX tiles of the same type per Group (tiles can change between groups)
            case CommonSquares -> new CommonSquares(CardCommonType.CommonSquares);

            //Three columns made with max three different tile types (combinations may vary, but AT MAX three different types)
            case CommonVertical0 -> new CommonVerticalCard(CardCommonType.CommonVertical0, 0);

            //Eight tiles of the same type
            case CommonEight -> new CommonEight(CardCommonType.CommonEight);

            //Five tiles of the same type, creating a diagonal line across the shelf
            case CommonSameDiagonal -> new CommonSameDiagonal(CardCommonType.CommonSameDiagonal);

            //Like "commonVertical0", but with 4 rows instead of 3 columns
            case CommonHorizontal0 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal0, 0);

            //Two columns made with all different tile types
            case CommonVertical1 -> new CommonVerticalCard(CardCommonType.CommonVertical1, 1);

            //Two rows made with all different tile types
            case CommonHorizontal1 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal1, 1);

            //An "X" pattern is formed on the board, with tiles of the same type
            case CommonX -> new CommonXCard(CardCommonType.CommonX);

            //Five columns in ascending or descending order: from 5 to 1 tile or from 1 to 5 tiles, types may vary
            case CommonStair -> new CommonStair(CardCommonType.CommonStair);
        };
    }
}
