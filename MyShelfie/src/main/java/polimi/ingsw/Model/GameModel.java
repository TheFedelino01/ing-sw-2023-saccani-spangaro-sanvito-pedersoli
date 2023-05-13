package polimi.ingsw.Model;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Listener.ListenersHandler;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.*;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.util.*;
import java.util.stream.Collectors;

public class GameModel {
    //maps the indexes of the players in the list with their position on the scoreBoard
    //1,3 means the first player came in third place
    private static Map<Integer, Integer> leaderBoard;
    private final List<Player> players;
    private final List<CommonCard> commonCards;
    private Integer gameId;
    private Playground pg;
    private Integer currentPlaying;

    private Chat chat;

    private GameStatus status;

    private Integer firstFinishedPlayer = -1;

    private Integer indexWonPlayer = -1;

    private transient ListenersHandler listenersHandler;


    public GameModel() {
        players = new ArrayList<>();
        commonCards = new ArrayList<>();

        Random random = new Random();
        gameId = random.nextInt(10000000);

        pg = new Playground();
        currentPlaying = -1;
        chat = new Chat();
        status = GameStatus.WAIT;

        chat = new Chat();

        listenersHandler = new ListenersHandler();


    }

    public GameModel(List<Player> players, List<CommonCard> commonCards, Integer gameId, Playground pg) {
        this.players = players;
        this.commonCards = commonCards;
        this.gameId = gameId;
        this.pg = pg;
    }

    @Serial
    private Object readResolve() throws ObjectStreamException {
        listenersHandler = new ListenersHandler();
        return this;
    }

    public int getNumOfPlayers() {
        return players.size();
    }

    public int getNumOfOnlinePlayers() {
        return players.stream().filter(Player::isConnected).toList().size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * add a player to the game
     *
     * @param p player to add
     * @throws PlayerAlreadyInException if the player is already in the game
     * @throws MaxPlayersInException    if the game is full
     */
    public void addPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        //Verifico per prima cosa che il player non è gia' presente
        //poi se non vado in overflow
        if (players.stream()
                .noneMatch(x -> x.equals(p))) {
            if (players.size() + 1 <= DefaultValue.MaxNumOfPlayer) {
                players.add(p);
                listenersHandler.notify_playerJoined(this);
            } else {
                listenersHandler.notify_JoinUnableGameFull(p, this);
                throw new MaxPlayersInException();
            }
        } else {
            listenersHandler.notify_JoinUnableNicknameAlreadyIn(p);
            throw new PlayerAlreadyInException();
        }

    }

    public void removePlayer(String nick) {
        players.remove(players.stream().filter(x -> x.getNickname().equals(nick)).collect(Collectors.toList()).get(0));
        listenersHandler.notify_playerLeft(this, nick);

        if (this.status.equals(GameStatus.RUNNING) && players.stream().filter(x -> x.isConnected()).collect(Collectors.toList()).size() <= 1) {
            //No enough players to keep playing
            this.setStatus(GameStatus.ENDED);
        }
    }

    public void reconnectPlayer(Player p) throws PlayerAlreadyInException, MaxPlayersInException {
        Player pIn = players.stream().filter(x -> x.equals(p)).toList().get(0);

        if (!pIn.isConnected()) {
            pIn.setConnected(true);
            listenersHandler.notify_playerReconnected(this, p.getNickname());
            //listenersHandler.notify_playerJoined(this);
        } else {
            System.out.println("ERROR: Trying to reconnect a player not offline!");
        }
    }


    public void playerIsReadyToStart(Player p) {
        p.setReadyToStart();
        listenersHandler.notify_PlayerIsReadyToStart(this, p.getNickname());
    }

    public boolean arePlayersReadyToStartAndEnough() {
        //Se tutti i giocatori sono pronti a giocare, inizia il game
        return players.stream().filter(Player::getReadyToStart)
                       .count() == players.size() && players.size() >= DefaultValue.minNumOfPlayer;
    }


    public int getNumOfCommonCards() {
        return commonCards.size();
    }


    public void addCommonCard(CommonCard c) throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //Si verifica per prima cosa se la carta e' gia' presente
        //se non e' gia' presente, verifico se si va in overflow

