package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Exceptions.MaxPlayersInException;
import polimi.ingsw.Model.Exceptions.PlayerAlreadyInException;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Gestisce tutte le partite in particolare la creazione, il join e il leave
public class MainController implements MainControllerInterface, Serializable {

    //Singleton
    private static MainController instance = null;

    private List<GameController> runningGames;


    private MainController() {
        runningGames = new ArrayList<GameController>();
    }

    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    @Override
    public synchronized GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        Player p = new Player(nick);


        GameController c = new GameController();
        c.addListener(lis, p);
        runningGames.add(c);

        System.out.println("\t>Game "+c.getGameId()+" added to runningGames, created by player:\""+nick+"\"");
        printRunningGames();

        try {
            c.addPlayer(p);
        } catch (MaxPlayersInException | PlayerAlreadyInException e) {
            throw new RuntimeException(e);
        }

        return c;
    }

    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT) && x.getNumOfPlayers() < DefaultValue.MaxNumOfPlayer)).toList();
        Player p = new Player(nick);
        if (ris.size() > 0) {
            try {
                ris.get(0).addListener(lis, p);
                ris.get(0).addPlayer(p);

                System.out.println("\t>Game "+ris.get(0).getGameId()+" player:\""+nick+"\" entered player");
                printRunningGames();
                return ris.get(0);
            } catch (MaxPlayersInException | PlayerAlreadyInException e) {
                ris.get(0).removeListener(lis, p);
            }
        }else{
            //This is the only call not inside the model
            lis.noGamesAvailableToJoin();
        }
        return null;

    }

    @Override
    public synchronized GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getGameId() == idGame)).toList();
        Player p = new Player(nick);

        if (ris.size() == 1) {
            try {
                ris.get(0).addListener(lis, p);
                ris.get(0).addPlayer(p);
                System.out.println("\t>Game "+ris.get(0).getGameId()+" player:\""+nick+"\" entered player");
                printRunningGames();
                return ris.get(0);
            } catch (MaxPlayersInException | PlayerAlreadyInException e) {
                ris.get(0).removeListener(lis, p);
            }
        } else {
            //This is the only call not inside the model
            lis.gameIdNotExists(idGame);
        }
        return null;

    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getGameId() == idGame)).toList();

        if (ris.size() == 1) {
            try {
                Player player = ris.get(0).getPlayers()
                        .stream()
                        .filter(x -> x.getNickname().equals(nick))
                        .toList().get(0);

                ris.get(0).addListener(lis, player);
                ris.get(0).reconnectPlayer(player);
                return ris.get(0);
            } catch (MaxPlayersInException e) {
                ris.get(0).removeListener(lis, ris.get(0).getPlayers()
                        .stream()
                        .filter(x -> x.getNickname().equals(nick))
                        .toList().get(0));
            }
        } else {
            //This is the only call not inside the model
            lis.gameIdNotExists(idGame);
        }
        return null;
    }

    public void deleteGame(int idGame){
        GameController gameToRemove = runningGames.stream().filter(x->x.getGameId()==idGame).collect(Collectors.toList()).get(0);
        if(gameToRemove!=null) {
            runningGames.remove(gameToRemove);
            System.out.println("\t>Game "+idGame+" removed from runningGames");
            printRunningGames();
        }

    }

    private void printRunningGames(){
        System.out.print("\t\trunningGames: ");
        runningGames.stream().forEach(x->System.out.print(x.getGameId()+" "));
        System.out.println("");
    }


}
