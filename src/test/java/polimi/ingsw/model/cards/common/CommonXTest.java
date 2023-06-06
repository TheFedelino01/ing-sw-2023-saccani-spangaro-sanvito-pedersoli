package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.Shelf;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static polimi.ingsw.utility.CommonlyUsedMethods.setShelf;

public class CommonXTest {

    private CommonXCard card;
    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        card = (CommonXCard) CommonCardFactory.getCommonCard(CardCommonType.CommonX);
    }

    @Test
    @DisplayName("X #1")
    public void testX1(){
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        card.toString(0, true);
        card.toString(0, false);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #3")
    public void testX3(){
        String[][] matrix = {
                {"C","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #4")
    public void testX4(){
        String[][] matrix = {
                {"C","","","",""},
                {"","C","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #5")
    public void testX5(){
        String[][] matrix = {
                {"C","","","",""},
                {"","C","","",""},
                {"","","C","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #6")
    public void testX6(){
        String[][] matrix = {
                {"C","","","",""},
                {"","C","","",""},
                {"C","","C","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #7")
    public void testX7(){
        String[][] matrix = {
                {"C","","C","",""},
                {"","C","","",""},
                {"C","","C","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("X #8")
    public void testX8(){
        String[][] matrix = {
                {"C","","C","",""},
                {"","C","","",""},
                {"C","","C","",""},
                {"C","C","C","C","C"},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("X #9")
    public void testX9(){
        String[][] matrix = {
                {"C","","T","",""},
                {"","C","","",""},
                {"C","","C","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #10")
    public void testX10(){
        String[][] matrix = {
                {"C","","T","",""},
                {"","P","","P",""},
                {"C","","P","",""},
                {"","P","","P",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("X #11")
    public void testX11(){
        String[][] matrix = {
                {"C","","T","",""},
                {"","P","","P",""},
                {"C","","P","",""},
                {"","C","","P",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("X #12")
    public void testX12(){
        String[][] matrix = {
                {"C","C","T","T","P"},
                {"P","P","","P","P"},
                {"C","","P","C","P"},
                {"C","C","","P","P"},
                {"T","A","T","C","P"},
                {"P","A","T","C","P"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

}
