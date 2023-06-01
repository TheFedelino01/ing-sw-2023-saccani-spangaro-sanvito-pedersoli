package polimi.ingsw.model.cards.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.*;
import polimi.ingsw.model.cards.goal.CardGoal;
import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalsTests {

    List<CardGoal> cards = new ArrayList<>();

    /**
     * Legend:<br>
     * N means that the tile has not been set<br>
     * C means that the tile is a CAT one (and so on, so T for TROPHY, ecc)<br>
     * R/X means that the tile is a random one
     */
    @BeforeEach
    void setUp() {
        for (CardGoalType t : CardGoalType.getValues())
            cards.add(new CardGoal(t));
    }


    /**
     * Test that checks if every goal card's verify method works
     */
    @Test
    @DisplayName("Goal cards testing")
    void goalCardsTesting() {
        for (CardGoal card : cards) {
            Shelf testShelf;
            testShelf = exampleShelf(card.getGoalType());
            assertEquals(card.verify(testShelf).getPoint(), 12);
        }
    }

    /**
     * It can't possibly return the maximum amount of points if the shelf is made to verify a different card goal.
     * It could also be that some tiles are matching, because the shelf is randomly populated,
     * so I'll just check that it doesn't return the max points
     */
    @Test
    @DisplayName("Goal cards testing with a different card")
    void goalCardsTestingFalse() {
        for (CardGoal card : cards) {
            Shelf testShelf;
            testShelf = exampleShelf(card.getGoalType());
            Optional<CardGoal> differentCard = cards.stream().filter(x -> !x.isSameType(card)).findFirst();
            differentCard.ifPresent(cardGoal -> assertNotEquals(cardGoal.verify(testShelf).getPoint(), 12));
        }
    }


    /**
     * Sets up an example shelf for every goal card
     */
    private Shelf exampleShelf(CardGoalType type) {
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
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
                        if (ret.getSingleTile(r, c).isSameType(TileType.NOT_USED))
                            ret.setSingleTile(new Tile(TileType.randomTile()), r, c);
                return ret;
            }
            default -> {
                return new Shelf();
            }
        }
    }


    @Test
    void goalTest(){
        CardGoal goal = new CardGoal(exampleShelf(CardGoalType.GOAL1), CardGoalType.GOAL1);
        assertEquals(goal.getGoalType(), CardGoalType.GOAL1);
    }
}
