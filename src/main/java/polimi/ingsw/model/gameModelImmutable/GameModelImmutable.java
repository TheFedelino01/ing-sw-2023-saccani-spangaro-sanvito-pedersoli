package polimi.ingsw.model.gameModelImmutable;

import polimi.ingsw.model.*;
import polimi.ingsw.model.chat.Chat;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.interfaces.*;

import java.io.Serializable;
import java.util.*;

/**
 * A different implementation of the GameModel class, this is the one we send to the clients<br>
 * As such, we need to make all the objects in this class immutable, so that the clients<br>
 * cannot modify the course of the game.<br>
 * <br>
 * To do so, a strategy patter was implemented.<br>
 * The pattern consists of implementing for each mutable object two different interfaces, <br>
 * one for the server, one for the client.<br>
 * The server one has no changes from the class it's implemented by<br>
 * the client one, on the other hand, only has getter methods, named differently that the server one,<br>
 * so that the client can only get the object, and doesn't know the names of the setter methods<br>
 */
public class GameModelImmutable implements Serializable {
    private final List<PlayerIC> players;
    private final List<CommonCardIC> commonCards;
    private final Integer gameId;
    private final PlaygroundIC pg;

    private final Integer currentPlaying;

    private final ChatIC chat;

    private final GameStatus status;

    private final Integer indexWonPlayer = -1;
    private final Map<Integer, Integer> leaderBoard;

    /**
     * Constructor
     */
    public GameModelImmutable() {
        players = new ArrayList<>();
        commonCards = new ArrayList<>();
        gameId = -1;

        pg = new Playground();
        leaderBoard = new HashMap<>();
        currentPlaying = -1;
        chat = new Chat();
        status = GameStatus.WAIT;
    }

    /**
     * Constructor
     * @param modelToCopy
     */
    public GameModelImmutable(GameModel modelToCopy) {
        players = new ArrayList<>(modelToCopy.getPlayers());
        commonCards = new ArrayList<>(modelToCopy.getCommonCards());
        gameId = modelToCopy.getGameId();

        pg = modelToCopy.getPg();
        currentPlaying = modelToCopy.getCurrentPlaying();
        chat = modelToCopy.getChat();
        status = modelToCopy.getStatus();
        leaderBoard = modelToCopy.getLeaderBoard();
    }

    /**
     *
     * @return the nickname of the current playing player
     */
    public String getNicknameCurrentPlaying() {
        return players.get(currentPlaying).getNickname();
    }

    /**
     *
     * @return the hand of the current playing player
     */
    public List<TileIC> getHandOfCurrentPlaying() {
        return players.get(currentPlaying).getInHandTile_IC();
    }

    /**
     *
     * @return the winner
     */
    public PlayerIC getWinner() {
        if (indexWonPlayer != -1) {
            return players.get(indexWonPlayer);
        }
        return null;
    }

    /**
     *
     * @return the list of players in game
     */
    public List<PlayerIC> getPlayers() {
        return players;
    }

    /**
     *
     * @return the game's scoreboard
     */
    public List<PlayerIC> getScoreboard(){
        players.sort(Comparator.comparing(PlayerIC::getTotalPoints,Comparator.reverseOrder()));
        return players;
    }

    /**
     *
     * @return the list of the extracted common cards
     */
    public List<CommonCardIC> getCommonCards() {
        return commonCards;
    }

    /**
     *
     * @return the game id
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     *
     * @return the playground
     */
    public PlaygroundIC getPg() {
        return pg;
    }

    /**
     *
     * @return the index of the current playing player
     */
    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    /**
     *
     * @return the game's chat
     */
    public ChatIC getChat() {
        return chat;
    }

    /**
     *
     * @return the game's status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     *
     * @return the first player to finish his shelf
     */
    public Integer getFirstFinishedPlayer() {
        return -1;
    }

    /**
     *
     * @return the index of the winning player
     */
    public Integer getIndexWonPlayer() {
        return indexWonPlayer;
    }

    /**
     *
     * @param playerNick looks for this player in the game
     * @return the player object
     */
    public PlayerIC getPlayerEntity(String playerNick) {
        return players.stream().filter(x -> x.getNickname().equals(playerNick)).toList().get(0);
    }

    /**
     *
     * @return the game's leaderboard
     */
    public Map<Integer, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    /**
     *
     * @param nickname player to check if in turn
     * @return true if is the turn of the player's passed by parameter
     */
    public boolean isMyTurn(String nickname) {
        return players.get(currentPlaying).getNickname().equals(nickname);
    }

    /**
     *
     * @return the list of players in string format
     */
    public String toStringListPlayers() {
        StringBuilder ris = new StringBuilder();
        int i = 1;
        for (PlayerIC p : players) {
            ris.append("[#").append(i).append("]: ").append(p.getNickname()).append("\n");
            i++;
        }
        return ris.toString();
    }

    /**
     *
     * @return the last player in the list of players
     */
    public PlayerIC getLastPlayer() {
        return players.get(players.size() - 1);
    }

    /**
     *
     * @return the last common card
     */
    public CommonCardIC getLastCommonCard() {
        return commonCards.get(commonCards.size() - 1);
    }

    /**
     *
     * @return the playing player
     */
    public PlayerIC getEntityCurrentPlaying() {
        return players.get(currentPlaying);
    }
}
