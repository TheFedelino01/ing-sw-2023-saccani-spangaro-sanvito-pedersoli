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

public class CommonVertixesTest {

    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    /*
    C X X X C
    X X X X X
    X X X X X
    X X X X X
    X X X X X
    C X X X C
     */
    @Test
    @DisplayName("Test vertixes")
    public void testVertixes(){
        Shelf test = new Shelf();
        for(int i = 0; i<DefaultValue.NumOfRowsShelf; i++){
            for(int j = 0; j<DefaultValue.NumOfColumnsShelf; j++){
                if((i==0 && j==0)||
                        (i==DefaultValue.NumOfRowsShelf-1 && j==DefaultValue.NumOfColumnsShelf-1)||
                        (i==0 && j==DefaultValue.NumOfColumnsShelf-1)||
                        (i==DefaultValue.NumOfRowsShelf-1 && j==0))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
            }
        }
        assertTrue(model.get(1).verify(test));
    }
}
