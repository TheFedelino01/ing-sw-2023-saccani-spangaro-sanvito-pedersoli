package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
public class CommonSixGroupTest extends ShelfConverter {
    private CommonSixGroups card;

    @BeforeEach
    void setUp() {
        card = (CommonSixGroups) CommonCardFactory.getCommonCard(CardCommonType.CommonSixGroups);
    }

    @Test
    @DisplayName("Test CommonSixGroup #1")
    public void testVertexes1() {
        String[][] matrix = {
                {"T", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        card.toString(0);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #2")
    public void testVertexes2() {
        String[][] matrix = {
                {"", "", "", "", "T"},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #3")
    public void testVertexes3() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"T", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #4")
    public void testVertexes4() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #5")
    public void testFourTile() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "T", "T"},
                {"", "", "", "T", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #6")
    public void testSixSingleTile() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "T", "T", "", ""},
                {"", "", "", "T", ""},
                {"", "", "T", "", ""},
                {"", "", "", "", ""},
                {"", "T", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #7")
    public void testNearSixGroup() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "T", "T", "T"},
                {"", "", "T", "T", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #8")
    public void testSixGroup1() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"T", "", "T", "", "T"},
                {"T", "", "T", "", "T"},
                {"", "", "", "", ""},
                {"T", "", "T", "", "T"},
                {"T", "", "T", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #9")
    public void testSixGroup2() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "T", "", "T"},
                {"T", "T", "T", "", "T"},
                {"", "", "", "", ""},
                {"T", "", "T", "", "T"},
                {"T", "", "T", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #10")
    public void testSixGroup3() {
        String[][] matrix = {
                {"T", "T", "", "", "T"},
                {"", "", "", "", "T"},
                {"", "T", "T", "", ""},
                {"", "", "", "", ""},
                {"T", "", "T", "", "T"},
                {"T", "", "T", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #11  ")
    public void testSixGroupWithDifferentType1() {
        String[][] matrix = {
                {"T", "T", "", "", "B"},
                {"", "", "", "", "B"},
                {"", "T", "T", "", ""},
                {"", "", "", "", ""},
                {"B", "", "T", "", "T"},
                {"B", "", "T", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }


    @Test
    @DisplayName("Test CommonSixGroup #12  ")
    public void testSixGroupWithDifferentType2() {
        String[][] matrix = {
                {"T", "P", "", "", "B"},
                {"", "", "", "", "B"},
                {"", "T", "T", "", ""},
                {"", "", "", "", ""},
                {"B", "", "T", "", "T"},
                {"B", "", "T", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #13  ")
    public void testSixGroupWithDifferentType3() {
        String[][] matrix = {
                {"", "P", "", "", "B"},
                {"", "P", "", "", "B"},
                {"", "", "T", "T", ""},
                {"", "", "", "", ""},
                {"B", "", "T", "", "T"},
                {"B", "", "F", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test CommonSixGroup #14  ")
    public void testSixGroupWithDifferentType4() {
        String[][] matrix = {
                {"", "P", "", "", "B"},
                {"", "P", "", "", "B"},
                {"", "", "T", "T", ""},
                {"", "", "", "", ""},
                {"B", "", "F", "", "T"},
                {"B", "", "F", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #15  ")
    public void testSixGroupWithDifferentType5() {
        String[][] matrix = {
                {"T", "", "", "", "F"},
                {"T", "", "", "", "F"},
                {"", "F", "", "P", ""},
                {"", "F", "", "P", ""},
                {"B", "", "", "", "T"},
                {"B", "", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test CommonSixGroup #16  ")
    public void testSixGroupWithDifferentType6() {
        String[][] matrix = {
                {"T", "", "", "", "F"},
                {"T", "", "", "", "F"},
                {"", "F", "", "P", ""},
                {"", "F", "", "P", ""},
                {"B", "", "", "", "T"},
                {"B", "", "", "F", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test CommonSixGroup #17  ")
    public void testSixGroupWithDifferentType7() {
        String[][] matrix = {
                {"", "", "", "", "F"},
                {"", "", "", "", "F"},
                {"", "F", "", "P", ""},
                {"", "F", "", "P", ""},
                {"B", "", "", "", "T"},
                {"B", "", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test CommonSixGroup #18  ")
    public void testSixGroupWithDifferentType8() {
        String[][] matrix = {
                {"", "P", "", "", "F"},
                {"", "P", "", "", "F"},
                {"", "F", "", "P", ""},
                {"", "F", "", "P", ""},
                {"", "T", "", "", "T"},
                {"", "T", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test CommonSixGroup #19  ")
    public void testSixGroupWithDifferentType9() {
        String[][] matrix = {
                {"", "P", "", "", "F"},
                {"", "P", "", "", "F"},
                {"A", "", "", "P", ""},
                {"A", "", "", "P", ""},
                {"", "T", "", "", "T"},
                {"", "T", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #20  ")
    public void testSixGroupWithDifferentType10() {
        String[][] matrix = {
                {"", "P", "", "F", "F"},
                {"", "P", "", "", ""},
                {"A", "", "P", "P", ""},
                {"A", "", "", "", ""},
                {"", "", "P", "", "T"},
                {"T", "T", "", "", "T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test CommonSixGroup #21  ")
    public void testSixGroupWithDifferentType21() {
        String[][] matrix = {
                {"T", "T", "", "", ""},
                {"T", "", "", "", ""},
                {"", "F", "", "P", ""},
                {"", "F", "", "P", ""},
                {"B", "", "", "", "T"},
                {"B", "", "", "F", "T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
}

