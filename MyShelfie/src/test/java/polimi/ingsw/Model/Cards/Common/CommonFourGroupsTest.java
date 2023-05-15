package polimi.ingsw.Model.Cards.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class CommonFourGroupsTest {

    private CommonFourGroups card;
    @BeforeEach
    void setUp() {
        card=(CommonFourGroups)CommonCardFactory.getCommonCard(CardCommonType.CommonFourGroups);
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #1")
    public void testFourGroups1() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"C","","T","",""},
                {"C","","T","",""},
                {"C","","T","",""},
                {"C","","T","",""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #2")
    public void testFourGroups2() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"C","P","T","",""},
                {"C","P","T","",""},
                {"C","P","T","",""},
                {"C","P","T","",""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #3")
    public void testFourGroups3() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"C","P","T","F",""},
                {"C","P","T","F",""},
                {"C","P","T","F",""},
                {"C","P","T","F",""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #4")
    public void testFourGroups4() {
        String[][] matrix = {
                {"A","","","",""},
                {"A","","","","P"},
                {"A","","","","P"},
                {"A","","","","P"},
                {"T","T","T","T","P"},
                {"F","F","F","F","P"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #5")
    public void testFourGroups5() {
        String[][] matrix = {
                {"P","","","F","P"},
                {"P","","","F","P"},
                {"P","","","F","P"},
                {"P","P","F","F","P"},
                {"A","A","A","A","A"},
                {"P","P","P","A","P"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #6")
    public void testFourGroups6() {
        String[][] matrix = {
                {"T","","","",""},
                {"T","A","","","P"},
                {"T","A","","A","P"},
                {"T","A","A","","P"},
                {"T","A","","","P"},
                {"A","C","C","C","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #7")
    public void testFourGroups7() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","","A"},
                {"T","","","","C"},
                {"T","","","","T"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #8")
    public void testFourGroups8() {
        String[][] matrix = {
                {"A","","T","T",""},
                {"A","T","T","T","T"},
                {"A","","T","",""},
                {"A","C","C","C","C"},
                {"A","","T","P","P"},
                {"A","","T","P","P"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #9")
    public void testFourGroups9() {
        String[][] matrix = {
                {"A","A","A","A","A"},
                {"A","P","P","P","A"},
                {"A","P","T","C","A"},
                {"A","P","T","C","A"},
                {"A","P","T","C","A"},
                {"A","P","T","C","A"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #10")
    public void testFourGroups10() {
        String[][] matrix = {
                {"","","","",""},
                {"","","","",""},
                {"T","","","",""},
                {"C","","T","T",""},
                {"A","A","T","",""},
                {"C","T","T","",""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }
    @Test
    @DisplayName("Four Groups of 4 Tiles #11")
    public void testFourGroups11() {
        String[][] matrix = {
                {"","A","","",""},
                {"C","A","A","A",""},
                {"C","A","F","A","C"},
                {"C","A","A","A","C"},
                {"C","","F","","C"},
                {"A","A","A","A","C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    private Shelf setShelf(String[][] shelf){
        int rows= DefaultValue.NumOfRowsShelf;
        int cols = DefaultValue.NumOfColumnsShelf;
        Shelf ris = new Shelf();
        TileType tmp=null;
        for(int r=0; r< rows;r++){
            for(int c=0; c<cols;c++){
                switch (shelf[r][c].toUpperCase()){
                    case "C"->tmp=TileType.CAT;
                    case "B"->tmp=TileType.BOOK;
                    case "A"->tmp=TileType.ACTIVITY;
                    case "F"->tmp=TileType.FRAME;
                    case "T"->tmp=TileType.TROPHY;
                    case "P"->tmp=TileType.PLANT;
                    case "FIN"->tmp=TileType.FINISHED_USING;
                    case "X"->tmp=TileType.NOT_USED;
                    case ""->tmp=TileType.NOT_USED;
                }

                ris.setSingleTile(new Tile(tmp),r,c);
            }
        }
        return ris;
    }
}