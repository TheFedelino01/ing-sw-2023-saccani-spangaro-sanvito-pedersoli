public class GoalCardCheck {
    public static void main(String[] args){
        int height = 6; // devo farmelo passare
        int width = 5; //devo farmelo passare
        int controllo=0;
        int[][] Player_Shelf = {{0,0,0,1,1},    //devo farmelo passare
                                {0,0,0,1,1},
                                {0,0,2,2,1},
                                {0,5,2,4,5},
                                {2,3,6,1,3},
                                {6,2,2,3,1}};
        int[][] GoalCardMatrix =   {{0,0,0,4,0},    //devo farmelo passare
                                    {0,0,0,0,0},
                                    {0,0,0,0,0},
                                    {0,0,0,0,6},
                                    {0,4,3,0,0},
                                    {0,1,0,0,0}};
        controllo = GoalCardCheck(Player_Shelf, GoalCardMatrix, height, width);

        System.out.println(controllo);
        }
    public static int GoalCardCheck(int[][] Player_Shelf, int[][] GoalCardMatrix, int height, int width){       //restituisce punteggio o 404 se ho errore
        int win=12;
        int check=0;
        int check2=0;
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(GoalCardMatrix[i][j]!=0){
                    check=check+1;
                    if(GoalCardMatrix[i][j]!=Player_Shelf[i][j]){
                        check2=check2+1;
                        if(check2<=2)
                            win=win-3;
                        else
                            if(check2<=4)
                                win=win-2;
                            else
                                if(check2==5)
                                    win=1;
                                else
                                    win=0;
                    }
                }
            }
        }
        if(check!=6)
            return 404; //errore
        return win;
    }
}
