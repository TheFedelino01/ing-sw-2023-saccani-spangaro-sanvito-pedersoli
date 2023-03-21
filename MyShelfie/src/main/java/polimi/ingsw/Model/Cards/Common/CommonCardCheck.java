package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Shelf;

public class CommonCardCheck extends CardCommon{
    public CommonCardCheck(CardCommonType type) {
        super(type);
    }

    public static int commonCheck(Player player, int NumDifferentTiles, int commonCard){      //restituisce 1 se ho vinto la CommonCard, 0 se no, 404 se ho errore
        int sum=0;
        int win=0;
        switch(commonCard)
        {
            case 0:         //6 gruppi separati formati da 2 tessere adiacenti dello stesso tipo
                int[][] Player_Shelf_Duplicate = new int[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
                InizializzoDuplicato(Player_Shelf, Player_Shelf_Duplicate, DefaultValue.NumOfRowsShelf, DefaultValue.NumOfColumnsShelf);
                for(int i=0; i<DefaultValue.NumOfRowsShelf; i++) //controllo presenza verticale
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1) {   //analizzo verticale
                            if (Player_Shelf_Duplicate[i][j] != 0 && Player_Shelf_Duplicate[i][j] == Player_Shelf_Duplicate[i + 1][j]) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    win = 1;
                                    break;
                                }
                                //elimino tutti altri elementi uguali attaccati
                                AzzeraAdiacenti(Player_Shelf_Duplicate, i, j, Player_Shelf_Duplicate[i][j]);
                            }
                        }
                        if (j < DefaultValue.NumOfColumnsShelf - 1) {
                            if (Player_Shelf_Duplicate[i][j] != 0 && Player_Shelf_Duplicate[i][j] == Player_Shelf_Duplicate[i][j + 1]) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    win = 1;
                                    break;
                                }
                                AzzeraAdiacenti(Player_Shelf_Duplicate, i, j, Player_Shelf_Duplicate[i][j]);
                            }
                        }
                    }
                }
                break;
            case 1:     //quattro tessere dello stesso tipo agli angoli dello shelf
                if(Player_Shelf[0][0]!=0 &&
                        Player_Shelf[0][0]==Player_Shelf[0][DefaultValue.NumOfColumnsShelf-1] &&
                        Player_Shelf[0][DefaultValue.NumOfColumnsShelf-1]==Player_Shelf[DefaultValue.NumOfRowsShelf-1][DefaultValue.NumOfColumnsShelf-1] &&
                        Player_Shelf[DefaultValue.NumOfRowsShelf-1][DefaultValue.NumOfColumnsShelf-1]==Player_Shelf[DefaultValue.NumOfRowsShelf-1][0])
                    win=1;
                break;
            case 2:
                int[][] Player_Shelf_Duplicate2 = new int[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
                InizializzoDuplicato(Player_Shelf,Player_Shelf_Duplicate2,DefaultValue.NumOfRowsShelf,DefaultValue.NumOfColumnsShelf);
                for(int i=0; i<DefaultValue.NumOfRowsShelf; i++) //controllo presenza verticale
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 3) {   //analizzo verticale
                            if (Player_Shelf_Duplicate2[i][j] != 0 &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i + 1][j] &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i + 2][j] &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i + 3][j]) {
                                sum = sum + 1;
                                if (sum == 4) {
                                    win = 1;
                                    break;
                                }
                                //elimino tutti altri elementi uguali attaccati
                                AzzeraAdiacenti(Player_Shelf_Duplicate2, i, j, Player_Shelf_Duplicate2[i][j]);
                            }
                        }
                        if (j < DefaultValue.NumOfColumnsShelf - 3) {
                            if (Player_Shelf_Duplicate2[i][j] != 0 &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i][j + 1] &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i][j + 2] &&
                                    Player_Shelf_Duplicate2[i][j] == Player_Shelf_Duplicate2[i][j + 3] ) {
                                sum = sum + 1;
                                if (sum == 4) {
                                    win = 1;
                                    break;
                                }
                                AzzeraAdiacenti(Player_Shelf_Duplicate2, i, j, Player_Shelf_Duplicate2[i][j]);
                            }
                        }
                    }
                }
                break;
            case 3:
                int[][] Player_Shelf_Duplicate3 = new int[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];
                InizializzoDuplicato(Player_Shelf,Player_Shelf_Duplicate3,DefaultValue.NumOfRowsShelf,DefaultValue.NumOfColumnsShelf);
                for(int i=0; i<DefaultValue.NumOfRowsShelf; i++) //controllo presenza verticale
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analizzo quadrato
                            if (Player_Shelf_Duplicate3[i][j] != 0 &&
                                    Player_Shelf_Duplicate3[i][j] == Player_Shelf_Duplicate3[i + 1][j] &&
                                    Player_Shelf_Duplicate3[i][j] == Player_Shelf_Duplicate3[i][j + 1] &&
                                    Player_Shelf_Duplicate3[i][j] == Player_Shelf_Duplicate3[i + 1][j + 1]) {
                                sum = sum + 1;
                                if (sum == 2) {
                                    win = 1;
                                    break;
                                }
                                //elimino tutti altri elementi uguali attaccati
                                AzzeraAdiacenti(Player_Shelf_Duplicate3, i, j, Player_Shelf_Duplicate3[i][j]);
                            }
                        }
                    }
                }
                break;
            case 4:     //tre colonne formate ciascuna da 6 tessere di al più 3 tipi differenti (per colonna)
                for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    int[] colCheck = {0,0,0,0,0};
                    int ok=0; //contollo singola colonna, sum controlla se ne ho 3 ok
                    for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                        if (Player_Shelf[i][j]!=0)
                            colCheck[Player_Shelf[i][j]-1]=1;
                    }
                    for (int i=0; i<NumDifferentTiles; i++){
                        ok = ok + colCheck[i];
                    }
                    if (ok<=3)
                        sum=sum+1;
                    if(sum==3){
                        win=1;
                        break;
                    }
                }
                break;
            case 5:     //otto tessere dello stesso tipo
                int[] tileCheck = {0,0,0,0,0,0};
                for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                    for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                        if(Player_Shelf[i][j]!=0)
                            tileCheck[Player_Shelf[i][j]-1]=tileCheck[Player_Shelf[i][j]-1]+1;
                    }
                }
                for (int i=0; i<NumDifferentTiles; i++){
                    if(tileCheck[i]>=8){
                        win=1;
                        break;
                    }
                }
                break;
            case 6:     //diagonale di 5 tessere dello stesso tipo
                for (int i=0; i<2; i++) {
                    int check1=1;
                    int check2=1;
                    for (int k = i, j = 0; k < DefaultValue.NumOfRowsShelf-1 && j < DefaultValue.NumOfColumnsShelf-1; k++, j++) {     //controllo sx dx
                        if(Player_Shelf[k][j]==0 || Player_Shelf[k][j]!=Player_Shelf[k+1][j+1])
                            check1=0;
                    }
                    for (int k = i, j = DefaultValue.NumOfColumnsShelf-1; k < DefaultValue.NumOfRowsShelf-1 && j >0 ; k++, j--) {     //controllo dx sx
                        if(Player_Shelf[k][j]==0 || Player_Shelf[k][j]!=Player_Shelf[k+1][j-1])
                            check2=0;
                    }
                    if(check1==1 || check2==1){
                        win=1;
                        break;
                    }
                }
                break;
            case 7:     //quattro righe con al massimo 3 tipi di tessere differenti
                for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                    int[] rowCheck = {0,0,0,0,0,0};
                    int ok=0; //contollo singola riga, sum controlla se ne ho max 3 ok
                    for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                        if (Player_Shelf[i][j]!=0)
                            rowCheck[Player_Shelf[i][j]-1]=1;
                    }
                    for (int j=0; j<NumDifferentTiles; j++){
                        ok = ok + rowCheck[j];
                    }
                    if (ok<=3)
                        sum=sum+1;
                    if(sum==4){
                        win=1;
                        break;
                    }
                }
                break;
            case 8:     //due colonne formate ciascuna da 6 tipi differenti di tessere (si può pensare di aggregare alla #4) (forse anche #7)
                for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    int[] colCheck = {0,0,0,0,0};
                    int ok=0; //contollo singola colonna, sum controlla se ne ho 3 ok
                    for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                        if (Player_Shelf[i][j]!=0)
                            colCheck[Player_Shelf[i][j]-1]=1;
                    }
                    for (int i=0; i<NumDifferentTiles; i++){
                        ok = ok + colCheck[i];
                    }
                    if (ok==6)
                        sum=sum+1;
                    if(sum==2){
                        win=1;
                        break;
                    }
                }
                break;
            case 9:     //due righe formate da 5 differenti tipi di tiles   (si può pensare di aggregarla alla #7)
                for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                    int[] rowCheck = {0,0,0,0,0,0};
                    int ok=0; //contollo singola riga, sum controlla se ne ho 3 ok
                    for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                        if (Player_Shelf[i][j]!=0)
                            rowCheck[Player_Shelf[i][j]-1]=1;
                    }
                    for (int j=0; j<NumDifferentTiles; j++){
                        ok = ok + rowCheck[j];
                    }
                    if (ok==5)
                        sum=sum+1;
                    if(sum==2){
                        win=1;
                        break;
                    }
                }
                break;
            case 10:    //      5 tessere a formare una croce (dubbio sulle regole controllo che le altre tessere siano diverse o no?)
                for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
                    for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                        if (i < DefaultValue.NumOfRowsShelf - 2 && j < DefaultValue.NumOfColumnsShelf - 2) {   //analizzo croce
                            if (Player_Shelf[i][j] != 0 &&
                                    Player_Shelf[i][j] == Player_Shelf[i + 2][j] &&
                                    Player_Shelf[i][j] == Player_Shelf[i][j + 2] &&
                                    Player_Shelf[i][j] == Player_Shelf[i + 1][j + 1] &&
                                    Player_Shelf[i][j] == Player_Shelf[i + 2][j + 2]) {
                                win = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            case 11:    //colonne 5 colonne di altezza decrescente
                int[] SpaceCheck = {0,0,0,0,0};
                win=1;
                int checkSxToDx =1;
                int checkDxToSx =1;
                int checkSxToDx2 =1;
                int checkDxToSx2 =1;
                for(int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    for(int i=0; i<DefaultValue.NumOfRowsShelfheig; i++){
                        if(Player_Shelf[i][j]==0)
                            SpaceCheck[j]=SpaceCheck[j]+1;
                    }
                }
                for(int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    if(SpaceCheck[j]!=sum){
                        checkSxToDx=0;
                    }
                    sum=sum+1;
                }
                sum=sum-1;
                for(int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    if(SpaceCheck[j]!=sum){
                        checkDxToSx=0;
                    }
                    sum=sum-1;
                }
                sum=sum+1;
                for(int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    if(SpaceCheck[j]!=sum){
                        checkSxToDx2=0;
                    }
                    sum=sum+1;
                }
                sum=sum-1;
                for(int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                    if(SpaceCheck[j]!=sum){
                        checkDxToSx2=0;
                    }
                    sum=sum-1;
                }
                if(checkSxToDx==0 && checkDxToSx==0 && checkSxToDx2==0 && checkDxToSx2==0)
                    win=0;
                break;
            default:
                System.out.println("Error CommonCard");
                win = 404;
                break;
        }
        return win;
    }
    private static void AzzeraAdiacenti(int[][] playerShelf, int i, int j, int TileType) {

        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0 || j >= DefaultValue.NumOfColumnsShelf) {  //ho superato le dimensioni della matrice
            return;
        }
        if (playerShelf[i][j] != TileType) {    //ho trovato tipo differente
            return;
        }
        playerShelf[i][j] = 0;      //azzero
        AzzeraAdiacenti(playerShelf, i - 1, j, TileType); // su
        AzzeraAdiacenti(playerShelf, i + 1, j, TileType); // giù
        AzzeraAdiacenti(playerShelf, i, j - 1, TileType); // sx
        AzzeraAdiacenti(playerShelf, i, j + 1, TileType); // dx
    }

    private static void InizializzoDuplicato(Shelf playerShelf, Shelf playerShelfDupe){
        for(int i = 0; i< DefaultValue.NumOfRowsShelf; i++) //creo duplicato fittizio
        {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                playerShelfDupe.setSingleTile(playerShelf.get(i,j) ,i,j);
            }
        }
        return;
    }
}


