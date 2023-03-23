package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonVerticalTest {
    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    @Test
    @DisplayName("Test Vertical")
    public void testVertical0() {
        /*
    C X B X T
    C X B X T
    C X B X T
    C X B X T
    C X B X T
    C X B X T
     */
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0) {
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                } else if (j == 2) {
                    test.setSingleTile(new Tile(TileType.BOOK), i, j);
                } else if (j == 4) {
                    test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                }
            }
        }
        assertTrue(model.get(4).verify(test));

    }

    @Test
    @DisplayName("Test Vertical")
    public void testVertical1() {

        /*
        C X C X X
        B X B X X
        F X F X X
        T X T X X
        A X A X X
        P X P X X
         */
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (j == 0 || j == 2) {
                    switch (i) {
                        case (0) -> test.setSingleTile(new Tile(TileType.CAT), i, j);
                        case (1) -> test.setSingleTile(new Tile(TileType.BOOK), i, j);
                        case (2) -> test.setSingleTile(new Tile(TileType.FRAME), i, j);
                        case (3) -> test.setSingleTile(new Tile(TileType.TROPHY), i, j);
                        case (4) -> test.setSingleTile(new Tile(TileType.ACTIVITY), i, j);
                        case (5) -> test.setSingleTile(new Tile(TileType.PLANT), i, j);
                        default -> {
                        }
                    }
                } else {
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                }
            }
        }
        assertTrue(model.get(8).verify(test));
    }


}
