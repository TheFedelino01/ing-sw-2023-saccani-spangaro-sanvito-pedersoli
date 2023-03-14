package polimi.ingsw.Controller;

import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModel;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.View;

public class GameController {
    private GameModel model;
    private View view;



    public GameController(){
        model = new GameModel();

    }

    public Boolean addPlayer(Player p){
        return false;
    }
    public void removePlayer(Player p){

    }


    public void start(){

    }
    private void extractCard(){

    }
    private void turnDefiner(){

    }

    private void setPlayGround(){
        //Imposta il layout di gioco in base al #giocatori
    }
    private void fillPlayGround(){
        //Riempie di tile il playground
    }

    private Boolean needToRefillPlayground(){
        return false;
    }

    public void grabTailFromPlayground(Player p, int X, int Y, int direction, int num){
        //p.setInHandTail(model.getPg().getBoard());
    }

    public Boolean positionTailOnShelf(Player p, int collum, TileType tipo){
        //p.getInHandTail().contains()
        return false;
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
        return model.getPlayers().get(model.getCurrentPlaying());
    }
    


}