        if (commonCards.stream().noneMatch(x -> x.isSameType(c))) {
            if (commonCards.size() + 1 <= DefaultValue.NumOfCommonCards) {
                commonCards.add(c);
                listenersHandler.notify_extractedCommonCard(this);
            } else {
                throw new MaxCommonCardsAddedException();
            }
        } else {
            throw new CommonCardAlreadyInException();
        }

    }

    public void setGoalCard(int indexPlayer, CardGoal c) throws SecretGoalAlreadyGivenException {
        if (indexPlayer < players.size() && indexPlayer >= 0) {
            //Assegno la carta goal solo se non ce l'ha nessun altro player
            if (players.stream().noneMatch(x -> (x.getSecretGoal().isSameType(c)))) {
                players.get(indexPlayer).setSecretGoal(c);
            } else {
                throw new SecretGoalAlreadyGivenException();
            }
        } else {
            throw new IndexPlayerOutOfBoundException();
        }

    }

    public CommonCard getCommonCard(int i) {
        return commonCards.get(i);
    }

    public CardGoal getGoalCard(int indexPlayer) {
        return players.get(indexPlayer).getSecretGoal();
    }


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Playground getPg() {
        return pg;
    }

    public void setPg(Playground pg) {
        this.pg = pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public void setCurrentPlaying(Integer currentPlaying) {
        this.currentPlaying = currentPlaying;
    }

    public Chat getChat() {
        return chat;
    }


    //Player p, String txt
    public void sentMessage(Message m) {
        if (players.stream().filter(x -> x.equals(m.getSender())).count() == 1) {
            chat.addMsg(m);
            listenersHandler.notify_SentMessage(this, chat.getLastMessage());
        } else {
            throw new ActionPerformedByAPlayerNotPlayingException();
        }

    }


    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        //Se voglio settare a Running il game, ci devono essere almeno 'DefaultValue.minNumOfPlayer' players
        if (status.equals(GameStatus.RUNNING) &&
            ((players.size() < DefaultValue.minNumOfPlayer
              || getNumOfCommonCards() != DefaultValue.NumOfCommonCards
              || !doAllPlayersHaveGoalCard())
             || currentPlaying == -1)) {
            throw new NotReadyToRunException();
        } else {
            this.status = status;


            if (status == GameStatus.RUNNING) {
                listenersHandler.notify_GameStarted(this);
                listenersHandler.notify_nextTurn(this);
            } else if (status == GameStatus.ENDED) {
                findWinner(); //Trovo il vincitore
                listenersHandler.notify_GameEnded(this);
            }
        }
    }

    public Map<Player, CardGoal> getGoalCards() {
        Map<Player, CardGoal> ris = new HashMap<>();

        for (Player p : players) {
            ris.put(p, p.getSecretGoal());
        }
        return ris;
    }

    public List<CommonCard> getCommonCards() {
        return commonCards;
    }

    public boolean doAllPlayersHaveGoalCard() {
        for (Player p : players) {
            if (p.getSecretGoal().getGoalType().equals(CardGoalType.NOT_SET))
                return false;
        }
        return true;
    }

    public void grabTileFromPlayground(Player p, int x, int y, Direction direction, int num) {
        List<Tile> ris;
        try {
            ris = pg.grabTile(x, y, direction, num);

            //if the player grabbed a valid set of tile (only if all of them had at least 1 side free)
            p.setInHandTile(ris);
            listenersHandler.notify_grabbedTile(this);

        } catch (TileGrabbedNotCorrectException e) {
            //Player grabbed a set of not valid tile (there was at least 1 tile with no free side)
            listenersHandler.notify_grabbedTileNotCorrect(this);
        }

    }

    public void positionTileOnShelf(Player p, int column, TileType type) throws GameEndedException {
        Tile t = popInHandTilePlayer(p, type);
        if (t != null) {
            p.getShelf().position(column, type);
            listenersHandler.notify_positionedTile(this, type, column);
            //if the hand is empty then call next turn
            if (p.getInHandTile().size() == 0) {

                nextTurn();
            }
        } else {
            throw new PositioningATileNotGrabbedException();
        }

    }

    private Tile popInHandTilePlayer(Player p, TileType tipo) {
        for (int i = 0; i < p.getInHandTile().size(); i++) {
            if (p.getInHandTile().get(i).isSameType(tipo)) {
                return p.getInHandTile().remove(i);
            }
        }
        return null;//Il player non ha questa Tile tra quelle estratte
    }


    public void nextTurn() throws GameEndedException {
        if (status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_CIRCLE)) {
            if (players.get(currentPlaying).getInHandTile().size() == 0) {
                currentPlaying = (currentPlaying + 1) % players.size();
                if (currentPlaying.equals(firstFinishedPlayer)) {
                    throw new GameEndedException();
                } else {
                    listenersHandler.notify_nextTurn(this);
                }
            } else {
                throw new NotEmptyHandException();
            }
        } else if (status.equals(GameStatus.ENDED)){
            throw new GameEndedException();
        } else {
            throw new GameNotStartedException();
        }

    }

    public void setFinishedPlayer(Integer indexPlayer) {
        firstFinishedPlayer = indexPlayer;
    }


    public int getPlayerIndex(Player p) {
        return players.indexOf(p);
    }

    /**
     * Controllo chi tra i vari player ha piú punti
     * Ritorna il Player con piú punti
     */

    private void findWinner() {
        int max = -1;
        int winnerIndex = -1;
        int point;
        leaderBoard = new HashMap<>();
        Map<Integer, Integer> temp = new HashMap<>();
        //Cycle between every player point and return the one with more point
        for (int i = 0; i < getNumOfPlayers(); i++) {
            point = getPlayers().get(i).getTotalPoints();
            if (point >= max) {
                temp.put(i, point);
                max = point;
                winnerIndex = i;

            }
        }

        //sorts temp and puts it into the leaderboard in order
        leaderBoard = temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        indexWonPlayer = winnerIndex;

    }


    public void addListener(GameListener obj) {
        listenersHandler.addListener(obj);
    }


    public void removeListener(GameListener lis) {
        listenersHandler.removeListener(lis);
    }

    public Player getPlayerEntity(String playerNick) {
        return players.stream().filter(x -> x.getNickname().equals(playerNick)).collect(Collectors.toList()).get(0);
    }


    public Map<Integer, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public void setAsDisconnected(String nick) {
        getPlayerEntity(nick).setConnected(false);
        getPlayerEntity(nick).setNotReadyToStart();
        listenersHandler.notify_playerDisconnected(this, nick);

        //Check if there are at least 2 players to keep playing (if the game is running)
        if (this.status.equals(GameStatus.RUNNING) && players.stream().filter(x -> x.isConnected()).collect(Collectors.toList()).size() <= 1) {
            //No enough players
            this.setStatus(GameStatus.ENDED);
        }

    }


    public void setAsConnected(String nick) {
        getPlayerEntity(nick).setConnected(true);
        listenersHandler.notify_playerReconnected(this, nick);
    }


}
