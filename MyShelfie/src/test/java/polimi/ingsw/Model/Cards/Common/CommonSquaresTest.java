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
                {"","A","A","A",""},
                {"","A","A","B","B"},
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
                {"","","","",""},
                {"","","","",""},
                {"A","","","",""},
                {"A","","","","B"},
                {"A","A","C","C","C"},
                {"A","A","C","C","B"}
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
                {"A","","C","C",""},
                {"A","","C","C","B"},
                {"A","A","A","C","C"},
                {"A","A","C","C","B"}
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
                {"T","A","A","C","A"},
                {"A","C","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
}
