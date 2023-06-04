package polimi.ingsw.model.cards.common;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import static polimi.ingsw.utility.CommonlyUsedMethods.setShelf;

public class CommonDiagonalTest {
    private CommonStair card;

    @BeforeEach
    void setUp() {
        card = (CommonStair) CommonCardFactory.getCommonCard(CardCommonType.CommonStair);
    }


    @Test
    @DisplayName("Stair #1")
    public void testStairShelf1() {
        String[][] matrix = {
                {"C", "", "", "", ""},
                {"P", "C", "", "", ""},
                {"F", "T", "C", "", ""},
                {"F", "F", "A", "C", ""},
                {"P", "A", "F", "T", "C"},
                {"A", "P", "F", "C", "T"}
        };

        Shelf test = setShelf(matrix);
        card.toString(0, true);
        card.toString(0, false);
        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #2")
    public void testStairShelf2() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }


    @Test
    @DisplayName("Stair #3")
    public void testStairShelf3() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"C", "", "", "", ""},
                {"F", "C", "", "", ""},
                {"F", "F", "C", "", ""},
                {"P", "A", "F", "C", ""},
                {"A", "P", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #4")
    public void testStairShelf4() {
        String[][] matrix = {
                {"", "", "", "", "C"},
                {"", "", "", "C", "P"},
                {"", "", "C", "F", "F"},
                {"", "C", "F", "F", "C"},
                {"C", "A", "F", "P", "F"},
                {"A", "P", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #5")
    public void testStairShelf5() {
        String[][] matrix = {
                {"", "", "", "", "C"},
                {"", "", "", "C", "P"},
                {"", "", "C", "F", "F"},
                {"", "C", "F", "F", "C"},
                {"C", "A", "F", "P", "F"},
                {"A", "P", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #6")
    public void testStairShelf6() {
        String[][] matrix = {
                {"F", "", "", "", ""},
                {"P", "F", "", "", ""},
                {"P", "F", "F", "", ""},
                {"A", "C", "F", "F", ""},
                {"C", "A", "F", "P", "F"},
                {"A", "P", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }


    @Test
    @DisplayName("Stair #7")
    public void testStairShelf7() {
        String[][] matrix = {
                {"F", "", "", "", ""},
                {"P", "F", "", "", ""},
                {"P", "F", "F", "", ""},
                {"A", "F", "F", "F", ""},
                {"C", "F", "F", "P", "F"},
                {"A", "F", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #8")
    public void testStairShelf8() {
        String[][] matrix = {
                {"F", "", "", "", ""},
                {"P", "F", "", "", ""},
                {"P", "P", "F", "", ""},
                {"A", "C", "P", "F", ""},
                {"C", "A", "F", "P", "C"},
                {"A", "P", "F", "C", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #9")
    public void testStairShelf9() {
        String[][] matrix = {
                {"C", "", "", "", ""},
                {"C", "C", "", "", ""},
                {"C", "C", "C", "", ""},
                {"C", "C", "C", "C", ""},
                {"C", "C", "C", "C", "C"},
                {"C", "C", "C", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #10")
    public void testStairShelf10() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"C", "", "", "", ""},
                {"C", "C", "", "", ""},
                {"C", "C", "C", "", ""},
                {"C", "C", "C", "C", ""},
                {"C", "C", "C", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #11")
    public void testStairShelf11() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"C", "", "", "", ""},
                {"C", "C", "", "", ""},
                {"C", "C", "C", "", ""},
                {"C", "C", "T", "C", ""},
                {"C", "C", "C", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #12")
    public void testStairShelf12() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"T", "T", "T", "T", "T"},
                {"", "T", "T", "T", "T"},
                {"", "", "T", "T", "T"},
                {"", "", "", "T", "T"},
                {"", "", "", "", "T"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #13")
    public void testStairShelf13() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"", "T", "T", "T", "T"},
                {"", "", "T", "T", "T"},
                {"", "", "", "T", "T"},
                {"", "", "", "", "T"},
                {"", "", "", "", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #14")
    public void testStairShelf14() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"", "T", "T", "T", "T"},
                {"", "", "T", "T", "T"},
                {"", "", "", "T", "T"},
                {"", "", "", "", "T"},
                {"", "", "", "", "T"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Stair #15")
    public void testStairShelf15() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"P", "T", "T", "T", "T"},
                {"", "P", "T", "T", "T"},
                {"", "", "P", "T", "T"},
                {"", "", "", "P", "T"},
                {"", "", "", "", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Stair #16")
    public void testStairShelf16() {
        String[][] matrix = {
                {"T", "T", "T", "T", "T"},
                {"P", "T", "T", "T", "T"},
                {"F", "P", "T", "T", "T"},
                {"", "F", "P", "T", "T"},
                {"", "", "F", "P", "T"},
                {"", "", "", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Stair #17")
    public void testStairShelf17() {
        String[][] matrix = {
                {"T", "T", "T", "", ""},
                {"P", "T", "T", "T", ""},
                {"F", "P", "T", "T", "T"},
                {"", "F", "P", "T", "T"},
                {"", "", "F", "P", "T"},
                {"", "", "", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Stair #18")
    public void testStairShelf18() {
        String[][] matrix = {
                {"T", "T", "T", "", ""},
                {"P", "T", "T", "T", ""},
                {"F", "P", "T", "T", "T"},
                {"F", "F", "P", "T", "T"},
                {"F", "F", "F", "P", "T"},
                {"F", "F", "F", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Stair #19")
    public void testStairShelf19() {
        String[][] matrix = {
                {"T", "T", "", "", ""},
                {"P", "T", "", "", ""},
                {"F", "P", "T", "", ""},
                {"F", "F", "P", "T", ""},
                {"F", "F", "F", "P", "T"},
                {"F", "F", "F", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Stair #20")
    public void testStairShelf20() {
        String[][] matrix = {
                {"T", "T", "", "", "P"},
                {"P", "T", "", "", ""},
                {"F", "P", "T", "", ""},
                {"F", "F", "P", "T", ""},
                {"F", "F", "F", "P", "T"},
                {"F", "F", "F", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }
}
