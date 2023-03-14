package polimi.ingsw.Controller;

import polimi.ingsw.Model.Player;

public class PlayerController {
    private Player player; //Player che gestisce
    private GameController gameController; //Il game dentro al quale gioca
    private MainController mc; //Controller principale

    public PlayerController(MainController mc, Player referred){
        player=referred;
    }

    public void createGame(){
        gameController = mc.createGame(player);
    }

    public void operazione(){
        if(gameController.whoIsPlaying().equals(player)){
            //....
        }
    }

}
