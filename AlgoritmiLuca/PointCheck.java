public class PointCheck {
	
	private Player player;
	
    public static int PointCheck(Player player){      //restituisce totalone punti
        int Tot=0;
        int Sum=0;
        for(int i=0; i<DefaultValue.NumOfRowsShelf; i++) {
            for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++) {
                if(player.getShelf()[i][j]!=0) {
                    Adiacenti_a_7(player.getShelf(), i, j, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf, player.getShelf().get(i,j));
                    Sum = Conta_Adiacenti(player.getShelf(), DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf);
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
        return Tot+CommonPoint+GoalPoint;
    }
	
	
    private static void Adiacenti_a_7(int i, int j, Shelf temp, TileType tile) {     //utile per conteggio adiacenti (uso il 7 perchè non presente tra le tiles)

        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0 || j >= DefaultValue.NumOfColumnsShelf) {  //ho superato le dimensioni della matrice
            return;
        }
        if (temp.get(i,j).getTileType() != tile) {    //ho trovato tipo differente
            return;
        }
		
        temp.setShelf()[i][j] = 7;      //metto a 7 per differenziare
        Adiacenti_a_7(temp, i - 1, j, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf, tile); // su
        Adiacenti_a_7(temp, i + 1, j, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf, tile); // giù
        Adiacenti_a_7(temp, i, j - 1, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf, tile); // sx
        Adiacenti_a_7(temp, i, j + 1, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf, tile); // dx

    }
	
	
    private static int Conta_Adiacenti(Shelf temp){
        int res=0;
        for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
            for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                if(temp.get(i,j)==7) {
                    res = res + 1;
                    temp.set(i,j)=0; //azzero per successivi
                }
            }
        }
        return res;
    }
}
