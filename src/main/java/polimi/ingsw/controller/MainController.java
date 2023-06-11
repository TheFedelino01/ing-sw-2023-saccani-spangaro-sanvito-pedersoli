package polimi.ingsw.controller;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.exceptions.GameEndedException;
import polimi.ingsw.model.exceptions.MaxPlayersInException;
import polimi.ingsw.model.exceptions.PlayerAlreadyInException;
import polimi.ingsw.model.Player;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * MainController Class <br>
 * Is the Controller of the controllers, it manages all the available games that are running {@link GameController}<br>
 * Allowing players to create, join, reconnect, leave and delete games
 *<br>
 * Therefore, the MainController is unique across the app and thus implements the Singleton Pattern
 */
public class MainController implements MainControllerInterface, Serializable {

    //Singleton
    /**
     * Singleton Pattern, instance of the class
     */
    private static MainController instance = null;

    /**
     * List of running games
     * For implementing AF: "Multiple games"
     */
    private List<GameController> runningGames;


    /**
     * Init an empty List of GameController
     * For implementing AF: "Multiple games"
     */
    private MainController() {
        runningGames = new ArrayList<>();
    }

    /**
     * Singleton Pattern
     *
     * @return the only one instance of the MainController class
     */
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    /**
     * Create a new game and join to it
     *
     * @param lis GameListener of the player who is creating the game
     * @param nick Nickname of the player who is creating the game
     * @return GameControllerInterface associated to the created game
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        Player p = new Player(nick);


        GameController c = new GameController();
        c.addListener(lis, p);
        runningGames.add(c);

        System.out.println("\t>Game " + c.getGameId() + " added to runningGames, created by player:\"" + nick + "\"");
        printRunningGames();

        try {
            c.addPlayer(p);
        } catch (MaxPlayersInException | PlayerAlreadyInException e) {
            lis.genericErrorWhenEnteringGame(e.getMessage());
        }

        return c;
    }

    /**
     * Join to the first available game
     *
     * @param lis GameListener of the player who is trying to join to a game
     * @param nick Nickname of the player who is trying to join to a game
     * @return GameControllerInterface associated to the game, null if no games are available
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getStatus().equals(GameStatus.WAIT) && x.getNumOfPlayers() < DefaultValue.MaxNumOfPlayer)).toList();
        Player p = new Player(nick);
        if (ris.size() > 0) {
            try {
                ris.get(0).addListener(lis, p);
                ris.get(0).addPlayer(p);

                System.out.println("\t>Game " + ris.get(0).getGameId() + " player:\"" + nick + "\" entered player");
                printRunningGames();
                return ris.get(0);
            } catch (MaxPlayersInException | PlayerAlreadyInException e) {
                ris.get(0).removeListener(lis, p);
                lis.genericErrorWhenEnteringGame(e.getMessage());
            }
        } else {
            //This is the only call not inside the model
            lis.genericErrorWhenEnteringGame("No games currently available to join...");
        }
        return null;

    }

    /**
     * Join to a specific game by @param idGame
     *
     * @param lis GameListener of the player who is trying to join to a specific game by id
     * @param nick Nickname of the player who is trying to join to a specific game by id
     * @param idGame the game ID to be connected
     * @return GameControllerInterface associated to the game, null if the specific game not exists or is unable to let players in
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getGameId() == idGame)).toList();
        Player p = new Player(nick);

        if (ris.size() == 1) {
            //If the game is in wait or if the game is in running and the player is in (so he is not a new player) then let him in
            if(ris.get(0).getStatus().equals(GameStatus.WAIT) ||
                    ((ris.get(0).getStatus().equals(GameStatus.RUNNING) || ris.get(0).getStatus().equals(GameStatus.LAST_CIRCLE)) &&
                            ris.get(0).getPlayers().stream().filter(x->x.getNickname().equals(nick)).toList().size()==1)
            ) {
                try {
                    ris.get(0).addListener(lis, p);
                    ris.get(0).addPlayer(p);
                    System.out.println("\t>Game " + ris.get(0).getGameId() + " player:\"" + nick + "\" entered player");
                    printRunningGames();
                    return ris.get(0);
                } catch (MaxPlayersInException | PlayerAlreadyInException e) {
                    ris.get(0).removeListener(lis, p);
                    lis.genericErrorWhenEnteringGame(e.getMessage());
                }
            }else{
                lis.gameIdNotExists(idGame);
            }
        } else {
            //This is the only call not inside the model
            lis.gameIdNotExists(idGame);
        }
        return null;

    }


    /**
     * Reconnect a player to a game @param idGame
     *
     * @param lis GameListener of the player who is trying to rejoin to a game
     * @param nick Nickname of the player who is trying to rejoin to a game
     * @param idGame the game ID to be reconnected
     * @return GameControllerInterface associated to the game, null if the game not exists or is unable to let players in
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> (x.getGameId() == idGame)).toList();
        List<Player> players = new ArrayList<>();
        if (ris.size() == 1) {
            try {
                players = ris.get(0).getPlayers()
                        .stream()
                        .filter(x -> x.getNickname().equals(nick))
                        .toList();
                //The game exists, check if nickname exists
                if (players.size() == 1) {
                    ris.get(0).addListener(lis, players.get(0));
                    ris.get(0).reconnectPlayer(players.get(0));
                    return ris.get(0);
                } else {
                    //Game exists but the nick no
                    lis.genericErrorWhenEnteringGame("The nickname used was not connected in a running game");
                    return null;
                }

            } catch (MaxPlayersInException e) {
                ris.get(0).removeListener(lis, players.get(0));
                lis.genericErrorWhenEnteringGame(e.getMessage());
            } catch (GameEndedException e) {
                throw new RuntimeException(e);
            }
        } else {
            //This is the only call not inside the model
            lis.gameIdNotExists(idGame);
        }
        return null;
    }

    /**
     * Leave a player from a game
     *
     * @param lis GameListener of the player who wants to leave
     * @param nick Nickname of the player who wants to leave
     * @param idGame the game ID to leave
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x -> x.getGameId() == idGame).toList();
        if (ris.size() == 1) {
            ris.get(0).leave(lis, nick);
            System.out.println("\t>Game " + ris.get(0).getGameId() + " player: \"" + nick + "\" decided to leave");
            printRunningGames();

            if (ris.get(0).getNumOfOnlinePlayers() == 0) {
                deleteGame(idGame);
            }
        }
        return null;
    }


    /**
     * Remove the @param idGame from the {@link MainController#runningGames}
     *
     * @param idGame Game ID to delete
     */
    public synchronized void deleteGame(int idGame) {
        List<GameController> gameToRemove = runningGames.stream().filter(x -> x.getGameId() == idGame).toList();

        if (gameToRemove != null && gameToRemove.size()>0) {
            runningGames.remove(gameToRemove.get(0));

            System.out.println("\t>Game " + idGame + " removed from runningGames");
            printRunningGames();
        }

    }

    /**
     * Print all games currently running
     */
    private void printRunningGames() {
        System.out.print("\t\trunningGames: ");
        runningGames.stream().forEach(x -> System.out.print(x.getGameId() + " "));
        System.out.println();
    }


}
