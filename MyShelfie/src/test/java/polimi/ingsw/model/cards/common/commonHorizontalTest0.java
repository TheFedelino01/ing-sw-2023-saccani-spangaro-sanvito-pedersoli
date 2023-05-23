package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class commonHorizontalTest0 extends ShelfConverter {
    private CommonHorizontalCard card;

    @BeforeEach
    void setUp() {
        card = (CommonHorizontalCard) CommonCardFactory.getCommonCard(CardCommonType.CommonHorizontal0);
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #1")
    void testHorizontal1() {
        String[][] matrix = {
                {"", "", "", "", ""},
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
    @DisplayName("Test Horizontal of 5 Tiles #2")
    void testHorizontal2() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
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
    @DisplayName("Test Horizontal of 5 Tiles #3")
    void testHorizontal3() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #4")
    void testHorizontal4() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #5")
    void testHorizontal5() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @DisplayName("Test Horizontal of 5 Tiles #6")
    void testHorizontal6() {
        String[][] matrix = {
                {"C", "C", "C", "C", "P"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @DisplayName("Test Horizontal of 5 Tiles #7")
    void testHorizontal7() {
        String[][] matrix = {
                {"C", "C", "C", "A", "P"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @DisplayName("Test Horizontal of 5 Tiles #8")
    void testHorizontal8() {
        String[][] matrix = {
                {"C", "C", "T", "A", "P"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #9")
    void testHorizontal9() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""},
                {"C", "C", "C", "C", "C"},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #10")
    void testHorizontal10() {
        String[][] matrix = {
                {"C", "C", "C", "A", "P"},
                {"A", "C", "C", "C", "C"},
                {"C", "P", "C", "C", "C"},
                {"C", "A", "C", "C", "C"},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #11")
    void testHorizontal11() {
        String[][] matrix = {
                {"C", "C", "C", "A", "P"},
                {"C", "P", "C", "C", "C"},
                {"", "", "", "", ""},
                {"A", "C", "C", "C", "C"},
                {"C", "A", "C", "C", "C"},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #12")
    void testHorizontal12() {
        String[][] matrix = {
                {"C", "C", "C", "A", "P"},
                {"C", "P", "C", "C", "C"},
                {"", "", "", "", ""},
                {"A", "C", "C", "C", "C"},
                {"C", "A", "C", "C", "C"},
                {"A", "T", "T", "T", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Horizontal of 5 Tiles #13")
    void testHorizontal13() {
        String[][] matrix = {
                {"C", "C", "C", "A", "P"},
                {"C", "P", "C", "C", "C"},
                {"C", "C", "C", "C", "C"},
                {"A", "C", "C", "C", "C"},
                {"C", "A", "C", "C", "C"},
                {"A", "T", "T", "T", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
}
