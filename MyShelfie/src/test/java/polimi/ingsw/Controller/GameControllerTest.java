package polimi.ingsw.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.CardCommon;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.NotReadyToRunException;
import polimi.ingsw.Model.Exceptions.PositioningATailNotGrabbedException;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    GameController gameController;

    List<Player> plist = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        plist.add(new Player("1"));
        plist.add(new Player("2"));
        plist.add(new Player("3"));
        plist.add(new Player("4"));

    }

    @Test
    @DisplayName("Join Player and Start")
    void joinPlayersAndStart() {

        assertTrue(gameController.addPlayer(plist.get(0)),"Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(1)),"Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(2)),"Player not added but needed to");

        assertFalse(gameController.addPlayer(plist.get(0)),"Player added but was already in");
        assertFalse(gameController.addPlayer(plist.get(1)),"Player added but was already in");
        assertFalse(gameController.addPlayer(plist.get(2)),"Player added but was already in");


        assertFalse(gameController.playerIsReadyToStart(plist.get(0)),"Game started but not everyone was ready");
        assertFalse(gameController.playerIsReadyToStart(plist.get(1)),"Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(2)),"Game not started but everyone was ready to start");

        assertTrue(gameController.getCurrentPlaying()>=0 && gameController.getCurrentPlaying()<gameController.getNumOfPlayers(),"Turn index overflow");

        List<CardCommon> risCommon = gameController.getAllCommonCards();


        boolean ris=true;
        for(int i=0; i<risCommon.size(); i++){
            for(int j=i+1; j<risCommon.size();j++){
                if(risCommon.get(i).equals(risCommon.get(j)))
                    ris=false;
            }
        }
        assertTrue(ris,"In Cards Common extracted there are duplicates");

        for(int i=0; i<gameController.getNumOfPlayers(); i++){
            for(int j=i+1; j<gameController.getNumOfPlayers();j++){
                if(gameController.getGoalCard(i).equals(gameController.getGoalCard(j)))
                    ris=false;
            }
        }
        assertTrue(ris,"In Cards Common extracted there are duplicates");

    }

    @Test
    @DisplayName("Grab and Position Tile in a running game")
    void grabAndPositionTile() {

        assertTrue(gameController.addPlayer(plist.get(0)),"Player not added but needed to");
        assertTrue(gameController.addPlayer(plist.get(1)),"Player not added but needed to");

        assertFalse(gameController.playerIsReadyToStart(plist.get(0)),"Game started but not everyone was ready");
        assertTrue(gameController.playerIsReadyToStart(plist.get(1)),"Game not started but everyone was ready to start");


        List<CardCommon> risCommon = gameController.getAllCommonCards();

        int currentPlayer = gameController.getCurrentPlaying();

        List<Tile> grabbed = gameController.grabTailFromPlayground(plist.get(currentPlayer),0,0, Direction.DOWN,1);


        for(int i=0; i<grabbed.size();i++){
            assertTrue(plist.get(currentPlayer).getInHandTail().get(i).isSameType(grabbed.get(i).getTYPE()),"Mismatch between Tile grabbed from playground and Tile in hand");

        }

        assertThrows(PositioningATailNotGrabbedException.class, () -> gameController.positionTailOnShelf(plist.get(currentPlayer),0, TileType.NOT_USED), "Wanted to position a Tail not grabbed");


        for(int i=0; i<grabbed.size();i++) {
            Tile firstTileInHand = plist.get(currentPlayer).getInHandTail().get(0);
            assertTrue(firstTileInHand.isSameType(grabbed.get(i).getTYPE()),"Mismatch between Tile grabbed from playground and Tile in hand");

            gameController.positionTailOnShelf(plist.get(currentPlayer), 0, grabbed.get(i).getTYPE());

            //plist.get(currentPlayer).getShelf().get(0,0); manca implementazione inserimento in shelf
        }


    }

}
