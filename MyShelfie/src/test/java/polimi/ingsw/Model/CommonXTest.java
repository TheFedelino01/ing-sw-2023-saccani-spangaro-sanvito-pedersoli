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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonXTest {

    List<CommonCard> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for (CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    /*
    C X C X X
    X C X X X
    C X C X X
    X X X X X
    X X X X X
    X X X X X
     */
    @Test
    @DisplayName("Test X")
    public void testX(){
        Shelf test = new Shelf();
        for(int i = 0; i<DefaultValue.NumOfRowsShelf; i++){
            for(int j = 0; j<DefaultValue.NumOfColumnsShelf; j++){
                if((i==0 && j==0)||
                        (i==0 && j==2)||
                        (i==2 && j==0)||
                        (i==2 && j==2)||
                        (i==1 && j==1))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.randomTile()), i, j);
            }
        }
        assertTrue(model.get(10).verify(test));
    }

    @Test
    @DisplayName("Test expected to return false")
    public void testFail() {
        Shelf test = new Shelf();
        for(int i = 0; i<DefaultValue.NumOfRowsShelf; i++){
            for(int j = 0; j<DefaultValue.NumOfColumnsShelf; j++){
                if((i==0 && j==0)||
                        (i==0 && j==2)||
                        (i==2 && j==0)||
                        (i==2 && j==2))
                    test.setSingleTile(new Tile(TileType.CAT), i, j);
                else
                    test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(10).verify(test));
    }

    @Test
    @DisplayName("Test with empty shelf")
    public void testEmptyShelf(){
        Shelf test = new Shelf();
        for(int i = 0; i<DefaultValue.NumOfRowsShelf; i++){
            for(int j = 0; j<DefaultValue.NumOfColumnsShelf; j++){
                test.setSingleTile(new Tile(TileType.NOT_USED), i, j);
            }
        }
        assertFalse(model.get(10).verify(test));
    }
}
