package polimi.ingsw.controller;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.cards.common.CommonCardFactory;
import polimi.ingsw.model.cards.common.CommonMethods;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.*;
import polimi.ingsw.model.enumeration.*;
import polimi.ingsw.model.exceptions.*;
import polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;


/**
 * GameController Class <br>
 * Controls a specific Game {@link GameModel} by allowing a player to perform all actions that can be executed in a game
 * The class can add, remove, reconnect and disconnects players to the game and let players grab and position tiles
 * from the playground to the shelf. <br>
 * <br>
 * It manages all the game from the beginning {@link GameStatus#WAIT} to the ending {@link GameStatus#ENDED}
 * checking CommonCards, GoalCards and FinalChecks.
 */
public class GameController implements GameControllerInterface, Serializable, Runnable {

    /**
     * The {@link GameModel} to control
     */
    private GameModel model;

    /**
     * A random object for implementing pseudo-random choice
     */
    private final Random random = new Random();

    /**
     * Map of heartbeats for detecting disconnections
     * For implementing AF: "Clients disconnections"
     */
    private final transient Map<GameListener, Heartbeat> heartbeats;
    /**
     * Timer started when only one player is playing
     * it ends the game if no one reconnects within {@link DefaultValue#secondsToWaitReconnection} seconds
     */
    private Thread reconnectionTh;

