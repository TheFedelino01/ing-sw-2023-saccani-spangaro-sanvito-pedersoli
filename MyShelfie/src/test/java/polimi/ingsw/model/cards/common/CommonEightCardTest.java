package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CommonEightCardTest extends ShelfConverter {

    List<CommonCard> model = new ArrayList<>();
    private CommonEight card;

    @BeforeEach
    void setUp() {
        card = (CommonEight) CommonCardFactory.getCommonCard(CardCommonType.CommonEight);
    }

    @Test
    @DisplayName("Common Eight Cart Test #1")
    public void test1() {
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
    @DisplayName("Common Eight Cart Test #2")
    public void test2() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"", "", "", "A", "A"},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #3")
    public void test3() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"", "", "P", "A", "A"},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Common Eight Cart Test #4")
    public void test4() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Common Eight Cart Test #5")
    public void test5() {
        String[][] matrix = {
                {"A", "P", "C", "C", "A"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Common Eight Cart Test #6")
    public void test6() {
        String[][] matrix = {
                {"A", "A", "B", "C", "C"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #7")
    public void test7() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #8")
    public void test8() {
        String[][] matrix = {
                {"B", "B", "B", "B", "B"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #9")
    public void test9() {
        String[][] matrix = {
                {"P", "P", "P", "P", "P"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #10")
    public void test10() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #11")
    public void test11() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Common Eight Cart Test #12")
    public void test12() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"B", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Common Eight Cart Test #13")
    public void test13() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #14")
    public void test14() {
        String[][] matrix = {
                {"B", "B", "B", "B", "B"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #15")
    public void test15() {
        String[][] matrix = {
                {"C", "C", "C", "C", "C"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #16")
    public void test16() {
        String[][] matrix = {
                {"P", "P", "P", "P", "P"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #17")
    public void test17() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"", "", "P", "A", "A"},
                {"", "", "A", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Common Eight Cart Test #18")
    public void test18() {
        String[][] matrix = {
                {"F", "F", "F", "F", "F"},
                {"F", "", "P", "A", "A"},
                {"F", "", "A", "", ""},
                {"F", "", "", "", ""},
                {"", "", "", "", "A"},
                {"", "", "A", "", "A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

}
