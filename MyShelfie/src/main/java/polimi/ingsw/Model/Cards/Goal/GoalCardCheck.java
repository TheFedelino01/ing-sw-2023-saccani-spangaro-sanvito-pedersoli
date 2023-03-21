package polimi.ingsw.Model.Cards.Goal;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

public class GoalCardCheck extends  CardGoal{

    private static Player player;
    private final static Tile confront = new Tile(TileType.NOT_USED);

    public static int goalCheck(Player player) {       //restituisce punteggio o 404 se ho errore
        int win = 12;
        int check = 0;
        int check2 = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (player.getSecretGoal().getLayoutToMatch().get(i, j) != confront) {
                    check = check + 1;
                    if (player.getSecretGoal().getLayoutToMatch().get(i, j) != player.getShelf().get(i, j)) {
                        check2 = check2 + 1;
                        if (check2 <= 2)
                            win = win - 3;
                        else if (check2 <= 4)
                            win = win - 2;
                        else if (check2 == 5)
                            win = 1;
                        else
                            win = 0;
                    }
                }
            }
        }
        if (check != 6)
            return 404; //errore
        return win;
    }
}
