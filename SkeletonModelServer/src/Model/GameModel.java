package Model;

import Model.Enumeration.CardCommon;

import java.util.List;

public class GameModel {
    private List<Player> players;
    private List<CardCommon> commonCards;
    private Integer gameId;
    private Playground pg;

    private Integer currentPlaying;

    public GameModel(List<Player> players, List<CardCommon> commonCards, Integer gameId, Playground pg) {
        this.players = players;
        this.commonCards = commonCards;
        this.gameId = gameId;
        this.pg = pg;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<CardCommon> getCommonCards() {
        return commonCards;
    }

    public void setCommonCards(List<CardCommon> commonCards) {
        this.commonCards = commonCards;
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
}
