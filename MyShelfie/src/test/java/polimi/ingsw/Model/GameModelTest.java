package polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.Model.Cards.Common.*;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Exceptions.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel();
    }

    @Test
    @DisplayName("Check Player List")
    void testPlayersList() {
        if (model.getNumOfPlayers() != 0) {
            fail("First init contains players");
        }

        for (int i = 0; i < 3; i++) {
            Player p = new Player(Integer.toString(i));

            try {
                try {
                    model.addPlayer(p);
                } catch (MaxPlayersInException e) {
                    fail("Player not added because game is full but that's not true");
                }
            } catch (PlayerAlreadyInException e) {
                fail("Player not added because of double but that's not true");
            }

            if (model.getNumOfPlayers() != (i + 1)) {
                fail("Wanted to add a player but never added");
            }
            if (!model.getPlayers().get(i).equals(p)) {
                fail("Player added isn't the corrent one");
            }

        }

        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("0")));
        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("1")));
        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("2")));

        try {
            model.addPlayer(new Player("3"));

        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }

        assertThrows(MaxPlayersInException.class, () -> model.addPlayer(new Player("4")));

        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("0")));
        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("1")));
        assertThrows(PlayerAlreadyInException.class, () -> model.addPlayer(new Player("2")));


    }

    @Test
    @DisplayName("Check Common Cards")
    void testCommonCards() {
        if (!(model.getNumOfCommonCards() >= 0 && model.getNumOfCommonCards() <= DefaultValue.NumOfCommonCards))
            fail("There are more common Cards than expected");

        CommonCard c1 = new CommonHorizontalCard(CardCommonType.CommonDiagonal0, 0);
        CommonCard c2 = new CommonDiagonalCard(CardCommonType.CommonVertix, 0);
        try {
            model.addCommonCard(c1);
        } catch (MaxCommonCardsAddedException e) {
            fail("Common Cards overflow but that's not true");
        } catch (CommonCardAlreadyInException e) {
            fail("Common Card already in but that's not true");
        }

        if (model.getNumOfCommonCards() != 1) {
            fail("Card size wrong");
        }

        if (!model.getCommonCard(0).equals(c1)) {
            fail("Card added isn't the right one");
        }
        assertThrows(CommonCardAlreadyInException.class, () -> model.addCommonCard(c1), "This card is already in but no exception thrown");

        try {
            model.addCommonCard(c2);
        } catch (MaxCommonCardsAddedException e) {
            fail("Common Cards overflow but that's not true");
        } catch (CommonCardAlreadyInException e) {
            fail("Common Card already in but that's not true");
        }


        if (model.getNumOfCommonCards() != 2) {
            fail("Card size wrong");
        }

        if (!model.getCommonCard(1).equals(c2)) {
            fail("Card added isn't the right one");
        }

        assertThrows(CommonCardAlreadyInException.class, () -> model.addCommonCard(c1), "This card is already in but no exception thrown");
        assertThrows(CommonCardAlreadyInException.class, () -> model.addCommonCard(c2), "This card is already in but no exception thrown");


        assertThrows(MaxCommonCardsAddedException.class, () -> model.addCommonCard(new CommonXCard(CardCommonType.CommonDiagonal1)), "Cards Overflow but no exception thrown");

    }


    @Test
    @DisplayName("Start a Game with players and increase turn")
    void testCurrentPlaying() {
        if (model.getCurrentPlaying() == -1 && model.getStatus() != GameStatus.WAIT) {
            fail("Game started but no player is current playing");
        }

        assertThrows(NotReadyToRunException.class, () -> model.setStatus(GameStatus.RUNNING), "Game started with not enough players");

        try {
            model.addPlayer(new Player("1"));
            model.addPlayer(new Player("2"));
            model.addPlayer(new Player("3"));
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }

        assertThrows(GameNotStartedException.class, () -> model.nextTurn(), "Wanted to increase turn but game is not started yet");

        int turn = 0;
        model.setCurrentPlaying(turn);

        assertThrows(NotReadyToRunException.class, () -> model.setStatus(GameStatus.RUNNING), "Wanted to start game but Common and Goal cards not setted");

        try {
            model.setGoalCard(0, new CardGoal(CardGoalType.GOAL1));
            model.setGoalCard(1, new CardGoal(CardGoalType.GOAL2));
            model.setGoalCard(2, new CardGoal(CardGoalType.GOAL3));
        } catch (SecretGoalAlreadyGivenException e) {
            throw new RuntimeException(e);
        }

        assertThrows(NotReadyToRunException.class, () -> model.setStatus(GameStatus.RUNNING), "Wanted to start game but Common Cards not setted");

        try {
            model.addCommonCard(new CommonXCard(CardCommonType.CommonDiagonal1));
            model.addCommonCard(new CommonVertixesCard(CardCommonType.CommonX));
        } catch (MaxCommonCardsAddedException | CommonCardAlreadyInException e) {
            throw new RuntimeException(e);
        }

        model.setStatus(GameStatus.RUNNING);

        for (int i = 0; i < 6; i++) {

            if (!(model.getCurrentPlaying() >= 0 && model.getCurrentPlaying() <= DefaultValue.MaxNumOfPlayer)) {
                fail("The current playing player is out of index");
            }

            if (!(model.getNumOfPlayers() >= DefaultValue.minNumOfPlayer)) {
                fail("There are no enough player playing");
            }

            if (model.getCurrentPlaying() != turn) {
                fail("Turn wrong");
            }
            turn = (turn + 1) % 3;
            try {
                model.nextTurn();
            } catch (GameEndedException e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Test
    @DisplayName("Test Chat")
    void testChat() {

        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        try {
            model.addPlayer(p1);
            model.addPlayer(p2);
            model.addPlayer(p3);
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }

        model.sentMessage(p1, "msg1");
        model.sentMessage(p2, "msg2");
        model.sentMessage(p3, "msg3");


        assertThrows(ActionPerformedByAPlayerNotPlayingException.class, () -> model.sentMessage(new Player("z"), "msg4"), "Player not playing sent a message");

        boolean found;
        for (Message m : model.getChat().getMsgs()) {
            found = false;
            for (int i = 0; i < model.getNumOfPlayers() && !found; i++) {
                if (model.getPlayers().get(i).equals(m.getSender())) {
                    found = true;
                }
            }
            if (!found) {
                fail("Player not playing sent a message");
            }
        }


    }

    @Test
    @DisplayName("Check final shelf true")
    void checkFinalShelfTrue() {
        try {
            model.addPlayer(new Player("0", new Shelf(), new CardGoal(CardGoalType.GOAL1), new ArrayList<>(), new ArrayList<>(), -1));
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }
        for (CardGoalType c : CardGoalType.getValues()) {
            model.getPlayers().get(0).setShelfS(new CardGoal(c).getLayoutToMatch());
            model.getPlayers().get(0).setSecretGoal(new CardGoal(c));
            assertTrue(model.getPlayers().get(0).getSecretGoal().verify(model.getPlayers().get(0).getShelf()).getPoint()>0);
        }
    }

    @Test
    @DisplayName("Check final shelf false")
    void checkFinalShelfFalse() {
        try {
            model.addPlayer(new Player("0", new Shelf(), new CardGoal(CardGoalType.GOAL0), new ArrayList<>(), new ArrayList<>(), -1));
            model.addPlayer(new Player("1", new Shelf(), new CardGoal(CardGoalType.GOAL0), new ArrayList<>(), new ArrayList<>(), -1));
        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }
        for (CardGoalType c : CardGoalType.getValuesBUT()) {
            model.getPlayers().get(0).setShelfS(new CardGoal(c).getLayoutToMatch());
            model.getPlayers().get(1).setShelfS(new CardGoal(c).getLayoutToMatch());
            model.getPlayers().get(1).setSecretGoal(new CardGoal(c));
            assertNotEquals(model.getPlayers().get(0).getSecretGoal().verify(model.getPlayers().get(0).getShelf()).getPoint(),
                    model.getPlayers().get(1).getSecretGoal().verify(model.getPlayers().get(1).getShelf()).getPoint());
        }
    }



    @Test
    @DisplayName("Test Goal Cards")
    void testSecretGoal() {
        assertThrows(IndexPlayerOutOfBoundException.class, () -> model.setGoalCard(0, new CardGoal()), "Wanted to set a GoalCard to a non playing player");
        assertThrows(IndexPlayerOutOfBoundException.class, () -> model.setGoalCard(-1, new CardGoal()), "Wanted to set a GoalCard to a non playing player");
        assertThrows(IndexPlayerOutOfBoundException.class, () -> model.setGoalCard(2, new CardGoal()), "Wanted to set a GoalCard to a non playing player");
        assertThrows(IndexPlayerOutOfBoundException.class, () -> model.setGoalCard(5, new CardGoal()), "Wanted to set a GoalCard to a non playing player");

        try {
            model.addPlayer(new Player("1"));
            model.addPlayer(new Player("2"));
            model.addPlayer(new Player("3"));

        } catch (PlayerAlreadyInException | MaxPlayersInException e) {
            throw new RuntimeException(e);
        }

        try {
            model.setGoalCard(0, new CardGoal(CardGoalType.GOAL1));
            model.setGoalCard(1, new CardGoal(CardGoalType.GOAL2));
        } catch (SecretGoalAlreadyGivenException e) {
            throw new RuntimeException(e);
        }

        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(1, new CardGoal(CardGoalType.GOAL1)), "Secret Goal was already assigned");
        assertThrows(IndexPlayerOutOfBoundException.class, () -> model.setGoalCard(3, new CardGoal()), "Wanted to set a GoalCard to a non playing player");

        try {
            model.setGoalCard(2, new CardGoal(CardGoalType.GOAL3));
        } catch (SecretGoalAlreadyGivenException e) {
            throw new RuntimeException(e);
        }

        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(0, new CardGoal(CardGoalType.GOAL1)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(0, new CardGoal(CardGoalType.GOAL2)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(0, new CardGoal(CardGoalType.GOAL3)), "Secret Goal was already assigned");

        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(1, new CardGoal(CardGoalType.GOAL1)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(1, new CardGoal(CardGoalType.GOAL2)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(1, new CardGoal(CardGoalType.GOAL3)), "Secret Goal was already assigned");

        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(2, new CardGoal(CardGoalType.GOAL1)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(2, new CardGoal(CardGoalType.GOAL2)), "Secret Goal was already assigned");
        assertThrows(SecretGoalAlreadyGivenException.class, () -> model.setGoalCard(2, new CardGoal(CardGoalType.GOAL3)), "Secret Goal was already assigned");
    }


}
