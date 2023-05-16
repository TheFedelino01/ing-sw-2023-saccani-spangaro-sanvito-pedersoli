package polimi.ingsw.model.cards.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.converter.DefaultArgumentConverter;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.Tile;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.enumeration.TileType;

import java.util.Random;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class CommonFourGroupsTest extends ShelfConverter {

    private CommonFourGroups card;

    @BeforeEach
    void setUp() {
        card = (CommonFourGroups) CommonCardFactory.getCommonCard(CardCommonType.CommonFourGroups);
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #1")
    public void testFourGroups1() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"C", "", "T", "", ""},
                {"C", "", "T", "", ""},
                {"C", "", "T", "", ""},
                {"C", "", "T", "", ""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #2")
    public void testFourGroups2() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"C", "P", "T", "", ""},
                {"C", "P", "T", "", ""},
                {"C", "P", "T", "", ""},
                {"C", "P", "T", "", ""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #3")
    public void testFourGroups3() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"C", "P", "T", "F", ""},
                {"C", "P", "T", "F", ""},
                {"C", "P", "T", "F", ""},
                {"C", "P", "T", "F", ""}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #4")
    public void testFourGroups4() {
        String[][] matrix = {
                {"A", "", "", "", ""},
                {"A", "", "", "", "P"},
                {"A", "", "", "", "P"},
                {"A", "", "", "", "P"},
                {"T", "T", "T", "T", "P"},
                {"F", "F", "F", "F", "P"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #5")
    public void testFourGroups5() {
        String[][] matrix = {
                {"P", "", "", "F", "P"},
                {"P", "", "", "F", "P"},
                {"P", "", "", "F", "P"},
                {"P", "P", "F", "F", "P"},
                {"A", "A", "A", "A", "A"},
                {"P", "P", "P", "A", "P"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #6")
    public void testFourGroups6() {
        String[][] matrix = {
                {"T", "", "", "", ""},
                {"T", "A", "", "", "P"},
                {"T", "A", "", "A", "P"},
                {"T", "A", "A", "", "P"},
                {"T", "A", "", "", "P"},
                {"A", "C", "C", "C", "C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #7")
    public void testFourGroups7() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", "A"},
                {"T", "", "", "", "C"},
                {"T", "", "", "", "T"}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #8")
    public void testFourGroups8() {
        String[][] matrix = {
                {"A", "", "T", "T", ""},
                {"A", "T", "T", "T", "T"},
                {"A", "", "T", "", ""},
                {"A", "C", "C", "C", "C"},
                {"A", "", "T", "P", "P"},
                {"A", "", "T", "P", "P"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #9")
    public void testFourGroups9() {
        String[][] matrix = {
                {"A", "A", "A", "A", "A"},
                {"A", "P", "P", "P", "A"},
                {"A", "P", "T", "C", "A"},
                {"A", "P", "T", "C", "A"},
                {"A", "P", "T", "C", "A"},
                {"A", "P", "T", "C", "A"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #10")
    public void testFourGroups10() {
        String[][] matrix = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"T", "", "", "", ""},
                {"C", "", "T", "T", ""},
                {"A", "A", "T", "", ""},
                {"C", "T", "T", "", ""}
        };

        Shelf test = setShelf(matrix);

        assertFalse(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #11")
    public void testFourGroups11() {
        String[][] matrix = {
                {"", "A", "", "", ""},
                {"C", "A", "A", "A", ""},
                {"C", "A", "F", "A", "C"},
                {"C", "A", "A", "A", "C"},
                {"C", "", "F", "", "C"},
                {"A", "A", "A", "A", "C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    @Test
    @DisplayName("Four Groups of 4 Tiles #12")
    public void testFourGroups12() {
        String[][] matrix = {
                {"", "A", "", "", ""},
                {"C", "A", "A", "A", ""},
                {"C", "", "F", "", "C"},
                {"C", "F", "F", "F", "C"},
                {"C", "", "F", "", "C"},
                {"A", "A", "", "", "C"}
        };

        Shelf test = setShelf(matrix);

        assertTrue(card.verify(test));
    }

    /*
    @Test
    @DisplayName("Four Groups of 4 Tiles #13")
    public void testFourGroups13() {
        Shelf test = randomShelfGenerator(true, 4);
        assertTrue(card.verify(test));
    }


    private Shelf randomShelfGenerator(boolean toReturn, int size) {
        int counter = 0;
        Shelf ret = new Shelf();
        Random randCoords = new Random();
        int row, col;
        if (toReturn) {
            while (counter < 4) {
                switch (counter) {
                    case (0) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.TROPHY, row, col, size);
                        counter++;
                    }
                    case (1) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.CAT, row, col, size);
                        counter++;
                    }
                    case (2) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.BOOK, row, col, size);
                        counter++;
                    }
                    case (3) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.ACTIVITY, row, col, size);
                        counter++;
                    }
                }

            }
            return ret;
        } else {
            while (counter < 4) {
                switch (counter) {
                    case (0) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.TROPHY, row, col, size - 1);
                        counter++;
                    }
                    case (1) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.CAT, row, col, size - 1);
                        counter++;
                    }
                    case (2) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.BOOK, row, col, size - 1);
                        counter++;
                    }
                    case (3) -> {
                        row = randCoords.nextInt(DefaultValue.NumOfRowsShelf);
                        col = randCoords.nextInt(DefaultValue.NumOfColumnsShelf);
                        placeRandomForm(ret, TileType.ACTIVITY, row, col, size - 1);
                        counter++;
                    }
                }

            }
            return ret;
        }
    }

    private boolean isSafeToPlace(Shelf toCheck, int r, int c) {
        if (r >= DefaultValue.NumOfRowsShelf || c >= DefaultValue.NumOfColumnsShelf)
            return false;
        return toCheck.get(r, c).isSameType(TileType.NOT_USED);
    }

    private void placeRandomForm(Shelf ret, TileType type, int row, int col, int size) {
        int count = 0;
        Random randDir = new Random();
        ret.setSingleTile(new Tile(type), row, col);
        while (count < size) {
            switch (randDir.nextInt(size)) {
                //UP
                case (0) -> {
                    if (isSafeToPlace(ret, row - 1, col)) {
                        count++;
                        ret.setSingleTile(new Tile(TileType.TROPHY), row - 1, col);
                    }
                }
                //RIGHT
                case (1) -> {
                    if (isSafeToPlace(ret, row, col + 1)) {
                        count++;
                        ret.setSingleTile(new Tile(TileType.TROPHY), row, col + 1);
                    }
                }
                //LEFT
                case (2) -> {
                    if (isSafeToPlace(ret, row, col - 1)) {
                        count++;
                        ret.setSingleTile(new Tile(TileType.TROPHY), row, col - 1);
                    }
                }
                //DOWN
                case (3) -> {
                    if (isSafeToPlace(ret, row + 1, col)) {
                        count++;
                        ret.setSingleTile(new Tile(TileType.TROPHY), row + 1, col);
                    }
                }
            }
        }
    }

  */
}
