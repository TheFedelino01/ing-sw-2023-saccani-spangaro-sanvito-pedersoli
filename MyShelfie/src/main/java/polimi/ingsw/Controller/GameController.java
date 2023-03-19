package polimi.ingsw.Controller;

import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.*;
import polimi.ingsw.View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    private final GameModel model;
    private final Random random = new Random();
    private View view;


    public GameController() {
        model = new GameModel();

    }

    public Boolean addPlayer(Player p) {
        try {
            model.addPlayer(p);
            return true;
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            return false;
        }
    }


    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

    public CardGoal getGoalCard(int indexPlayer) {
        return model.getGoalCard(indexPlayer);
    }

    public boolean playerIsReadyToStart(Player p) {
        //La partita parte automaticamente quando tutti i giocatori sono pronti
        model.playerIsReadyToStart(p);

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


    private void extractCommonCards() {
        //Estraggo in modo random 'DefaultValue.NumOfCommonCards' carte comuni
        do {
            Integer extracted = random.nextInt(CardCommonType.values().length);
            try {
                model.addCommonCard(new CardCommon(CardCommonType.values()[extracted]));
            } catch (MaxCommonCardsAddedException e) {
                throw new RuntimeException(e);
            } catch (CommonCardAlreadyInException e) {
                //non viene aggiunta la carta comune e continuo a sorteggiare
            }

        } while (model.getNumOfCommonCards() < DefaultValue.NumOfCommonCards);
    }

    private void extractGoalCards() {
        //Estraggo in modo random carte goal per ogni giocatore
        int i = 0;

        do {
            Integer extracted = random.nextInt(CardGoalType.values().length);

            try {
                model.setGoalCard(i, new CardGoal(CardGoalType.values()[extracted]));
                i++;
            } catch (SecretGoalAlreadyGivenException e) {
                //carta goal giá assegnata, non incremento i e riestraggo
            }

        } while (i < model.getNumOfPlayers());
    }

    private void setPlaygroundLayout() {
        int numOfPlayers = model.getNumOfPlayers();
        //todo fare model.setPg(); con il layout corretto

    }

    private void extractFirstTurn() {
        model.setCurrentPlaying(random.nextInt(model.getNumOfPlayers()));
    }

    public List<CardCommon> getAllCommonCards() {
        List<CardCommon> ris = new ArrayList<>();
        for (int i = 0; i < model.getNumOfCommonCards(); i++)
            ris.add(model.getCommonCard(i));

        return ris;
    }

    public Map<Player, CardGoal> getAllGoalCards() {
        return model.getGoalCards();
    }

    public int getCurrentPlaying() {
        return model.getCurrentPlaying();
    }


    public List<Tile> grabTailFromPlayground(Player p, int x, int y, Direction direction, int num) {
        //p.setInHandTail(model.getPg().getBoard());
        return model.grabTailFromPlayground(p, x, y, direction, num);
    }

    public void positionTailOnShelf(Player p, int collum, TileType tipo) {
        model.positionTailOnShelf(p, collum, tipo);
    }


    /**
     * Controlla se il player p ha completato una carta comune
     *
     * @param p player
     * @apiNote Ho aggiunto il riferimento al Player p (Perchè ho pensato che il check non si vuole fare sempre su tutti i player)
     */
    private void checkCommonCards(Player p) {
        //controlla tutte le carte comuni
        for (int i = 0; i < DefaultValue.NumOfCommonCards; i++)
            if (model.getCommonCard(i).verify(p.getShelf())) {
                //Aggiungo i punti al player p e li tolgo dalla coda della carta comune
                Point point = model.getCommonCard(i).getPoints().remove();
                p.getObtainedPoints().add(point);
            }
    }


    /**
     * Controlla se il player p ha completato una carta goal
     *
     * @param p player
     * @apiNote Ho aggiunto il riferimento al Player p e il metodo getPlayerIndex nella classe model
     */
    private void checkGoalCards(Player p) {
        //get the index of the player
        int index = model.getPlayerIndex(p);
        if (model.doAllPlayersHaveGoalCard()) {
            CardGoal g = model.getGoalCard(index);
            Point point = g.verify(p.getShelf());
            if (point != null) {
                p.getObtainedPoints().add(point);
            }
        }
    }

    //TODO test

    /**
     * Controllo chi tra i vari player ha piú punti
     *
     * @return Player con piú punti
     * @apiNote Ho cambiato il tipo di ritorno da void a Player
     */
    //TODO Aggiungere un comparatore di Point
    private Player findWinner() {
        Player winner = null;
        int max = 0;
        //Cycle between every player point and return the one with more point
        for (int i = 0; i < model.getNumOfPlayers(); i++) {
            Integer point = model.getPlayer(i).getObtainedPoints().stream().map(Point::getPoint).reduce(0, Integer::sum);
            if (point > max) {
                max = point;
                winner = model.getPlayer(i);
            }

        }
        //TODO: Caso player con stessi punti
        return winner;
    }

    public void nextTurn() {
        model.nextTurn();
    }

    private void end() {
        //TODO: aggiungere uno status END
        model.setStatus(GameStatus.STOPPED);
    }


    public Player whoIsPlaying() {
        //return model.getPlayers().get(model.getCurrentPlaying());
        return null;
    }

}
