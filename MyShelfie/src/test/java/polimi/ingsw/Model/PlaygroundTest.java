package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.TileGrabbedNotCorrectException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaygroundTest {

    Playground p2,p3,p4;

    private int data[][] ={
            {0,0,0,3,4,0,0,0,0},
        {0,0,0,2,2,4,0,0,0},
        {0,0,3,2,2,2,3,0,0},
        {0,4,2,2,2,2,2,2,3},
        {4,2,2,2,2,2,2,2,4},
        {3,2,2,2,2,2,2,4,0},
        {0,0,3,2,2,2,3,0,0},
        {0,0,0,4,2,2,0,0,0},
        {0,0,0,0,4,3,0,0,0}
    };

    @BeforeEach
    void setUp() {
        p2 = new Playground(2);
        p3 = new Playground(3);
        p4 = new Playground(4);

    }

    @Test
    @DisplayName("Test position fill")
    void testPlaygroundClass() {
        testTilePosition(2);
        testTilePosition(3);
        testTilePosition(4);

        int totalTileInBag=DefaultValue.NumOfTilesPerType*DefaultValue.NumOfTileTypes;
        if(p2.getNumOfTileinTheBag()!=(totalTileInBag)-29){
            assertTrue(false, "Num of tile in bag wrong for 2 players");
        }
        if(p3.getNumOfTileinTheBag()!=(totalTileInBag)-29-8){
            assertTrue(false, "Num of tile in bag wrong for 3 players");
        }
        if(p4.getNumOfTileinTheBag()!=(totalTileInBag)-29-8-8){
            assertTrue(false, "Num of tile in bag wrong for 4 players");
        }
    }

    private void testTilePosition(int numPlayer){
        Playground test=p2;
        switch (numPlayer){
            case 2->test=p2;
            case 3-> test=p3;
            case 4-> test=p4;
            default -> assertTrue(false, "Num of player over");
        }
        for(int r=0; r<DefaultValue.PlaygroundSize;r++){
            for(int c=0; c<DefaultValue.PlaygroundSize;c++){
                if(data[r][c]!=0 && data[r][c]<=numPlayer && (test.getTile(r,c).getType().equals(TileType.NOT_USED) || test.getTile(r,c).getType().equals(TileType.FINISHED_USING))){
                    assertTrue(false, "Tile wrong position");
                }
                if(data[r][c]!=0 && data[r][c]>numPlayer && !(test.getTile(r,c).getType().equals(TileType.NOT_USED) )){
                    assertTrue(false, "Tile wrong position");
                }
            }
        }

        assertTrue(true);
    }

    @Test
    @DisplayName("Grab Tile")
    void testGrab() {
        //todo il test verifica solo il grab di 2 tile semplici
        List<Tile> ris= null;
        try {
            ris = p2.grabTile(1,3, Direction.RIGHT,2);
        } catch (TileGrabbedNotCorrectException e) {
            throw new RuntimeException(e);
        }

        if(!p2.getTile(1,3).isSameType(TileType.FINISHED_USING) || !p2.getTile(1,4).isSameType(TileType.FINISHED_USING) ){
            assertTrue(false, "Tile grabbed wrong");
        }
        if(ris.size()!=2){
            assertTrue(false, "Tile grabbed wrong");
        }
    }
}