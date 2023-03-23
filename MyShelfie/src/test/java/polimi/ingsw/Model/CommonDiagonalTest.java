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

public class CommonDiagonalTest {

    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    //I'll be using the CAT tile as the one to assert its value
    /*

     */
    @Test
    @DisplayName("Test Diagonal cards")

    public void testDiagonal() {
        Shelf test = new Shelf();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i >= j) {
                    if (i == j) {
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {
                        test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }
        //Check first algorithm
        assertTrue(model.get(6).verify(test));
        assertTrue(model.get(11).verify(test));
    }

}
