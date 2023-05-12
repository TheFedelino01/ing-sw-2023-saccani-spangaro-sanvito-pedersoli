package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Controller.GameController;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Cards.Common.CommonStair;
import polimi.ingsw.Model.Cards.Common.CommonXCard;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.CommonCardAlreadyInException;
import polimi.ingsw.Model.Exceptions.MaxCommonCardsAddedException;
import polimi.ingsw.Model.Exceptions.SecretGoalAlreadyGivenException;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
    Player p1;
    Player p2;

    @BeforeEach
    void setUp() {
        //Set up the player point
        p1 = new Player("Test");
        p2 = new Player("Test2");
    }

    @Test
    @DisplayName("Test player points")
    void testPlayerPoints() {
        //Test the player points
        Point point = new Point(0, CardCommonType.CommonX);
        p1.addPoint(point);
        assertEquals(0, p1.getTotalPoints());
        p2.addPoint(point);
        assertEquals(0, p2.getTotalPoints());

    }

    @Test
    @DisplayName("Check who wins?")
    void testWhoWins() {
        //Check who wins
        Point point = new Point(0, CardCommonType.CommonX);
        p1.addPoint(point);
        p2.addPoint(point);
        assertEquals(0, p1.getTotalPoints());
        assertEquals(0, p2.getTotalPoints());
        int x = p1.getTotalPoints();
        int y = p2.getTotalPoints();
        if (x > y) {
            System.out.println("Player 1 wins!");
        } else if (x < y) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Draw!");
        }
    }

    /**
     * The first player will have set a shelf that
     * makes him get all the points for common card X,
     * The second player will also have set the same shelf,
     * but the test will do the checking in order, so that
     * I'll be able to see if the first player gets 8 points and the second 6, as it should
     */
    @Test
    @DisplayName("Test common cards checks")
    void commonCardsPoints() throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //game setup
        GameModel game = new GameModel();
        GameController controller = new GameController();
        Queue<Point> pointsX = new ArrayDeque<>();
        Queue<Point> pointsStair = new ArrayDeque<>();
        for(int i = 4; i > 0; i--)
            pointsX.add(new Point(i*2, CardCommonType.CommonX));
        for(int i = 4; i > 0; i--)
            pointsStair.add(new Point(i*2, CardCommonType.CommonStair));


        //add players
        Player test1 = new Player("p1");
        Player test2 = new Player("p2");
        Player test3 = new Player("p3");
        game.addPlayer(test1);
        game.addPlayer(test2);
        game.addPlayer(test3);

        game.setCurrentPlaying(game.getPlayerIndex(test2));
        //add common cards
        CommonCard c1 = new CommonXCard(CardCommonType.CommonX);
        c1.setPoints(pointsX);
        CommonCard c2 = new CommonStair(CardCommonType.CommonStair);
        c2.setPoints(pointsStair);
        game.addCommonCard(c1);
        game.addCommonCard(c2);

        test1.setSecretGoal(new CardGoal(CardGoalType.getValues().get(game.getPlayerIndex(test1))));
        test2.setSecretGoal(new CardGoal(CardGoalType.getValues().get(game.getPlayerIndex(test2))));
        test3.setSecretGoal(new CardGoal(CardGoalType.getValues().get(game.getPlayerIndex(test3))));

        Shelf p1Shelf = new Shelf();
        Shelf p2Shelf = new Shelf();
        Shelf p3Shelf = new Shelf();

        test2.setShelf(new Shelf());
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((i == 0 && j == 0) ||
                    (i == 0 && j == 2) ||
                    (i == 2 && j == 0) ||
                    (i == 2 && j == 2) ||
                    (i == 1 && j == 1)){
                    p1Shelf.setSingleTile(new Tile(TileType.CAT), i, j);
                    p2Shelf.setSingleTile(new Tile(TileType.CAT), i, j);
                }
                else{
                    p1Shelf.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    p2Shelf.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                }
            }
        }

        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (i >= j) {
                    if (i == j) {
                        p3Shelf.setSingleTile(new Tile(TileType.CAT), i, j);
                    } else {
                        p3Shelf.setSingleTile(new Tile(TileType.randomTileCAT()), i, j);
                    }
                }
            }
        }

        test1.setShelf(p1Shelf);
        game.setCurrentPlaying(game.getPlayerIndex(test1));
        controller.setModel(game);
        game.setStatus(GameStatus.RUNNING);
        controller.nextTurn();

        //first player that completes the common goal gets 8 points
        assertEquals(8, test1.getTotalPoints());

        //second player that completes the common goal gets 6 points
        test2.setShelf(p2Shelf);
        controller.nextTurn();
        assertEquals(6, test2.getTotalPoints());

        test3.setShelf(p3Shelf);
        controller.nextTurn();
        assertEquals(8, test3.getTotalPoints());
    }
}
