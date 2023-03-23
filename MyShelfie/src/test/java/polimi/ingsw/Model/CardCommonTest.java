package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardCommonTest {

    List<CardCommon> model = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CommonCardFactory c = new CommonCardFactory();
        for(CardCommonType t : CardCommonType.values())
            model.add(c.getCommonCard(t));
    }

    @Test
    @DisplayName("Test Diagonal cards")
    public void testDiagonal(){
        Shelf test = new Shelf();
        for(int i = DefaultValue.NumOfRowsShelf - 1 ; i > 0 ; i--){
            for(int j = 0; j < DefaultValue.NumOfColumnsShelf; j++){
                if(i<=j){
                    if(i==j){
                        test.setSingleTile(new Tile(TileType.CAT), i, j);
                    }else{
                        test.setSingleTile(new Tile(TileType.randomTile()), i, j);
                    }
                }
            }
        }
        assertTrue(model.get(0).verify(test));
        //for(int)
    }
}
