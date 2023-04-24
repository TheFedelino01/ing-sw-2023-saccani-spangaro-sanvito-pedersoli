package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Enumeration.CardCommonType;

public class CommonCardFactory {
    public CommonCard getCommonCard(CardCommonType type) {
        return switch (type) {
            //Six groups of 2 tiles of the same type (tile type can change between groups)
            //case CommonGroup0 -> new CommonGroupsCard(CardCommonType.CommonGroup0, 0);
            case CommonGroup0 -> new CommonGroupsCard(CardCommonType.CommonGroup0, 0, 2, 6);
            //Same tiles in the shelf vertexes
            case CommonVertix -> new CommonVertixesCard(CardCommonType.CommonVertix);

            //Four groups of 4 tiles of the same type (tile type can change between groups)
            //case CommonGroup1 -> new CommonGroupsCard(CardCommonType.CommonGroup1, 1);
            case CommonGroup1 -> new CommonGroupsCard(CardCommonType.CommonGroup1, 0, 4, 4);

            //Two groups of 2x2 tiles of the same type (tile type CANNOT change between groups)
            case CommonGroup2 -> new CommonGroupsCard(CardCommonType.CommonGroup2, 2);

            //Three columns made with max three different tile types (combinations may vary, but AT MAX three different types)
            case CommonVertical0 -> new CommonVerticalCard(CardCommonType.CommonVertical0, 0, 3, 3);

            //Eight tiles of the same type
            case CommonGroup3 -> new CommonGroupsCard(CardCommonType.CommonGroup3, 3);

            //Five tiles of the same type, creating a diagonal line across the shelf
            case CommonDiagonal0 -> new CommonDiagonalCard(CardCommonType.CommonDiagonal0, 0);

            //Like "commonVertical0", but with 4 rows instead of 3 columns
            case CommonHorizontal0 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal0, 0);

            //Two columns made with all different tile types
            case CommonVertical1 -> new CommonVerticalCard(CardCommonType.CommonVertical1, 0, 6, 2);

            //Two rows made with all different tile types
            case CommonHorizontal1 -> new CommonHorizontalCard(CardCommonType.CommonHorizontal1, 1);

            //An "X" pattern is formed on the board, with tiles of the same type
            case CommonX -> new CommonXCard(CardCommonType.CommonX);

            //Five columns in ascending or descending order: from 5 to 1 tile or from 1 to 5 tiles, types may vary
            case CommonDiagonal1 -> new CommonDiagonalCard(CardCommonType.CommonDiagonal1, 1);
        };
    }
}
