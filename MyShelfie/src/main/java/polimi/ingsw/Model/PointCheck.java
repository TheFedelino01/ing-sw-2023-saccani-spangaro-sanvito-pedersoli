package polimi.ingsw.Model;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.Enumeration.TileType;

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
                    Adiacenti_a_7(i, j, player.getShelf(), player.getShelf().get(i,j).getType());
                    Sum = Conta_Adiacenti(player.getShelf());
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


    private static void Adiacenti_a_7(int i, int j, Shelf temp, TileType tile) {     //utile per conteggio adiacenti (uso il 7 perchè non presente tra le tiles)

        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0 || j >= DefaultValue.NumOfColumnsShelf) {  //ho superato le dimensioni della matrice
            return;
        }
        if (temp.get(i,j).getType() != tile) {    //ho trovato tipo differente
            return;
        }

        temp.setSingleTile(control, i,j);      //metto a 7 per differenziare
        Adiacenti_a_7(i - 1, j, temp, tile); // su
        Adiacenti_a_7(i + 1, j, temp, tile); // giù
        Adiacenti_a_7(i, j - 1, temp, tile); // sx
        Adiacenti_a_7(i, j + 1, temp, tile); // dx

    }


    private static int Conta_Adiacenti(Shelf temp){
        int res=0;
        for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
            for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                if(temp.get(i,j)==control) {
                    res = res + 1;
                    temp.setSingleTile(used, i,j); //azzero per successivi
                }
            }
        }
        return res;
    }
}
