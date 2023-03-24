package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.TileType;

import java.util.Objects;

public class PointCheck extends Point{

    public PointCheck(Integer point, Card referredTo) {
        super(point, referredTo);
    }

    private Player player;
    private static final Tile control = new Tile(TileType.NOT_USED);
    private static final Tile used = new Tile(TileType.FINISHED_USING);
    public static int PointCheck(Player player, int commonPoint, int goalPoint){      //restituisce totalone punti
        int Tot=0;
        int Sum=0;
        for(int i=0; i<DefaultValue.NumOfRowsShelf; i++) {
            for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++) {
                if(player.getShelf().get(i,j)!=control) {
                    adjacentToFU(player.getShelf(), i, j, player.getShelf().get(i,j));
                    Sum = countAdjacent(player.getShelf());
                    if (Sum > 2) {
                        if (Sum == 3 || Sum == 4)
                            Tot = Tot + Sum - 1;
                        else {
                            if (Sum == 5)
                                Tot = Tot + Sum;
                            else
                                Tot = Tot + 8;
                        }
                    }
                }
            }
        }
        return Tot+commonPoint+goalPoint;
    }


    private static void adjacentToFU(Shelf playerShelf, int i, int j, Tile tile) {     //useful for adjacent count(FU is finished_using)
        if (checkIfSafe(playerShelf, i, j, tile)){
            playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);     //finished using
            if(i>0)
                adjacentToFU(playerShelf, i - 1, j, tile); // up
            if(i<DefaultValue.NumOfRowsShelf-1)
                adjacentToFU(playerShelf, i + 1, j, tile); // down
            if(j>0)
                adjacentToFU(playerShelf, i, j - 1, tile); // sx
            if(j<DefaultValue.NumOfColumnsShelf-1)
                adjacentToFU(playerShelf, i, j + 1, tile); // dx
        }
    }


    private static boolean checkIfSafe(Shelf playerShelf, int i, int j, Tile tile) {
        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0
                || j >= DefaultValue.NumOfColumnsShelf) {  //check if out of bounds
            return false;
        }
        //check if different type is found
        try{
            return playerShelf.get(i, j).getType() == tile.getType();
        }catch(StackOverflowError er){
            er.printStackTrace();
            return false;
        }
    }

    private static int countAdjacent(Shelf playerShelf) {
        int res = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (Objects.equals(playerShelf.get(i, j), new Tile(TileType.FINISHED_USING))) {
                    res = res + 1;
                }
            }
        }
        return res;
    }
}
