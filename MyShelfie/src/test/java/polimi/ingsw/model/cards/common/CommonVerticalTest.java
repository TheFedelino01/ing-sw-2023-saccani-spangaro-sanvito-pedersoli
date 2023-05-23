package polimi.ingsw.model.cards.common;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.CardCommonType;

import static polimi.ingsw.model.cards.common.ShelfConverter.setShelf;

public class CommonVerticalTest {
    private CommonVerticalCard card;

    @BeforeEach
    void setUp() {
        card = (CommonVerticalCard) CommonCardFactory.getCommonCard(CardCommonType.CommonVertical0);
    }

    @Test
    @DisplayName("Three column of 6 tiles #1")
    public void testCommon1() {
        String[][] matrix = {
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"}
        };

        Shelf test = setShelf(matrix);
        card.toString(0);
        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #2")
    public void testCommon2() {
        String[][] matrix = {
                {"F", "F", "F", "F", "F"},
                {"T", "T", "T", "T", "T"},
                {"C", "C", "C", "C", "C"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"},
                {"C", "", "T", "", "F"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #3")
    public void testCommon3() {
        String[][] matrix = {
                {"F", "P", "F", "F", "F"},
                {"T", "P", "T", "T", "T"},
                {"C", "P", "C", "C", "C"},
                {"C", "P", "T", "C", "F"},
                {"C", "P", "T", "C", "F"},
                {"C", "P", "T", "C", "F"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #4")
    public void testCommon4() {
        String[][] matrix = {
                {"", "P", "", "", ""},
                {"", "P", "", "", ""},
                {"", "P", "", "", ""},
                {"", "P", "", "", ""},
                {"", "P", "", "", ""},
                {"", "P", "", "", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #5")
    public void testCommon5() {
        String[][] matrix = {
                {"", "P", "T", "", ""},
                {"", "P", "T", "", ""},
                {"", "P", "T", "", ""},
                {"", "P", "T", "", ""},
                {"", "P", "T", "", ""},
                {"", "P", "T", "", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #6")
    public void testCommon6() {
        String[][] matrix = {
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #7")
    public void testCommon7() {
        String[][] matrix = {
                {"", "P", "T", "F", ""},
                {"", "C", "F", "P", ""},
                {"", "C", "F", "P", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #8")
    public void testCommon8() {
        String[][] matrix = {
                {"", "P", "T", "F", ""},
                {"", "C", "F", "P", ""},
                {"", "C", "F", "P", ""},
                {"", "A", "P", "A", ""},
                {"", "P", "T", "F", ""},
                {"", "P", "T", "F", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #9")
    public void testCommon9() {
        String[][] matrix = {
                {"", "P", "T", "F", ""},
                {"", "C", "F", "P", ""},
                {"", "C", "F", "P", ""},
                {"", "A", "P", "A", ""},
                {"", "P", "T", "F", ""},
                {"", "A", "A", "A", ""}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #10")
    public void testCommon10() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"A", "A", "A", "A", "A"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Three column of 6 tiles #11")
    public void testCommon11(){
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"T", "T", "T", "T", "T"},
                {"", "", "", "", ""},
                {"A", "A", "A", "A", "A"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Three column of 6 tiles #12")
    public void testCommon12(){
        String[][] matrix = {
                {"", "", "", "", ""},
                {"F", "F", "F", "F", ""},
                {"", "", "", "", ""},
                {"T", "T", "T", "T", "T"},
                {"", "", "", "", ""},
                {"A", "A", "A", "A", "A"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Three column of 6 tiles #13")
    public void testCommon13(){
        String[][] matrix = {
                {"", "", "", "", ""},
                {"F", "F", "F", "F", "F"},
                {"", "", "", "", ""},
                {"T", "T", "T", "T", "T"},
                {"", "", "", "", ""},
                {"A", "A", "A", "A", "A"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Three column of 6 tiles #14")
    public void testCommon14(){
        String[][] matrix = {
                {"F", "", "F", "", "F"},
                {"F", "F", "F", "F", "F"},
                {"F", "", "F", "", "F"},
                {"T", "T", "T", "T", "T"},
                {"F", "", "F", "", "F"},
                {"A", "A", "A", "A", "A"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }

}
