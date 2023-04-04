package polimi.ingsw.View;

import polimi.ingsw.Model.Cards.Common.*;
import polimi.ingsw.Model.Enumeration.CardCommonType;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    Scanner scanner = new Scanner(System.in);
    private String nickname;
    private Integer gameID;
    private boolean isReadyToStart;
    private String answer;
    private static int commoncard;
    private static int commoncard2;


    public TextUI() {
        nickname = "";
        gameID = 0;
        isReadyToStart = false;
        answer = "";
        commoncard = 0;
        commoncard2 = 2;
    }

    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.insertNickname();
        textUI.selectGame();
        textUI.isPlayerReadyToStart();
        textUI.turn(true);

    }

    public void insertNickname() {
        System.out.println("> Insert your nickname: ");
        nickname = scanner.nextLine();
        System.out.println("> Your nickname is: " + nickname);
    }

    public void selectGame() {
        System.out.println("> Do you want to create a new game or join an existing one? (c/j)");
        answer = scanner.nextLine();
        if (answer.equals("c")) {
            System.out.println("> You have selected to create a new game");
            System.out.println("> Insert the number of players: ");
            int numberOfPlayers = Integer.parseInt(scanner.nextLine());
            System.out.println("> You have selected to create a new game with ID: " + gameID + " and " + numberOfPlayers + " players");
        } else {
            System.out.println("> You have selected to join an existing game");
            System.out.println("> Select the game you want to join: ");
            gameID = Integer.parseInt(scanner.nextLine());
        }

        System.out.println("> You have selected the game: " + gameID);
    }

    public void isPlayerReadyToStart() {
        while(!isReadyToStart) {
            System.out.println("> Are you ready to start? (y/n)");
            answer = scanner.nextLine();
            if (answer.equals("y"))
                isReadyToStart = true;
            else
                isReadyToStart = false;
        }
        System.out.println("> You have selected: " + isReadyToStart);
    }

    public void turn(boolean isYourTurn) {
        if (isYourTurn)
            System.out.println("> It's your turn!");
        else
            System.out.println("> It's not your turn!");
    }

    public void viewCommonCard(int card1, int card2){
        System.out.println("> The two common cards are:");
        viewSingleCommonCard(card1);
        viewSingleCommonCard(card2);
    }
    public void viewSingleCommonCard(int card) {
        System.out.println("");
        switch (card) {
            case 0:
                System.out.println("> Six groups of 2 tiles of the same type (tile type can change between groups)");
                break;

            case 1:
                System.out.println("> Same tiles in the shelf vertexes");
                break;

            case 2:
                System.out.println("> Four groups of 4 tiles of the same type (tile type can change between groups)");
                break;

            case 3:
                System.out.println("> Two groups of 2x2 tiles of the same type (tile type CANNOT change between groups)");
                break;

            case 4:
                System.out.println("> Three columns made with max three different tile types (combinations may vary, but AT MAX three different types)");
                break;

            case 5:
                System.out.println("> Eight tiles of the same type");
                break;

            case 6:
                System.out.println("> Five tiles of the same type, creating a diagonal line across the shelf");
                break;

            case 7:
                System.out.println("> Four rows made with max three different tile types (combinations may vary, but AT MAX three different types)");
                break;

            case 8:
                System.out.println("> Two columns made with all different tile types");
                break;

            case 9:
                System.out.println("> Two rows made with all different tile types");
                break;

            case 10:
                System.out.println("> An X pattern is formed on the shelf, with tiles of the same type");
                break;

            case 11:
                System.out.println("> Five columns in ascending or descending order: from 5 to 1 tile or from 1 to 5 tiles, types may vary");
                break;

            default:
                System.out.println("> Error CommonCard");
                break;
        };
    }

    public void viewPlayGround() {
    }

    public void viewGoalCard() {
    }

    public void viewShelf() {
    }
}
