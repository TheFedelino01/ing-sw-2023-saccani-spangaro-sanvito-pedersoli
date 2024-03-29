package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.Shelf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static polimi.ingsw.model.enumeration.CardCommonType.CommonVertex;
import static polimi.ingsw.utility.CommonlyUsedMethods.setShelf;

public class CommonVertexesTest {

    private CommonVertexesCard card;

    @BeforeEach
    void setUp() {
        card = (CommonVertexesCard)CommonCardFactory.getCommonCard(CommonVertex);
    }


    @Test
    @DisplayName("Test vertexes #0")
    public void testVertexes0() {
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
    @DisplayName("Test vertexes #1")
    public void testVertexes1() {
        String[][] matrix = {
                {"C","","","","C"},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"C","","","","C"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test vertexes #2")
    public void testVertexes2() {
        String[][] matrix = {
                {"A","","","","C"},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"C","","","","C"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Test vertexes #3")
    public void testVertexes3() {
        String[][] matrix = {
                {"C","C","","","A"},
                {"C","C","","","A"},
                {"C","C","","","A"},
                {"C","C","","","A"},
                {"C","C","","","A"},
                {"C","C","A","A","A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #4")
    public void testVertexes4() {
        String[][] matrix = {
                {"A","A","A","T","A"},
                {"A","","","","T"},
                {"A","","","","T"},
                {"A","","","","T"},
                {"A","","","","T"},
                {"A","A","A","T","A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #5")
    public void testVertexes5() {
        String[][] matrix = {
                {"A","","","","T"},
                {"A","","","","A"},
                {"A","","","","A"},
                {"A","","","","A"},
                {"A","","","","A"},
                {"A","","","","A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #6")
    public void testVertexes6() {
        String[][] matrix = {
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #7")
    public void testVertexes7() {
        String[][] matrix = {
                {"T","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #8")
    public void testVertexes8() {
        String[][] matrix = {
                {"T","A","A","A","T"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","A"},
                {"A","A","A","A","T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #9")
    public void testVertexes9() {
        String[][] matrix = {
                {"T","A","A","A","T"},
                {"A","A","A","A","A"},
                {"A","A","T","T","A"},
                {"A","T","T","A","A"},
                {"A","A","T","A","A"},
                {"T","A","A","A","T"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Test vertexes #10")
    public void testVertexes10() {
        String[][] matrix = {
                {"C","A","A","A","C"},
                {"A","A","A","A","A"},
                {"A","A","T","T","A"},
                {"A","T","T","A","C"},
                {"C","A","T","C","A"},
                {"A","C","A","A","T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #11")
    public void testVertexes11() {
        String[][] matrix = {
                {"C","A","A","A","C"},
                {"A","A","A","A","A"},
                {"A","A","T","T","A"},
                {"A","T","T","A","C"},
                {"C","A","T","C","A"},
                {"C","C","A","A","T"}
        };
        Shelf test = setShelf(matrix);
        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #12")
    public void testVertexes12() {
        String[][] matrix = {
                {"C","A","A","A","C"},
                {"A","A","A","A","A"},
                {"A","A","T","T","A"},
                {"A","T","T","A","C"},
                {"C","A","T","C","A"},
                {"C","C","A","A","C"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #13")
    public void testVertexes13() {
        String[][] matrix = {
                {"C","A","A","A","C"},
                {"A","A","A","A","A"},
                {"A","A","","","A"},
                {"A","T","","","C"},
                {"C","A","T","C","A"},
                {"C","C","A","A","C"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Test vertexes #14")
    public void testVertexes14() {
        String[][] matrix = {
                {"C","","","","C"},
                {"A","","A","A","A"},
                {"A","A","","","A"},
                {"A","T","","","C"},
                {"C","A","T","C","A"},
                {"C","C","A","A","C"}
        };
        Shelf test = setShelf(matrix);
        assertTrue(card.verify(test));
    }
}

