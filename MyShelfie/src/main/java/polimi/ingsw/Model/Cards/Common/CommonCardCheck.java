package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.HashMap;
import java.util.Map;

public class CommonCardCheck extends CardCommon {

    private final static Tile controller = new Tile(TileType.NOT_USED);

    public CommonCardCheck(CardCommonType type) {
        super(type);

    }

    /*public static int commonCheck(Player player, int commonCard) {      //restituisce 1 se ho vinto la CommonCard, 0 se no, 404 se ho errore

        int sum = 0;
        int win = 0;
        Shelf playerShelfDupe = new Shelf();
        initializeDupe(player.getShelf(), playerShelfDupe);
        switch (commonCard) {

            case 0:       //done
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //controllo presenza verticale
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1) {   //analizzo verticale
                            if (playerShelfDupe.get(i, j) != controller && playerShelfDupe.get(i, j) == playerShelfDupe.get(i + 1, j)) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    win = 1;
                                    break;
                                }
                                //elimino tutti altri elementi uguali attaccati
                                AzzeraAdiacenti(playerShelfDupe, i, j, playerShelfDupe.get(i, j));
                            }
                        }
                        if (j < DefaultValue.NumOfColumnsShelf - 1) {
                            if (playerShelfDupe.get(i, j) != controller && playerShelfDupe.get(i, j) == playerShelfDupe.get(i, j + 1)) {
                                sum = sum + 1;
                                if (sum == 6) {
                                    win = 1;
                                    break;
                                }
                                AzzeraAdiacenti(playerShelfDupe, i, j, playerShelfDupe.get(i, j));
                            }
                        }
                    }
                }
                break;
            case 1:     //quattro tessere dello stesso tipo agli angoli dello shelf
                if (playerShelfDupe.get(0, 0) != controller &&
                        playerShelfDupe.get(0, 0) == playerShelfDupe.get(0, DefaultValue.NumOfColumnsShelf - 1) &&
                        playerShelfDupe.get(0, DefaultValue.NumOfColumnsShelf - 1) == playerShelfDupe.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) &&
                        playerShelfDupe.get(DefaultValue.NumOfRowsShelf - 1, DefaultValue.NumOfColumnsShelf - 1) == playerShelfDupe.get(DefaultValue.NumOfRowsShelf - 1, 0))
                    win = 1;
                break;
            case 2://done
                int check=0;
                for(int i=0; i<DefaultValue.NumOfRowsShelf; i++)
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if(playerShelfDupe.get(i, j)!=controller) {  //controllo che ci siano gruppi da 4 in qualsiasi direzione
                            Adiacenti_a_7(playerShelfDupe, i, j, playerShelfDupe.get(i, j));
                            sum = Conta_Adiacenti(playerShelfDupe);
                            if (sum >= 4) {
                                AzzeraAdiacenti(playerShelfDupe, i, j, playerShelfDupe.get(i, j));
                                check = check + 1;
                                if (check == 4) {
                                    win = 1;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 3: //done
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //controllo presenza verticale
                {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1) {   //analizzo quadrato
                            if (playerShelfDupe.get(i, j) != controller &&
                                    playerShelfDupe.get(i, j) == playerShelfDupe.get(i + 1, j) &&
                                    playerShelfDupe.get(i, j) == playerShelfDupe.get(i, j + 1) &&
                                    playerShelfDupe.get(i, j) == playerShelfDupe.get(i + 1, j + 1)) {
                                sum = sum + 1;
                                if (sum == 2) {
                                    win = 1;
                                    break;
                                }
                                //elimino tutti altri elementi uguali attaccati
                                AzzeraAdiacenti(playerShelfDupe, i, j, playerShelfDupe.get(i, j));
                            }
                        }
                    }
                }
                break;
            case 4:     //done
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    Map<TileType, Integer> colCheck = new HashMap<>();
                    int ok = 0; //controllo singola colonna, sum controlla se ne ho 3 ok
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (playerShelfDupe.get(i, j) != controller)
                            colCheck.putIfAbsent(playerShelfDupe.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + colCheck.get(t);
                    }
                    if (ok <= 3)
                        sum = sum + 1;
                    if (sum == 3) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 5:     //done
                Map<TileType, Integer> tileCheck = new HashMap<>();
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (playerShelfDupe.get(i, j) != controller) {
                            if (tileCheck.get(playerShelfDupe.get(i, j).getType()) == null) {
                                tileCheck.putIfAbsent(playerShelfDupe.get(i, j).getType(), 1);
                            } else {
                                tileCheck.put(playerShelfDupe.get(i, j).getType(), tileCheck.get(playerShelfDupe.get(i, j).getType()) + 1);
                            }
                        }
                    }
                }
                for (TileType t : TileType.values()) {
                    if (tileCheck.get(t) >= 8) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 6:     //diagonale di 5 tessere dello stesso tipo
                for (int i = 0; i < 2; i++) {
                    int check1 = 1;
                    int check2 = 1;
                    for (int k = i, j = 0; k < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1; k++, j++) {     //controllo sx dx
                        if (playerShelfDupe.get(k,j) == controller || playerShelfDupe.get(k,j) !=playerShelfDupe.get(k+1,j+1) )
                            check1 = 0;
                    }
                    for (int k = i, j = DefaultValue.NumOfColumnsShelf - 1; k < DefaultValue.NumOfRowsShelf - 1 && j > 0; k++, j--) {     //controllo dx sx
                        if (playerShelfDupe.get(k,j) == controller || playerShelfDupe.get(k,j) != playerShelfDupe.get(k+1,j-1))
                            check2 = 0;
                    }
                    if (check1 == 1 || check2 == 1) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 7:     //quattro righe con al massimo 3 tipi di tessere differenti
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    Map<TileType, Integer> rowCheck = new HashMap<>();
                    int ok = 0; //contollo singola riga, sum controlla se ne ho max 3 ok
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (playerShelfDupe.get(i,j) != controller)
                            rowCheck.putIfAbsent(playerShelfDupe.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + rowCheck.get(t);
                    }
                    if (ok <= 3)
                        sum = sum + 1;
                    if (sum == 4) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 8:     //done
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    Map<TileType, Integer> colCheck = new HashMap<>();
                    int ok = 0; //contollo singola colonna, sum controlla se ne ho 3 ok
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (playerShelfDupe.get(i,j) != controller)
                            colCheck.putIfAbsent(playerShelfDupe.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + colCheck.get(t);
                    }
                    if (ok == 6)
                        sum = sum + 1;
                    if (sum == 2) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 9:     //due righe formate da 5 differenti tipi di tiles (si può pensare di aggregarla alla #7)
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    Map<TileType, Integer> rowCheck = new HashMap<>();
                    int ok = 0; //contollo singola riga, sum controlla se ne ho 3 ok
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (playerShelfDupe.get(i,j) != controller)
                            rowCheck.putIfAbsent(playerShelfDupe.get(i, j).getType(), 1);
                    }
                    for (TileType t : TileType.values()) {
                        ok = ok + rowCheck.get(t);
                    }
                    if (ok == 5)
                        sum = sum + 1;
                    if (sum == 2) {
                        win = 1;
                        break;
                    }
                }
                break;
            case 10:    //      5 tessere a formare una croce (dubbio sulle regole controllo che le altre tessere siano diverse o no?)
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                        if (i < DefaultValue.NumOfRowsShelf - 2 && j < DefaultValue.NumOfColumnsShelf - 2) {   //analizzo croce
                            if (playerShelfDupe.get(i,j) != controller &&
                                    playerShelfDupe.get(i,j) == playerShelfDupe.get(i+2,j) &&
                                    playerShelfDupe.get(i,j) == playerShelfDupe.get(i,j+2) &&
                                    playerShelfDupe.get(i,j) == playerShelfDupe.get(i+1,j+1) &&
                                    playerShelfDupe.get(i,j) == playerShelfDupe.get(i+2,j+2)) {
                                win = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            case 11:    //done
                int[] spaceCheck = {0,0,0,0,0};
                win = 1;
                int checkSxToDx = 1;
                int checkDxToSx = 1;
                int checkSxToDx2 = 1;
                int checkDxToSx2 = 1;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (playerShelfDupe.get(i,j) == controller)
                            spaceCheck[j]++;

                    }
                }
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkSxToDx = 0;
                    }
                    sum++;
                }
                sum--;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkDxToSx = 0;
                    }
                    sum--;
                }
                sum += 2;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkSxToDx2 = 0;
                    }
                    sum++;
                }
                sum--;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkDxToSx2 = 0;
                    }
                    sum--;
                }
                if (checkSxToDx == 0 && checkDxToSx == 0 && checkSxToDx2 == 0 && checkDxToSx2 == 0)
                    win = 0;
                break;
            default:
                System.out.println("Error CommonCard");
                win = 404;
                break;
        }
        return win;
    }

    private static void AzzeraAdiacenti(Shelf playerShelf, int i, int j, Tile tile) {

        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0 || j >= DefaultValue.NumOfColumnsShelf) {  //ho superato le dimensioni della matrice
            return;
        }
        if (playerShelf.get(i, j) != tile) {    //ho trovato tipo differente
            return;
        }
        playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i, j);      //azzero
        AzzeraAdiacenti(playerShelf, i - 1, j, tile); // su
        AzzeraAdiacenti(playerShelf, i + 1, j, tile); // giù
        AzzeraAdiacenti(playerShelf, i, j - 1, tile); // sx
        AzzeraAdiacenti(playerShelf, i, j + 1, tile); // dx
    }

    private static void initializeDupe(Shelf playerShelf, Shelf playerShelfDupe) {
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) //creo duplicato fittizio
        {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                playerShelfDupe.setSingleTile(playerShelf.get(i, j), i, j);
            }
        }
    }

    private static void Adiacenti_a_7(Shelf playerShelf, int i, int j, Tile tile) {     //utile per conteggio adiacenti (uso il 7 perchè non presente tra le tiles)

        if (i < 0 || i >= DefaultValue.NumOfRowsShelf || j < 0 || j >= DefaultValue.NumOfColumnsShelf) {  //ho superato le dimensioni della matrice
            return;
        }
        if (playerShelf.get(i,j).getType() != tile.getType()) {    //ho trovato tipo differente
            return;
        }
        playerShelf.setSingleTile(new Tile(TileType.FINISHED_USING), i,j);     //metto a 7 per differenziare
        Adiacenti_a_7(playerShelf, i - 1, j, tile); // su
        Adiacenti_a_7(playerShelf, i + 1, j, tile); // giù
        Adiacenti_a_7(playerShelf, i, j - 1, tile); // sx
        Adiacenti_a_7(playerShelf, i, j + 1, tile); // dx

    }

    private static int Conta_Adiacenti(Shelf playerShelf){
        int res=0;
        for (int i=0; i<DefaultValue.NumOfRowsShelf; i++){
            for (int j=0; j<DefaultValue.NumOfColumnsShelf; j++){
                if(playerShelf.get(i,j)==new Tile(TileType.FINISHED_USING)) {
                    res = res + 1;
                }
            }
        }
        return res;
    }*/
}