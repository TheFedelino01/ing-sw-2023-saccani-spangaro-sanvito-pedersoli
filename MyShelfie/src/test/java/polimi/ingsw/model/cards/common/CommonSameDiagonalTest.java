package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.Shelf;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSameDiagonalTest extends ShelfConverter{
    private CommonSameDiagonal card;
    @BeforeEach
    void setUp() {
        card=(CommonSameDiagonal)CommonCardFactory.getCommonCard(CardCommonType.CommonSameDiagonal);
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #1")
    public void testVertexes1() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        Shelf test = this.setShelf(matrix);
        card.toString(0);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #2")
    public void testVertexes2() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","","C"},
                {"","","","C",""},
                {"","","C","",""},
                {"","C","","",""},
                {"C","","","",""}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #3")
    public void testVertexes3() {
        String[][] matrix = {
                {"","","","",""},
                {"C","C","C","C","C"},
                {"","C","","C",""},
                {"","","C","",""},
                {"","C","","C",""},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #4")
    public void testVertexes4() {
        String[][] matrix = {
                {"","","","",""},
                {"C","C","C","T","T"},
                {"","C","","C","T"},
                {"","","C","",""},
                {"","C","","C",""},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #5")
    public void testVertexes5() {
        String[][] matrix = {
                {"C","","","","C"},
                {"","C","","C",""},
                {"","","C","T",""},
                {"","C","C","C","C"},
                {"C","T","T","C","T"},
                {"A","A","C","C","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #6")
    public void testVertexes6() {
        String[][] matrix = {
                {"","","","",""},
                {"C","C","C","T",""},
                {"","C","","",""},
                {"","","C","",""},
                {"","C","","C",""},
                {"C","","","","T"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #7")
    public void testVertexes7() {
        String[][] matrix = {
                {"","","","C","C"},
                {"","","C","T","C"},
                {"","C","T","",""},
                {"C","T","C","",""},
                {"T","","","C",""},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #8")
    public void testVertexes8() {
        String[][] matrix = {
                {"","","","C","C"},
                {"C","","C","T","C"},
                {"A","C","","",""},
                {"C","T","C","",""},
                {"T","","","C",""},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #9")
    public void testVertexes9() {
        String[][] matrix = {
                {"","","","","T"},
                {"C","","","","T"},
                {"","C","","T","T"},
                {"","","T","","T"},
                {"","T","","C","T"},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #10")
    public void testVertexes10() {
        String[][] matrix = {
                {"","","","","T"},
                {"A","","","","T"},
                {"","A","","","T"},
                {"","A","A","","T"},
                {"A","T","","A","T"},
                {"C","A","","","A"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #11")
    public void testVertexes11() {
        String[][] matrix = {
                {"","","","","T"},
                {"C","","","","T"},
                {"","C","","T","T"},
                {"","","C","","T"},
                {"","T","","C","T"},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #12")
    public void testVertexes12() {
        String[][] matrix = {
                {"","","","","T"},
                {"T","T","T","T","T"},
                {"","C","","T","T"},
                {"","","T","","T"},
                {"","T","","C","T"},
                {"C","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #13")
    public void testVertexes13() {
        String[][] matrix = {
                {"","","","",""},
                {"C","","","","T"},
                {"","C","","T",""},
                {"","","A","",""},
                {"","T","","C",""},
                {"T","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #14")
    public void testVertexes14() {
        String[][] matrix = {
                {"","","","",""},
                {"C","","","","T"},
                {"","C","","A",""},
                {"","","A","",""},
                {"","A","","C",""},
                {"A","","","","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #15")
    public void testVertexes15() {
        String[][] matrix = {
                {"","","","",""},
                {"C","","","","A"},
                {"","C","","A",""},
                {"","","A","",""},
                {"","A","C","C","C"},
                {"A","","C","C","C"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #16")
    public void testVertexes16() {
        String[][] matrix = {
                {"","","","",""},
                {"T","","","","A"},
                {"","T","","A",""},
                {"","","A","",""},
                {"","T","C","T","C"},
                {"A","","C","C","T"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #17")
    public void testVertexes17() {
        String[][] matrix = {
                {"T","","","","T"},
                {"","T","","T",""},
                {"","","T","",""},
                {"","T","","T",""},
                {"T","","","","A"},
                {"","","","",""}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #18")
    public void testVertexes18() {
        String[][] matrix = {
                {"T","","","","A"},
                {"A","T","","T",""},
                {"","A","T","",""},
                {"","T","A","T",""},
                {"T","","","A","A"},
                {"","","","","A"}
        };
        Shelf test = this.setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test Same Diagonal of 5 Tiles #19")
    public void testVertexes19() {
        String[][] matrix = {
                {"T","T","T","T","T"},
                {"T","T","T","T","T"},
                {"T","T","A","T","T"},
                {"T","T","A","T","T"},
                {"T","T","T","T","T"},
                {"T","T","T","T","T"}
        };
        Shelf test = this.setShelf(matrix);
        assertFalse(card.verify(test));
    }
}
