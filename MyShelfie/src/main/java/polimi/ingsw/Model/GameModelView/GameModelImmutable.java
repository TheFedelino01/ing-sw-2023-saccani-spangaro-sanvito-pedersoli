package polimi.ingsw.Model.GameModelView;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Listener.ListenersHandler;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.*;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class GameModelImmutable implements Serializable {
    private final List<Player> players;
    private final List<CommonCard> commonCards;
    private final Integer gameId;
    private final Playground pg;

    private final Integer currentPlaying;

    private final Chat chat;

    private final GameStatus status;

    private final Integer firstFinishedPlayer = -1;

    private final Integer indexWonPlayer = -1;


    public GameModelImmutable(GameModel modelToCopy) {
        players = modelToCopy.getPlayers();
        commonCards = modelToCopy.getCommonCards();
        gameId = modelToCopy.getGameId();

        pg = modelToCopy.getPg();
        currentPlaying = modelToCopy.getCurrentPlaying();
        chat = modelToCopy.getChat();
        status = modelToCopy.getStatus();
    }

    public String getNicknameCurrentPlaying(){
        return players.get(currentPlaying).getNickname();
    }
    public List<Tile> getHandOfCurrentPlaying(){
        return players.get(currentPlaying).getInHandTile();
    }
    public Player getWinner(){
        if(indexWonPlayer!=-1) {
            return players.get(indexWonPlayer);
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<CommonCard> getCommonCards() {
        return commonCards;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Playground getPg() {
        return pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public Chat getChat() {
        return chat;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Integer getFirstFinishedPlayer() {
        return firstFinishedPlayer;
    }

    public Integer getIndexWonPlayer() {
        return indexWonPlayer;
    }

    public Player getPlayerEntity(String playerNick) {
        return players.stream().filter(x->x.getNickname().equals(playerNick)).collect(Collectors.toList()).get(0);
    }

    public boolean isMyTurn(String nickname){
        return players.get(currentPlaying).equals(nickname);
    }

}
