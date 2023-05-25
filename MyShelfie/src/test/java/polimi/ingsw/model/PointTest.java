package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.controller.GameController;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.cards.common.CommonStair;
import polimi.ingsw.model.cards.common.CommonXCard;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.enumeration.*;
import polimi.ingsw.model.exceptions.CommonCardAlreadyInException;
import polimi.ingsw.model.exceptions.MaxCommonCardsAddedException;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PointTest {
    Player p1;
    Player p2;

    @BeforeEach
    void setUp() {
        //Set up the player point
        p1 = new Player("Test");
        p2 = new Player("Test2");
    }

    /**
     * The first player will have set a shelf that
     * makes him get all the points for common card X,
     * The second player will also have that same shelf,
     * but the test will do the checking in order, so that
     * I'll be able to see if the first player gets 8 points and the second 6, as it should
     * <br>
     * <br>
     * The third player, on the other end, completes the other common goal, granting him 8 points
     * <br>
     * <br>
     * This method tests two random common goal cards, bit since the code is the same for every card, and
     * every card verify was previously tested, if these two work then all the others should follow
     */
    @Disabled
    @Test
    @DisplayName("Test common cards points assignment")
    void commonCardsPoints() throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //game setup
        GameModel game = new GameModel();
        GameController controller = new GameController();
        Queue<Point> pointsX = new ArrayDeque<>();
        Queue<Point> pointsStair = new ArrayDeque<>();
        for (int i = 4; i > 0; i--)
            pointsX.add(new Point(i * 2, CardCommonType.CommonX));
        for (int i = 4; i > 0; i--)
            pointsStair.add(new Point(i * 2, CardCommonType.CommonStair));


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
                    (i == 1 && j == 1)) {
                    p1Shelf.setSingleTile(new Tile(TileType.CAT), i, j);
                    p2Shelf.setSingleTile(new Tile(TileType.CAT), i, j);
                } else {
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

    @Test
    @DisplayName("Test goal cards points assignment")
    void goalCardsPoints() throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //game setup
        GameModel game = new GameModel();
        GameController controller = new GameController();
        Queue<Point> pointsX = new ArrayDeque<>();
        Queue<Point> pointsStair = new ArrayDeque<>();
        for (int i = 4; i > 0; i--)
            pointsX.add(new Point(i * 2, CardCommonType.CommonX));
        for (int i = 4; i > 0; i--)
            pointsStair.add(new Point(i * 2, CardCommonType.CommonStair));


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

        test1.setSecretGoal(new CardGoal(CardGoalType.GOAL1));
        test2.setSecretGoal(new CardGoal(CardGoalType.GOAL2));
        test3.setSecretGoal(new CardGoal(CardGoalType.GOAL3));

        Shelf p1Shelf = setUpShelf(CardGoalType.GOAL1);
        Shelf p2Shelf = setUpShelf(CardGoalType.GOAL2);
        Shelf p3Shelf = setUpShelf(CardGoalType.GOAL3);

        test1.setShelf(p1Shelf);
        test2.setShelf(p2Shelf);
        test3.setShelf(p3Shelf);

        game.setCurrentPlaying(game.getPlayerIndex(test1));
        game.setStatus(GameStatus.RUNNING);
        controller.setModel(game);
        game.setStatus(GameStatus.ENDED);
        //apparently the personal goals are checked only if an exception is thrown...
        // PECULIAR design
        controller.nextTurn();
        assertEquals(12, test1.getTotalPoints());
        assertEquals(12, test2.getTotalPoints());
        assertEquals(12, test3.getTotalPoints());
    }

    @Disabled
    @Test
    @DisplayName("prova")
    private Shelf setUpShelf(CardGoalType type){
        switch (type) {
            case GOAL0 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.PLANT), 0, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 1, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 3, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 5, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL1 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.PLANT), 1, 1);
                ret.setSingleTile(new Tile(TileType.CAT), 2, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 2, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 4, 3);
                ret.setSingleTile(new Tile(TileType.FRAME), 5, 4);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL2 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.FRAME), 1, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 1, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 2, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 3, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL3 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 0, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 2, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 2, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 3, 3);
                ret.setSingleTile(new Tile(TileType.BOOK), 4, 1);
                ret.setSingleTile(new Tile(TileType.CAT), 4, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL4 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.TROPHY), 1, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 3, 1);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 4, 4);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 5, 0);
                ret.setSingleTile(new Tile(TileType.CAT), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL5 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.TROPHY), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 0, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 4, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL6 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.CAT), 0, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 1, 3);
                ret.setSingleTile(new Tile(TileType.PLANT), 2, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 0);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 4);
                ret.setSingleTile(new Tile(TileType.BOOK), 5, 2);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL7 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.FRAME), 0, 4);
                ret.setSingleTile(new Tile(TileType.CAT), 1, 1);
                ret.setSingleTile(new Tile(TileType.TROPHY), 2, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 3, 0);
                ret.setSingleTile(new Tile(TileType.BOOK), 4, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL8 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 0, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 2, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 3, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 4, 1);
                ret.setSingleTile(new Tile(TileType.PLANT), 4, 4);
                ret.setSingleTile(new Tile(TileType.FRAME), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL9 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.TROPHY), 0, 4);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 1, 1);
                ret.setSingleTile(new Tile(TileType.BOOK), 2, 0);
                ret.setSingleTile(new Tile(TileType.CAT), 3, 3);
                ret.setSingleTile(new Tile(TileType.FRAME), 4, 1);
                ret.setSingleTile(new Tile(TileType.PLANT), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL10 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.PLANT), 0, 2);
                ret.setSingleTile(new Tile(TileType.BOOK), 1, 1);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 2, 0);
                ret.setSingleTile(new Tile(TileType.FRAME), 3, 2);
                ret.setSingleTile(new Tile(TileType.CAT), 4, 4);
                ret.setSingleTile(new Tile(TileType.TROPHY), 5, 3);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            case GOAL11 -> {
                Shelf ret = new Shelf();
                ret.setSingleTile(new Tile(TileType.BOOK), 0, 2);
                ret.setSingleTile(new Tile(TileType.PLANT), 1, 1);
                ret.setSingleTile(new Tile(TileType.FRAME), 2, 2);
                ret.setSingleTile(new Tile(TileType.TROPHY), 3, 3);
                ret.setSingleTile(new Tile(TileType.ACTIVITY), 4, 4);
                ret.setSingleTile(new Tile(TileType.CAT), 5, 0);
                for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++)
                    for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++)
                        if (ret.get(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            default -> {
                return new Shelf();
            }
        }
    }

    /**
     * Method checks if the game controller assigns correctly the points to the
     * players, after the final checks were done
     */

    @Test
    @DisplayName("Test final checks points assignment")
    void finalChecks() throws MaxCommonCardsAddedException, CommonCardAlreadyInException {
        //game setup
        GameModel game = new GameModel();
        GameController controller = new GameController();
        Queue<Point> pointsX = new ArrayDeque<>();
        Queue<Point> pointsStair = new ArrayDeque<>();
        for (int i = 4; i > 0; i--)
            pointsX.add(new Point(i * 2, CardCommonType.CommonX));
        for (int i = 4; i > 0; i--)
            pointsStair.add(new Point(i * 2, CardCommonType.CommonStair));


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
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0) {
                    p1Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if (r == DefaultValue.NumOfRowsShelf - 1) {
                    p1Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                } else if (r == 3) {
                    p1Shelf.setSingleTile(new Tile(TileType.FRAME), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.FRAME), r, c);
                } else if (r == 1 && c < 2) {
                    p1Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if (r == 1) {
                    p1Shelf.setSingleTile(new Tile(TileType.BOOK), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.BOOK), r, c);
                } else if (r == 2 && c < 2) {
                    p1Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                } else if (r == 2) {
                    p1Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                } else if (c < 2) {
                    p1Shelf.setSingleTile(new Tile(TileType.PLANT), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.PLANT), r, c);
                } else {
                    p1Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                    p2Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                }
            }
        }

        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                if (r == 0)
                    p3Shelf.setSingleTile(new Tile(TileType.NOT_USED), r, c);
                else if (r == DefaultValue.NumOfRowsShelf - 1)
                    p3Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 3)
                    p3Shelf.setSingleTile(new Tile(TileType.FRAME), r, c);
                else if (r == 1 && c < 2)
                    p3Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (r == 1)
                    p3Shelf.setSingleTile(new Tile(TileType.BOOK), r, c);
                else if (r == 2 && c < 2)
                    p3Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
                else if (r == 2)
                    p3Shelf.setSingleTile(new Tile(TileType.CAT), r, c);
                else if (c < 2){
                    p3Shelf.setSingleTile(new Tile(TileType.PLANT), r, c);
                }else p3Shelf.setSingleTile(new Tile(TileType.TROPHY), r, c);
            }
        }

        test1.setShelf(p1Shelf);
        test2.setShelf(p2Shelf);
        test3.setShelf(p3Shelf);
        game.setCurrentPlaying(game.getPlayerIndex(test1));
        controller.setModel(game);

        //checks the final points in the shelves
        controller.checkFinal();
        //first and second player have a shelf that grants them 25 points (see game controller test)
        assertEquals(25, test1.getTotalPoints());
        assertEquals(25, test2.getTotalPoints());
        //third player has a shelf that only grants him 17 points
        assertEquals(17, test3.getTotalPoints());
    }
    @Test
    @DisplayName("Test getPoint()")
    void testGetPoint() {
        int expectedPoint = 5;
        CardCommonType referredTo = CardCommonType.CommonX;

        Point point = new Point(expectedPoint, referredTo);

        int actualPoint = point.getPoint();

        assertEquals(expectedPoint, actualPoint, "Point value does not match");
    }

    @Test
    @DisplayName("Test getReferredTo()")
    void testGetReferredTo() {
        CardCommonType expectedReferredTo = CardCommonType.CommonX;

        Point point = new Point(8, expectedReferredTo);

        CardType actualReferredTo = point.getReferredTo();

        assertEquals(expectedReferredTo, actualReferredTo, "ReferredTo card type does not match");
    }

    @Test
    @DisplayName("Test setPoint()")
    void testSetPoint() {
        Point point = new Point(5);
        int expectedPoint = 8;

        point.setPoint(expectedPoint);

        int actualPoint = point.getPoint();

        assertEquals(expectedPoint, actualPoint, "Point value does not match after setting");
    }

    @Test
    @DisplayName("Test setReferredTo()")
    void testSetReferredTo() {
        Point point = new Point(5);
        CardGoalType expectedReferredTo = CardGoalType.GOAL0;

        point.setReferredTo(expectedReferredTo);

        CardType actualReferredTo = point.getReferredTo();

        assertEquals(expectedReferredTo, actualReferredTo, "ReferredTo card type does not match after setting");
    }


}
