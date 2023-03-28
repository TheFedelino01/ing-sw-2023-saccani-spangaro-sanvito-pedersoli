package polimi.ingsw.Controller;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.*;
import polimi.ingsw.View.View;

import java.util.*;

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
        CommonCardFactory cfactory = new CommonCardFactory();
        do {
            Integer extracted = random.nextInt(CardCommonType.values().length);
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
    private Queue<Point> getListPointForCommonCard(CommonCard card){
        //Creo i punti per la carta
        Queue<Point> ris = new ArrayDeque<Point>();
        for(int i=0; i<DefaultValue.pointsValue.length;i++)
            ris.add(new Point(DefaultValue.pointsValue[i],card.getCommonType()));

        return ris;
    }

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

    private void setPlaygroundLayout() {
        int numOfPlayers = model.getNumOfPlayers();
        model.setPg(new Playground(numOfPlayers));

    }

    private void extractFirstTurn() {
        model.setCurrentPlaying(random.nextInt(model.getNumOfPlayers()));
    }

    public List<CommonCard> getAllCommonCards() {
        List<CommonCard> ris = new ArrayList<>();
        for (int i = 0; i < model.getNumOfCommonCards(); i++)
            ris.add(model.getCommonCard(i));

        return ris;
    }

    public Map<Player, CardGoal> getAllGoalCards() {
        return model.getGoalCards();
    }

    public int getIndexCurrentPlaying() {
        return model.getCurrentPlaying();
    }

    private boolean isPlayerTheCurrentPlaying(Player p){
        return whoIsPlaying().equals(p);
    }


    public void grabTileFromPlayground(Player p, int x, int y, Direction direction, int num) {
        if(isPlayerTheCurrentPlaying(p)){
            model.grabTileFromPlayground(p, x, y, direction, num);
        }else{
            throw new NotPlayerTurnException();
        }

    }

    public void positionTileOnShelf(Player p, int column, TileType type) {
        if(isPlayerTheCurrentPlaying(p)){
            model.positionTileOnShelf(p, column, type);
        }else{
            throw new NotPlayerTurnException();
        }

    }

    public void nextTurn() {
        checkCommonCards(whoIsPlaying());

        if(whoIsPlaying().getShelf().getFreeSpace()==0 && !model.getStatus().equals(GameStatus.LAST_CIRCLE)){
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
        //controlla tutte le carte comuni
        for (int i = 0; i < DefaultValue.NumOfCommonCards; i++)
            if (model.getCommonCard(i).verify(p.getShelf())) {
                //Aggiungo i punti al player p e li tolgo dalla coda della carta comune
                try {
                    p.addPoint(model.getCommonCard(i).getPoints().peek());

                    model.getCommonCard(i).getPoints().remove();//Non ha sollevato eccezione quindi rimuovo il punto

                }catch(IllegalArgumentException e){
                    //Punto gia' aggiunto non posso riaggiungerlo
                }

            }
    }


    /**
     * Controlla se il player p ha completato una carta goal
     *
     * @apiNote Ho aggiunto il riferimento al Player p e il metodo getPlayerIndex nella classe model
     */
    private void checkGoalCards() {
        //get the index of the player
        for(int i=0; i<model.getNumOfPlayers(); i++){
            Player p = model.getPlayer(i);
            CardGoal g = model.getGoalCard(i);
            Point point = g.verify(p.getShelf());
            if (point != null) {
                p.addPoint(point);
            }
        }


    }



    public Player whoIsPlaying() {
        return model.getPlayer(model.getCurrentPlaying());
    }

}
