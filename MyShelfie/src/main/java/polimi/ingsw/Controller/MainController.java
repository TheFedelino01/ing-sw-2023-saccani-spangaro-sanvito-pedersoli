package polimi.ingsw.Controller;

import polimi.ingsw.Model.Player;

import java.util.List;

//Gestisce tutte le partite in particolare la creazione, il join e il leave
public class MainController {

    //Singleton
    private static MainController instance = null;

    private List<GameController> runningGames;


    private MainController(){

    }

    public static MainController getInstance(){
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public GameController createGame(Player p){
        GameController c = new GameController();
        c.addPlayer(p);

        return c;
    }

    public GameController joinFirstGameAvailable(Player p){
        return null;//todo
    }
    public GameController joinGame(Player p, Integer id){
        return null;//todo
    }

    public void leaveGame(Player p){

    }

}
