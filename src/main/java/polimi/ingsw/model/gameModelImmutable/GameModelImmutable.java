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

    public String getNicknameCurrentPlaying() {
        return players.get(currentPlaying).getNickname();
    }

    public List<TileIC> getHandOfCurrentPlaying() {
        return players.get(currentPlaying).getInHandTile_IC();
    }

    public PlayerIC getWinner() {
        if (indexWonPlayer != -1) {
            return players.get(indexWonPlayer);
        }
        return null;
    }

    public List<PlayerIC> getPlayers() {
        return players;
    }

    public List<PlayerIC> getScoreboard(){
        players.sort(Comparator.comparing(PlayerIC::getTotalPoints,Comparator.reverseOrder()));
        return players;
    }


    public List<CommonCardIC> getCommonCards() {
        return commonCards;
    }

    public Integer getGameId() {
        return gameId;
    }

    public PlaygroundIC getPg() {
        return pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public ChatIC getChat() {
        return chat;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Integer getFirstFinishedPlayer() {
        return -1;
    }

    public Integer getIndexWonPlayer() {
        return indexWonPlayer;
    }

    public PlayerIC getPlayerEntity(String playerNick) {
        return players.stream().filter(x -> x.getNickname().equals(playerNick)).toList().get(0);
    }

    public Map<Integer, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public boolean isMyTurn(String nickname) {
        return players.get(currentPlaying).getNickname().equals(nickname);
    }

    public String toStringListPlayers() {
        StringBuilder ris = new StringBuilder();
        int i = 1;
        for (PlayerIC p : players) {
            ris.append("[#").append(i).append("]: ").append(p.getNickname()).append("\n");
            i++;
        }
        return ris.toString();
    }

    public PlayerIC getLastPlayer() {
        return players.get(players.size() - 1);
    }

    public CommonCardIC getLastCommonCard() {
        return commonCards.get(commonCards.size() - 1);
    }

    public PlayerIC getEntityCurrentPlaying() {
        return players.get(currentPlaying);
    }
}