    /**
     * Init a GameModel and starts thread for detecting disconnections by heartbeats
     */
    public GameController() {
        model = new GameModel();
        heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    /**
     * It detects disconnections by heartbeats
     * When it detects one, set player as disconnected and eventually delete the game
     */
    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //checks all the heartbeat to detect disconnection
            if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE) || model.getStatus().equals(GameStatus.ENDED) || model.getStatus().equals(GameStatus.WAIT)) {
                synchronized (heartbeats) {
                    Iterator<Map.Entry<GameListener, Heartbeat>> heartIter = heartbeats.entrySet().iterator();

                    while (heartIter.hasNext()) {
                        Map.Entry<GameListener, Heartbeat> el = (Map.Entry<GameListener, Heartbeat>) heartIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > DefaultValue.timeout_for_detecting_disconnection) {
                            try {
                                this.disconnectPlayer(el.getValue().getNick(), el.getKey());
                                System.out.println("Disconnection detected by heartbeat of player: "+el.getValue().getNick());

                                if (this.getNumOnlinePlayers() == 0) {
                                    stopReconnectionTimer();
                                    MainController.getInstance().deleteGame(this.getGameId());
                                }

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }

                            heartIter.remove();
                        }
                    }
                }
            }
            try {

                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Add player @param p to the Game
     * <br>
     *
     * @throws PlayerAlreadyInException when in the game there is already another Player with the same nickname
     * @throws MaxPlayersInException    when the game has already reached its full capability (#player={@link DefaultValue#MaxNumOfPlayer})
     */
    public void addPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        model.addPlayer(p);
    }

    /**
     * @return the list of the players currently playing (online and offline)
     */
    public List<Player> getPlayers() {
        return model.getPlayers();
    }

    /**
     * Reconnect the player with the nickname @param to the game
     *
     * @param p Player that want to reconnect
     * @throws PlayerAlreadyInException if a player tries to rejoin the same game
     * @throws MaxPlayersInException    if there are already 4 players in game
     * @throws GameEndedException       the game is ended
     */
    public void reconnectPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException, GameEndedException {
        boolean outputres = model.reconnectPlayer(p);

        if (outputres && getNumOfOnlinePlayers() > 1) {
            stopReconnectionTimer();
        }
        //else nobody was connected and now one player has reconnected before the timer expires
    }

    /**
     * Returns num of current players that are in the game (online and offline)
     *
     * @return num of current players
     */
    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

    /**
     * @return the number of online players that are in the game
     */
    public int getNumOfOnlinePlayers() {
        return model.getNumOfOnlinePlayers();
    }

    /**
     * Return the secret Goal Card associated with the player in index @param indexPlayer
     *
     * @param indexPlayer the index of the player to return his secret goal card
     * @return CardGoal associated to the player
     */
    public CardGoal getGoalCard(int indexPlayer) {
        return model.getGoalCard(indexPlayer);
    }

    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     *
     * @param p Player to set has ready
     * @return true if the game has started, false else
     */
    public synchronized boolean playerIsReadyToStart(String p) {
        //La partita parte automaticamente quando tutti i giocatori sono pronti
        model.playerIsReadyToStart(model.getPlayerEntity(p));

        if (model.arePlayersReadyToStartAndEnough()) {
            extractCommonCards();
            extractGoalCards();
            setPlaygroundLayout();
            extractFirstTurn();
            model.setStatus(GameStatus.RUNNING);
            return true;
        }

        return false;//Game non started yet
    }

    /**
     * The Common Cards (Default 2) are extracted pseudo-randomly between all the enum of CardCommonType
     * and associated to the game (no duplicates)
     *
     * @throws RuntimeException when MaxCommonCardsAddedException is thrown
     */
    private void extractCommonCards() {
        //Estraggo in modo random 'DefaultValue.NumOfCommonCards' carte comuni
        do {
            int extracted = random.nextInt(CardCommonType.values().length);
            try {
                CommonCard ca = CommonCardFactory.getCommonCard(CardCommonType.values()[extracted]);
                model.addCommonCard(ca);//Aggiungo la card al model
                //Se la card che ho aggiunto va bene, gli imposto i punti
                ca.setPoints(getListPointForCommonCard(ca));


            } catch (MaxCommonCardsAddedException e) {
                throw new RuntimeException(e);
            } catch (CommonCardAlreadyInException e) {
                //non viene aggiunta la carta comune e continuo a sorteggiare
            }

        } while (model.getNumOfCommonCards() < DefaultValue.NumOfCommonCards);

    }

    /**
     * Get the list containing the points that a @param card will distribute when satisfied
     *
     * @param card the card which point will be added
     * @return the list of points to add to the @param card
     */
    private Queue<Point> getListPointForCommonCard(CommonCard card) {
        //Creates the points for the card
        Queue<Point> ris = new ArrayDeque<>();
        for (int i = 0; i < DefaultValue.pointsValue.length; i++)
            ris.add(new Point(DefaultValue.pointsValue[i], card.getCommonType()));

        return ris;
    }


    /**
     * The Goal Cards are extracted pseudo-randomly between all the enum of GoalType
     * and associated specifically one to one player (no duplicates)
     */
    private void extractGoalCards() {
        //Extract pseudo-randomly the goal cards for the players
        int i = 0;

        do {
            int extracted = random.nextInt(CardGoalType.getValues().size());

            try {
                model.setGoalCard(i, new CardGoal(CardGoalType.getValues().get(extracted)));
                i++;
            } catch (SecretGoalAlreadyGivenException e) {
                //goal card already given
            }

        } while (i < model.getNumOfPlayers());
    }


    /**
     * Set the playground layout according to the num of players playing
     */
    private void setPlaygroundLayout() {
        int numOfPlayers = model.getNumOfPlayers();
        model.setPg(new Playground(numOfPlayers));
    }

    /**
     * Extract pseudo-randomly the player who has the first move (first turn)
     */
    private void extractFirstTurn() {
        int ris = random.nextInt(model.getNumOfPlayers());
        model.setFirstTurnIndex(ris);
        model.setCurrentPlaying(ris);
    }


    /**
     * Return the list of all the common cards extracted
     *
     * @return list of all the common cards of the game
     */
    public List<CommonCard> getAllCommonCards() {
        List<CommonCard> ris = new ArrayList<>();
        for (int i = 0; i < model.getNumOfCommonCards(); i++)
            ris.add(model.getCommonCard(i));

        return ris;
    }


    /**
     * Return the list of all the goal cards extracted associated with the players
     *
     * @return Map of {Player,(Secret goal card)}
     */
    public Map<Player, CardGoal> getAllGoalCards() {
        return model.getGoalCards();
    }


    /**
     * Return the index of the player who is currently playing the turn
     *
     * @return index of the player who is moving
     */
    public int getIndexCurrentPlaying() {
        return model.getCurrentPlaying();
    }

    /**
     * Return the player who is currently playing the turn
     *
     * @param p the player that you want to know if is the current playing
     * @return true if the player is the current playing, false else
     */
    private boolean isPlayerTheCurrentPlaying(Player p) {
        return whoIsPlaying().equals(p);
    }

    /**
     * Given the coordinates of a tile, the direction and the number of tiles, the player can grab the tiles
     *
     * @param p         the nickname of the player
     * @param x         the x coordinate of the tile
     * @param y         the y coordinate of the tile
     * @param direction the direction of the tile
     * @param num       the number of tiles you want to get
     */
    public synchronized void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) {
        if (isPlayerTheCurrentPlaying(model.getPlayerEntity(p))) {
            model.grabTileFromPlayground(model.getPlayerEntity(p), x, y, direction, num);
        } else {
            throw new NotPlayerTurnException();
        }
    }

    /**
     * Position a tile on the shelf of the player
     * if the player has positioned all of his tiles, {@link GameModel#nextTurn()}  will be called
     * it detects {@link GameStatus#LAST_CIRCLE} and it calls {@link GameStatus#ENDED}
     *
     * @param p      the nickname of the player
     * @param column the column where you want to position the tile
     * @param type   the type of the tile
     * @throws NotPlayerTurnException when a player wants to position tiles and, it's not his turn
     */
    public synchronized void positionTileOnShelf(String p, int column, TileType type) {
        if (isPlayerTheCurrentPlaying(model.getPlayerEntity(p))) {

            Player currentPlaying = this.whoIsPlaying();//Because position can call nextTurn
            int currentPlayingIndex = this.getIndexCurrentPlaying();

            try {
                model.positionTileOnShelf(model.getPlayerEntity(p), column, type);

                checkCommonCards(currentPlaying);


                if (currentPlaying.getShelf().getFreeSpace() == 0 && (!model.getStatus().equals(GameStatus.LAST_CIRCLE) && !model.getStatus().equals(GameStatus.ENDED))) {
                    //This player has his shelf full, time to complete le last circle
                    model.setStatus(GameStatus.LAST_CIRCLE);
                    model.setFinishedPlayer(currentPlayingIndex);
                    currentPlaying.addPoint(new Point(true),model);
                }

                //if the hand is empty then call next turn
                if (currentPlaying.getInHandTile().size() == 0) {
                    model.nextTurn();
                }

            } catch (GameEndedException e) {
                //Time to check for personal goal and final
                checkGoalCards();
                checkFinal();
                model.setStatus(GameStatus.ENDED);
            }

        } else {
            throw new NotPlayerTurnException();
        }

    }


    /**
     * Check if it's your turn
     *
     * @param nick the nickname of the player
     * @return true if it's your turn, false else
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public synchronized boolean isThisMyTurn(String nick) throws RemoteException {
        return model.getPlayers().get(model.getCurrentPlaying()).getNickname().equals(nick);
    }

    /**
     * Disconnect the player, if the game is in {@link GameStatus#WAIT} status, the player is removed from the game
     * If only one player is connected, a timer of {@link DefaultValue#secondsToWaitReconnection} will be started
     *
     * @param nick        the nickname of the player
     * @param lisOfClient the listener of the client
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException {

        //Player has just disconnected, so I remove the notifications for him
        Player p = model.getPlayerEntity(nick);
        if(p!=null) {
            removeListener(lisOfClient, p);

            if (model.getStatus().equals(GameStatus.WAIT)) {
                //The game is in Wait (game not started yet), the player disconnected, so I remove him from the game)
                model.removePlayer(nick);
            } else {
                //Tha game is running, so I set him as disconnected (He can reconnect soon)
                model.setAsDisconnected(nick);
            }

            //Check if there is only one player playing
            if ((model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)) && model.getNumOfOnlinePlayers() == 1) {
                //Starting a th for waiting until reconnection at least of 1 client to keep playing
                if (reconnectionTh == null) {
                    startReconnectionTimer();
                    System.out.println("Starting timer for reconnection waiting: " + DefaultValue.secondsToWaitReconnection + " seconds");
                }
            }
        }


    }

    /**
     * Starts a timer for detecting the reconnection of a player, if no one reconnects in time, the game is over
     */
    @SuppressWarnings("BusyWait")
    private void startReconnectionTimer() {
        reconnectionTh = new Thread(
                () -> {
                    long startingtimer = System.currentTimeMillis();

                    while (reconnectionTh != null && !reconnectionTh.isInterrupted() && System.currentTimeMillis() - startingtimer < DefaultValue.secondsToWaitReconnection * 1000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            //Someone called interrupt on this th (no need to keep waiting)
                        }
                    }
                    System.out.println("Timer for reconnection ended");

                    if (model.getNumOfOnlinePlayers() == 0) {
                        //No players online, I delete the games
                        MainController.getInstance().deleteGame(model.getGameId());
                    } else if (model.getNumOfOnlinePlayers() == 1) {
                        System.out.println("\tNo player reconnected on time, set game to ended!");
                        model.setStatus(GameStatus.ENDED);
                    } else {
                        System.out.println("\tA player reconnected on time");
                        this.reconnectionTh = null;
                    }
                }

        );
        reconnectionTh.start();
    }

    /**
     * It stops the timer (if started) that checks for reconnections of players
     */
    private void stopReconnectionTimer() {
        if (reconnectionTh != null) {
            reconnectionTh.interrupt();
            reconnectionTh = null;
        }
        //else It means that a player reconnected but the timer was not started (ex 3 players and 1 disconnects)
    }

    /**
     * Add a hearthbeat to the list of heartbeats
     *
     * @param nick the player's nickname associated to the heartbeat
     * @param me   the player's GameListener associated to the heartbeat
     * @throws RemoteException
     */
    @Override
    public synchronized void heartbeat(String nick, GameListener me) throws RemoteException {
        synchronized (heartbeats) {
            heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), nick));
        }
        //System.out.println("heartbeat rec: "+heartbeats.get(me));
    }

    /**
     * Add a message to the chat list
     *
     * @param msg to add
     * @throws RemoteException
     */
    @Override
    public synchronized void sentMessage(Message msg) throws RemoteException {
        model.sentMessage(msg);
    }


    /**
     * Check if a player @param achieved a common card,
     * called every time a player position a tile
     *
     * @param p player
     */
    private void checkCommonCards(Player p) {
        for (CommonCard card : model.getCommonCards()) {
            if (card.verify(p.getShelf()) && p.getObtainedPoints().stream()
                    .noneMatch(x -> x.getReferredTo().equals(card.getCommonType()))) {
                p.addPoint(card.getPoints().poll(), model);
            }
        }
    }


    /**
     * Check if a player @param achieved his goal card,
     * called only when game ends
     */
    private void checkGoalCards() {
        //get the index of the player
        for (int i = 0; i < model.getNumOfPlayers(); i++) {
            Player p = model.getPlayers().get(i);
            CardGoal g = model.getGoalCard(i);
            Point point = (Point) g.verify(p.getShelf());
            if (point != null) {
                p.addPoint(point, model);
            }
        }
    }

    /**
     * Check final adjacent tiles for all the players,
     * called only when game ends
     */
    public void checkFinal() {
        boolean allTilesFound;
        int toCheck;
        for (Player p : model.getPlayers()) {
            for (TileType t : TileType.getUsableValues()) {
                allTilesFound = false;
                while (!allTilesFound) {
                    toCheck = CommonMethods.checkAdjacent(t, p.getShelf(), 0, 0);
                    if (toCheck == 3) {
                        p.addPoint(new Point(2), model);
                    } else if (toCheck == 4) {
                        p.addPoint(new Point(3), model);
                    } else if (toCheck == 5) {
                        p.addPoint(new Point(5), model);
                    } else if (toCheck > 5) {
                        p.addPoint(new Point(8), model);
                    }

                    //checks whether all the tiles with tileType t have been checked
                    allTilesFound = Arrays.stream(p.getShelf().getShelf())
                            .flatMap(Arrays::stream)
                            .noneMatch(x -> x.isSameType(t));
                }
            }
        }
    }


    /**
     * Return the entity of the player associated with the nickname @param
     *
     * @param playerNick
     * @return the player by nickname @param
     */
    public Player getPlayer(String playerNick) {
        return model.getPlayerEntity(playerNick);
    }

    /**
     * Return the entity of the player who is playing (it's his turn)
     *
     * @return the player who is playing the turn
     */
    public Player whoIsPlaying() {
        return model.getPlayers().get(model.getCurrentPlaying());
    }

    /**
     * Return the {@link GameStatus} of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

    /**
     * Add listener @param l to model listeners and player listeners
     *
     * @param l GameListener to add
     * @param p entity of the player
     */
    public void addListener(GameListener l, Player p) {
        model.addListener(l);
        for (GameListener othersListener : model.getListeners()) {
            p.addListener(othersListener);
        }
        for (Player otherPlayer : model.getPlayers()) {
            if (!otherPlayer.equals(p)) {
                otherPlayer.addListener(l);
            }
        }
    }

    /**
     * Remove the listener @param lis to model listeners and player listeners
     *
     * @param lis GameListener to remove
     * @param p   entity of the player to remove
     */
    public void removeListener(GameListener lis, Player p) {
        model.removeListener(lis);

        p.getListeners().clear();

        for (Player otherPlayer : model.getPlayers()) {
            if (!otherPlayer.equals(p)) {
                otherPlayer.removeListener(lis);
            }
        }
    }

    /**
     * @return the ID of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }

    /**
     * @return the number of online players
     * @throws RemoteException
     */
    @Override
    public int getNumOnlinePlayers() throws RemoteException {
        return model.getNumOfOnlinePlayers();
    }

    /**
     * It removes a player by nickname @param nick from the game including the associated listeners
     *
     * @param lis  GameListener to remove
     * @param nick of the player to remove
     * @throws RemoteException
     */
    @Override
    public synchronized void leave(GameListener lis, String nick) throws RemoteException {
        removeListener(lis, model.getPlayerEntity(nick));
        model.removePlayer(nick);
    }


    //TESTING METHODS

    /**
     * Can set a model
     * used for testing purposes only, not used (and should not be used) elsewhere
     *
     * @param model
     */
    @Deprecated
    public void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * Return the playground of the game
     * used for testing purposes only, not used (and should not be used) elsewhere
     */
    @Deprecated
    public Playground getPlayground() {
        return model.getPg();
    }

}
