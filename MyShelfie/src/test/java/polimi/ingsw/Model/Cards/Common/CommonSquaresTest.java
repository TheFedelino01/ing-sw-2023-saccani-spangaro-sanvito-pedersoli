package polimi.ingsw.Model.Cards.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Shelf;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CommonSquaresTest extends ShelfConverter{

    private CommonSquares card;
    @BeforeEach
    void setUp() {
        card=(CommonSquares)CommonCardFactory.getCommonCard(CardCommonType.CommonSquares);
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #1")
    public void testFourGroups1() {
        String[][] matrix = {
                {"C","C","","",""},
                {"C","C","","",""},
                {"","","","",""},
                {"","","","B","B"},
                {"","","","B","B"},
                {"","","","",""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #2")
    public void testFourGroups2() {
        String[][] matrix = {
                {"C","C","C","",""},
                {"C","C","C","",""},
                {"","","","",""},
                {"","","B","B","B"},
                {"","","B","B","B"},
                {"","","","",""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #3")
    public void testFourGroups3() {
        String[][] matrix = {
                {"C","C","C","",""},
                {"C","C","C","",""},
                {"C","C","C","",""},
                {"","","","B","B"},
                {"","","","B","B"},
                {"","","","",""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #4")
    public void testFourGroups4() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","","A"},
                {"A","A","A","A",""},
                {"A","A","C","B","B"},
                {"","A","","B","B"},
                {"A","","","",""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #5")
    public void testFourGroups5() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"A","","","",""},
                {"A","","","","B"},
                {"A","","","","C"},
                {"A","","","","B"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #6")
    public void testFourGroups6() {
        String[][] matrix = {
                {"","","T","",""},
                {"","","T","C","C"},
                {"A","","T","C","C"},
                {"A","","T","C","B"},
                {"A","A","T","C","C"},
                {"A","A","T","C","B"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #7")
    public void testFourGroups7() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"A","","C","C","C"},
                {"A","","T","C","C"},
                {"A","A","T","C","C"},
                {"A","A","T","C","B"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #8")
    public void testFourGroups8() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"","","C","B",""},
                {"A","A","C","B","B"},
                {"A","C","C","C","C"},
                {"A","A","C","B","B"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #9")
    public void testFourGroups9() {
        String[][] matrix = {
                {"","","C","C",""},
                {"","","C","C",""},
                {"","","C","B",""},
                {"A","A","C","B","B"},
                {"A","A","C","C","C"},
                {"A","A","C","B","B"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #10")
    public void testFourGroups10() {
        String[][] matrix = {
                {"A","A","","A","A"},
                {"","A","A","A","A"},
                {"C","C","A","",""},
                {"C","C","","A","A"},
                {"T","A","A","C","C"},
                {"A","C","T","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #11")
    public void testFourGroups11() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"A","","T","C","C"},
                {"A","","T","C","C"},
                {"A","A","T","T","T"},
                {"A","A","T","T","B"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #12")
    public void testFourGroups12() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"A","","C","C","C"},
                {"A","","C","C","C"},
                {"A","A","C","T","T"},
                {"A","A","T","T","B"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #13")
    public void testFourGroups13() {
        String[][] matrix = {
                {"T","T","","",""},
                {"T","T","","",""},
                {"A","","C","C","C"},
                {"A","","C","C","C"},
                {"A","C","C","C","C"},
                {"A","C","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }


    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #14")
    public void testFourGroups14() {
        String[][] matrix = {
                {"T","T","","",""},
                {"T","T","","",""},
                {"A","","C","C","C"},
                {"A","","C","C","C"},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #15")
    public void testFourGroups15() {
        String[][] matrix = {
                {"T","T","","",""},
                {"T","T","","",""},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #16")
    public void testFourGroups16() {
        String[][] matrix = {
                {"T","T","","",""},
                {"T","T","","",""},
                {"A","C","C","C","C"},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"},
                {"A","A","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #17")
    public void testFourGroups17() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"T","T","","",""},
                {"T","T","","",""},
                {"A","T","A","C","C"},
                {"A","T","A","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #18")
    public void testFourGroups18() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"T","T","","",""},
                {"T","T","","",""},
                {"C","C","C","C","C"},
                {"C","C","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #19")
    public void testFourGroups19() {
        String[][] matrix = {
                {"C","","","T","T"},
                {"","C","","T","T"},
                {"","","C","","T"},
                {"A","A","A","C","T"},
                {"A","A","A","C","T"},
                {"C","C","C","T","T"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("2 Groups of 2x2 box tiles with same Tiles inside group #20")
    public void testFourGroups20() {
        String[][] matrix = {
                {"","","","T","T"},
                {"","","","T","T"},
                {"","","","C","T"},
                {"","","","C","T"},
                {"A","A","","A","T"},
                {"A","A","A","A","T"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
}
