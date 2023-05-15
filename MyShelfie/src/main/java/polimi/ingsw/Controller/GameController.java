package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.*;
import polimi.ingsw.View.networking.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.userView.Flow;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class GameController implements GameControllerInterface, Serializable, Runnable {

    // TODO: FIND A WAY SO THAT THE MODEL IS NOT FINAL IN THE LATEST RELEASE
    //  BECAUSE IT CAN'T BE FINAL FOR TESTING
    //private final GameModel model;
    private GameModel model; // testing
    private final Random random = new Random();
    private Flow view;
    private transient Map<GameListener, Heartbeat> heartbeats;

    /**
     * Init a Controller for one specific game that controls a GameModel
     */
    public GameController() {
        model = new GameModel();
        heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            //checks all the heartbeat to detect disconnection
            for (Map.Entry<GameListener, Heartbeat> entry : heartbeats.entrySet()) {
                if (System.currentTimeMillis() - entry.getValue().getBeat() > DefaultValue.timeout_for_detecting_disconnection) {
                    try {
                        this.setConnectionStatus(entry.getValue().getNick(), entry.getKey(), false);

                        if (this.getNumOnlinePlayers() == 0) {
                            MainController.getInstance().deleteGame(this.getGameId());
                        }

                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Disconnection detected by heartbeat");
                    heartbeats.remove(entry.getKey());


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
     *
     * @return true if player is added and is now in game, false else
     * @throws PlayerAlreadyInException when in the game there is already another Player with the same nickname
     * @throws MaxPlayersInException    when the game has already reached its full capability (#player=4)
     */
    public void addPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        model.addPlayer(p);
    }

    public List<Player> getPlayers() {
        return model.getPlayers();
    }

    public void reconnectPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        model.reconnectPlayer(p);
    }

    /**
     * Returns num of current players that are in the game
     *
     * @return num of current players
     */
    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

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
        CommonCardFactory cfactory = new CommonCardFactory();
        do {
            int extracted = random.nextInt(CardCommonType.values().length);
            try {
                CommonCard ca = cfactory.getCommonCard(CardCommonType.values()[extracted]);
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
        //Creo i punti per la carta
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
        //Estraggo in modo random carte goal per ogni giocatore
        int i = 0;

        do {
            int extracted = random.nextInt(CardGoalType.getValues().size());

            try {
                model.setGoalCard(i, new CardGoal(CardGoalType.getValues().get(extracted)));
                i++;
            } catch (SecretGoalAlreadyGivenException e) {
                //carta goal giá assegnata, non incremento i e riestraggo
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
        model.setCurrentPlaying(random.nextInt(model.getNumOfPlayers()));
    }


    /**
     * Return the list of all the commond cards extracted
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
     * @return Map of <Player,(Secret goal card)>
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

    private boolean isPlayerTheCurrentPlaying(Player p) {
        return whoIsPlaying().equals(p);
    }


    public synchronized void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) {
        if (isPlayerTheCurrentPlaying(model.getPlayerEntity(p))) {
            model.grabTileFromPlayground(model.getPlayerEntity(p), x, y, direction, num);
        }else{
            throw new NotPlayerTurnException();
        }
    }

    public synchronized void positionTileOnShelf(String p, int column, TileType type) throws GameEndedException {
        if (isPlayerTheCurrentPlaying(model.getPlayerEntity(p))) {

            model.positionTileOnShelf(model.getPlayerEntity(p), column, type);

            checkCommonCards(whoIsPlaying());
            if (whoIsPlaying().getShelf().getFreeSpace() == 0 && !model.getStatus().equals(GameStatus.LAST_CIRCLE)) {
                //Il gioco è finito perche ha completato tutta la sua shelf ed è stato il primo
                model.setStatus(GameStatus.LAST_CIRCLE);
                model.setFinishedPlayer(model.getCurrentPlaying());
            }
        } else {
            throw new NotPlayerTurnException();
        }

    }


    @Override
    public synchronized boolean isThisMyTurn(String nick) throws RemoteException {
        return model.getPlayers().get(model.getCurrentPlaying()).getNickname().equals(nick);
    }

    @Override
    public void setConnectionStatus(String nick, GameListener lisOfClient, boolean connected) throws RemoteException {
        if (!connected) {
            //Player has just disconnected, so I remove the notifications for him
            removeListener(lisOfClient, model.getPlayerEntity(nick));

            if (model.getStatus().equals(GameStatus.WAIT)) {
                //The game is in Wait (game not started yet), the player disconnected so I remove him from the game)
                model.removePlayer(nick);
            } else {
                //Tha game is running so I set him as disconnected (He can reconnects soon)
                model.setAsDisconnected(nick);
            }


        } else {
            //Player rejoined
            addListener(lisOfClient, model.getPlayerEntity(nick));
            model.setAsConnected(nick);
        }
    }

    @Override
    public void heartbeat(String nick, GameListener me) throws RemoteException {
        heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), nick));
        //System.out.println("heartbeat rec: "+heartbeats.get(me));
    }

    @Override
    public void sentMessage(Message msg) throws RemoteException {
        model.sentMessage(msg);
    }


    /**
     * Check if the player has completed the shelf, otherwise the turn is passed to the next player
     */
    public synchronized void nextTurn() {
        if (whoIsPlaying().getShelf().getFreeSpace() == 0 && !model.getStatus().equals(GameStatus.LAST_CIRCLE)) {
            //Il gioco è finito perche ha completato tutta la sua shelf ed è stato il primo
            model.setStatus(GameStatus.LAST_CIRCLE);
            model.setFinishedPlayer(model.getCurrentPlaying());
        }
        try {
            model.nextTurn();
        } catch (GameEndedException e) {
            checkGoalCards();
            model.setStatus(GameStatus.ENDED);
        }
    }


    /**
     * Controlla se il player p ha completato una carta comune
     *
     * @param p player
     * @apiNote Ho aggiunto il riferimento al Player p (Perchè ho pensato che il check non si vuole fare sempre su tutti i player)
     */
    private void checkCommonCards(Player p) {
        for (CommonCard card : model.getCommonCards())
            if (card.verify(p.getShelf()) && p.getObtainedPoints().stream()
                    .noneMatch(x -> x.getReferredTo().equals(card.getCommonType()))) {
                    p.addPoint(card.getPoints().poll());
            }
    }


    /**
     * Controlla se il player p ha completato una carta goal
     *
     * @apiNote Ho aggiunto il riferimento al Player p e il metodo getPlayerIndex nella classe model
     */
    private void checkGoalCards() {
        //get the index of the player
        for (int i = 0; i < model.getNumOfPlayers(); i++) {
            Player p = model.getPlayers().get(i);
            CardGoal g = model.getGoalCard(i);
            Point point = g.verify(p.getShelf());
            if (point != null) {
                p.addPoint(point);
            }
        }
    }


    //TODO: needs to check why it returns
    // the correct occurrences + 1 each time, except when there's no occurrences
    //
    // EG: in the tests, I don't use the ACTIVITY tile,
    // so when it checks for ACTIVITY, the algorithm returns 0, correct
    // In every other case, EG the first 7 CAT tiles, it returns 8
    public void checkFinal() {
        boolean allTilesFound;
        int toCheck;
        for (Player p : model.getPlayers()) {
            for (TileType t : TileType.getUsableValues()) {
                allTilesFound = false;
                while (!allTilesFound) {
                    toCheck = checkAdjacent(t, p.getShelf(), 0, 0) - 1;
                    if (toCheck == 3) {
                        p.addPoint(new Point(2));
                    } else if (toCheck == 4) {
                        p.addPoint(new Point(3));
                    } else if (toCheck == 5) {
                        p.addPoint(new Point(5));
                    } else if (toCheck > 5) {
                        p.addPoint(new Point(8));
                    }

                    //checks whether all the tiles with tileType t have been checked
                    allTilesFound = Arrays.stream(p.getShelf().getShelf())
                            .flatMap(Arrays::stream)
                            .noneMatch(x -> x.isSameType(t));
                }
            }
        }
    }

    private int checkAdjacent(TileType typeToCheck, Shelf temp, int r, int c) {
        int res = 0, col, row = 0;
        boolean found = false;

        if (temp.get(r, c).isSameType(typeToCheck)) {
            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            res++;
        } else {
            while (row < DefaultValue.NumOfRowsShelf && !found) {
                col = 0;
                while (col < DefaultValue.NumOfColumnsShelf && !found) {
                    if (temp.get(row, col).isSameType(typeToCheck)) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                        res++;
                        found = true;
                        r = row;
                        c = col;
                    }
                    col++;
                }
                row++;
            }
        }
        switch (r) {
            case (0) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, 0);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            case (DefaultValue.NumOfRowsShelf - 1) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            default -> {
                switch (c) {
                    case (0) -> {
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.get(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.get(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.get(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.get(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                }
            }
        }
    }


    public Player getPlayer(String playerNick) {
        return model.getPlayerEntity(playerNick);
    }

    public Player whoIsPlaying() {
        return model.getPlayers().get(model.getCurrentPlaying());
    }

    public GameStatus getStatus() {
        return model.getStatus();
    }

    public void addListener(GameListener l, Player p) {
        model.addListener(l);
        p.addListener(l);
    }

    public void removeListener(GameListener lis, Player p) {
        model.removeListener(lis);
        p.removeListener(lis);
    }

    @Override
    public int getGameId() {
        return model.getGameId();
    }

    @Override
    public int getNumOnlinePlayers() throws RemoteException {
        return model.getNumOfOnlinePlayers();
    }

    @Override
    public void leave(GameListener lis, String nick) throws RemoteException {
        removeListener(lis, model.getPlayerEntity(nick));
        model.removePlayer(nick);
    }


    //TESTING METHODS
    @Deprecated
    public void setModel(GameModel model) {
        this.model = model;
    }
}
