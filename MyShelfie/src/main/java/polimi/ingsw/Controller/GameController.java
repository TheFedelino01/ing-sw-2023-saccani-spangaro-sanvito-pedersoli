package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonCardFactory;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.*;
import polimi.ingsw.View.RMI.ClientResponsesInterface;
import polimi.ingsw.View.View;

import java.util.*;

public class GameController implements ClientResponsesInterface {
    private final GameModel model;
    private final Random random = new Random();
    private View view;


    /**
     * Init a Controller for one specific game that controls a GameModel
     */
    public GameController() {
        model = new GameModel();

    }

    /**
     * Add player @param p to the Game
     *
     * @exception  PlayerAlreadyInException when in the game there is already another Player with the same nickname
     * @exception MaxPlayersInException when the game has already reached its full capability (#player=4)
     * @return true if player is added and is now in game, false else
     */
    public Boolean addPlayer(Player p) {
        try {
            model.addPlayer(p);
            return true;
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            return false;
        }
    }

    /**
     * Returns num of current players that are in the game
     *
     * @return num of current players
     */
    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
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

    /**
     * The Common Cards (Default 2) are extracted pseudo-randomly between all the enum of CardCommonType
     * and associated to the game (no duplicates)
     *
     * @exception RuntimeException when MaxCommonCardsAddedException is thrown
     */
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

    /**
     * Get the list containing the points that a @param card will distribute when satisfied
     *
     * @param card the card which point will be added
     * @return the list of points to add to the @param card
     */
    private Queue<Point> getListPointForCommonCard(CommonCard card){
        //Creo i punti per la carta
        Queue<Point> ris = new ArrayDeque<Point>();
        for(int i=0; i<DefaultValue.pointsValue.length;i++)
            ris.add(new Point(DefaultValue.pointsValue[i],card.getCommonType()));

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


    public void addListener(GameListener l, Player p){
        model.addListener(l);
        p.addListener(l);
    }
}
