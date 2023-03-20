public class PointCheck {
    public static void main(String[] arg){
        int height = 6; // devo farmelo passare
        int width = 5; //devo farmelo passare
        int point=0;
        int[][] Player_Shelf = {{0,0,1,1,1},    //devo farmelo passare
                                {0,0,0,0,1},
                                {0,0,0,0,0},
                                {0,0,0,0,0},
                                {0,0,0,0,0},
                                {0,0,0,0,0}};
        int CommonPoint=2;
        int GoalPoint=4;
        point = PointCheck(Player_Shelf, height, width,CommonPoint,GoalPoint);
        System.out.println(point);
    }
    public static int PointCheck(int[][] Player_Shelf, int height, int width, int CommonPoint, int GoalPoint){      //restituisce totalone punti
        int Tot=0;
        int Sum=0;
        for(int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if(Player_Shelf[i][j]!=0) {
                    Adiacenti_a_7(Player_Shelf, i, j, height, width, Player_Shelf[i][j]);
                    Sum = Conta_Adiacenti(Player_Shelf, height, width);
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
    private static void Adiacenti_a_7(int[][] PlayerShelf, int i, int j, int height, int width, int TileType) {     //utile per conteggio adiacenti (uso il 7 perchè non presente tra le tiles)

        if (i < 0 || i >= height || j < 0 || j >= width) {  //ho superato le dimensioni della matrice
            return;
        }
        if (PlayerShelf[i][j] != TileType) {    //ho trovato tipo differente
            return;
        }
        PlayerShelf[i][j] = 7;      //metto a 7 per differenziare
        Adiacenti_a_7(PlayerShelf, i - 1, j, height, width, TileType); // su
        Adiacenti_a_7(PlayerShelf, i + 1, j, height, width, TileType); // giù
        Adiacenti_a_7(PlayerShelf, i, j - 1, height, width, TileType); // sx
        Adiacenti_a_7(PlayerShelf, i, j + 1, height, width, TileType); // dx

    }
    private static int Conta_Adiacenti(int[][] PlayerShelf, int height, int width){
        int res=0;
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                if(PlayerShelf[i][j]==7) {
                    res = res + 1;
                    PlayerShelf[i][j]=0; //azzero per successivi
                }
            }
        }
        return res;
    }
}
