package polimi.ingsw.Model.Cards.Goal;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

public class GoalCardCheck extends  CardGoal{

    public static boolean goalCheck(Player player) {       //restituisce punteggio o 404 se ho errore
        int win = 12;
        int check = 0;
        int check2 = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (!(player.getSecretGoal().getLayoutToMatch().get(i, j).isSameType(TileType.NOT_USED))) {
                    check++;
                    if (player.getSecretGoal().getLayoutToMatch().get(i, j) != player.getShelf().get(i, j)) {
                        check2++;
                        if (check2 <= 2)
                            win -= 3;
                        else if (check2 <= 4)
                            win -= 2;
                        else if (check2 == 5)
                            win = 1;
                        else
                            win = 0;
                    }
                }
            }
        }
        if (check != 6) {
            System.out.println("Check error in goalCheck!"); //error
            return false;
        }
        return win == 1;
    }
}
