package polimi.ingsw.model.cards.common;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static polimi.ingsw.model.cards.common.ShelfConverter.setShelf;

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
    //Non dovrebbe passarlo?
    @Disabled
    @Test
    @DisplayName("Stair #7")
    public void testStairShelf7() {
        String[][] matrix = {
                {"F", "P", "", "", ""},
                {"P", "F", "P", "", ""},
                {"P", "F", "F", "A", ""},
                {"A", "C", "F", "F", "A"},
                {"C", "A", "F", "P", "F"},
                {"A", "P", "F", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        Assert.assertTrue(card.verify(test));
    }


}
