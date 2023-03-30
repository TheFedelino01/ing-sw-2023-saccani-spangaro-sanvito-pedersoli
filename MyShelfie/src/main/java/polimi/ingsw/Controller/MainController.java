package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;
import polimi.ingsw.Model.Player;

import java.util.ArrayList;
import java.util.List;

//Gestisce tutte le partite in particolare la creazione, il join e il leave
public class MainController {

    //Singleton
    private static MainController instance = null;

    private List<GameController> runningGames;


    private MainController(){
        runningGames = new ArrayList<GameController>();
    }

    public static MainController getInstance(){
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public ControllerAndPlayer createGame(String nick, GameListener lis){
        Player p = new Player(nick);

        GameController c = new GameController();
        runningGames.add(c);
        c.addPlayer(p);

        c.addListener(lis,p);


        return new ControllerAndPlayer(c,p);
    }


    public ControllerAndPlayer joinFirstGameAvailable(String nick, GameListener lis){
        return null;//todo
    }
    public ControllerAndPlayer joinGame(String nick, Integer id, GameListener lis){
        return null;//todo
    }



}
