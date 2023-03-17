package polimi.ingsw.Controller;

import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.*;
import polimi.ingsw.Model.Exceptions.*;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    private GameModel model;
    private View view;

    private Random random = new Random();


    public GameController(){
        model = new GameModel();

    }

    public Boolean addPlayer(Player p){
        try {
            model.addPlayer(p);
            return true;
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            return false;
        }
    }


    public int getNumOfPlayers(){
        return model.getNumOfPlayers();
    }

    public CardGoal getGoalCard(int indexPlayer){
        return model.getGoalCard(indexPlayer);
    }

    public boolean playerIsReadyToStart(Player p){
        //La partita parte automaticamente quando tutti i giocatori sono pronti
        model.playerIsReadyToStart(p);

        if(model.arePlayersReadyToStartAndEnough()){
            extractCommonCards();
            extractGoalCards();
            setPlaygroundLayout();
            extractFirstTurn();
            model.setStatus(GameStatus.RUNNING);
            return true;
        }

        return false;//Game non started yet
    }



    private void extractCommonCards(){
        //Estraggo in modo random 'DefaultValue.NumOfCommonCards' carte comuni
        do{
            Integer extracted = random.nextInt(CardCommonType.values().length);
                try {
                    model.addCommonCard(new CardCommon(CardCommonType.values()[extracted]) );
                } catch (MaxCommonCardsAddedException e) {
                    throw new RuntimeException(e);
                } catch (CommonCardAlreadyInException e) {
                    //non viene aggiunta la carta comune e continuo a sorteggiare
                }

        }while(model.getNumOfCommonCards()< DefaultValue.NumOfCommonCards);
    }

    private void extractGoalCards(){
        //Estraggo in modo random carte goal per ogni giocatore
        int i=0;

        do{
            Integer extracted = random.nextInt(CardGoalType.values().length);

            try {
                model.setGoalCard(i, new CardGoal(CardGoalType.values()[extracted]));
                i++;
            } catch (SecretGoalAlreadyGivenException e) {
                //carta goal giá assegnata, non incremento i e riestraggo
            }

        }while(i< model.getNumOfPlayers());
    }

    private void setPlaygroundLayout(){
        int numOfPlayers = model.getNumOfPlayers();
        //todo fare model.setPg(); con il layout corretto

    }

    private void extractFirstTurn(){
        model.setCurrentPlaying(random.nextInt(model.getNumOfPlayers()));
    }

    public List<CardCommon> getAllCommonCards(){
        List<CardCommon> ris = new ArrayList<>();
        for(int i=0; i<model.getNumOfCommonCards();i++)
            ris.add(model.getCommonCard(i));

        return ris;
    }
    public Map<Player, CardGoal> getAllGoalCards(){
        return model.getGoalCards();
    }

    public int getCurrentPlaying(){
        return model.getCurrentPlaying();
    }


    public List<Tile> grabTailFromPlayground(Player p, int x, int y, Direction direction, int num){
        //p.setInHandTail(model.getPg().getBoard());
        return model.grabTailFromPlayground(p, x,y,direction,num);
    }

    public void positionTailOnShelf(Player p, int collum, TileType tipo){
        model.positionTailOnShelf(p,collum,tipo);
    }


    private void checkCommonCards(){

    }

    private void checkGoalCards(){

    }


    private void findWinner(){

    }

    public void nextTurn(){

    }

    private void end(){

    }


    public Player whoIsPlaying(){
        //return model.getPlayers().get(model.getCurrentPlaying());
        return null;
    }
    


}
