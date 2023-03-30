package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.ControllerAndPlayer;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Exceptions.NotAvailableGamesException;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.RMI.ClientRequestsInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

//Gestisce tutte le partite in particolare la creazione, il join e il leave
public class MainController implements ClientRequestsInterface, Serializable {

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




    @Override
    public ControllerAndPlayer createGame(GameListener lis, String nick) throws RemoteException {
        Player p = new Player(nick);


        GameController c = new GameController();
        c.addListener(lis,p);
        runningGames.add(c);
        c.addPlayer(p);

        return new ControllerAndPlayer(c,p);
    }

    @Override
    public ControllerAndPlayer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {

        int firstIndexAvailable=-1;
        for(int i=0; i<runningGames.size();i++){
            if(runningGames.get(i).getNumOfPlayers()< DefaultValue.MaxNumOfPlayer)
                firstIndexAvailable=i;
        }

        if(firstIndexAvailable!=-1){
            Player p = new Player(nick);
            runningGames.get(firstIndexAvailable).addListener(lis,p);
            boolean ris = runningGames.get(firstIndexAvailable).addPlayer(p);
            if(!ris) {
                //runningGames.get(firstIndexAvailable).removeListener(lis,p);
                return null;
            }else{
                return new ControllerAndPlayer(runningGames.get(firstIndexAvailable),p);
            }

        }
        return null;

    }

    @Override
    public ControllerAndPlayer joinGame(GameListener lis, String nick, Integer idGame) throws RemoteException {
        return null;
    }
}
