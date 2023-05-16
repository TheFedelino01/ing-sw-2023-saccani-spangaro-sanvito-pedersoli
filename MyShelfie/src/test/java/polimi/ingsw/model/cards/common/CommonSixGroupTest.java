package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import static junit.framework.Assert.assertFalse;

public class CommonSixGroupTest extends ShelfConverter {
    private CommonSixGroups card;
    @BeforeEach
    void setUp() {
        card=(CommonSixGroups) CommonCardFactory.getCommonCard(CardCommonType.CommonSixGroups);
    }

    @Test
    @DisplayName("Test #1")
    public void testVertexes1() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","","T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

}
