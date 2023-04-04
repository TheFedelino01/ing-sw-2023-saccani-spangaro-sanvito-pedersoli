package polimi.ingsw.View;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Tile;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    Scanner scanner = new Scanner(System.in);
    private String nickname;
    private Integer gameID;
    private boolean isReadyToStart;
    private String answer;
    private static Tile[][] shelf;

    public TextUI() {
        nickname = "";
        gameID = 0;
        isReadyToStart = false;
        answer = "";
        shelf = new Tile[DefaultValue.NumOfRowsShelf][DefaultValue.NumOfColumnsShelf];

    }

    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.insertNickname();
        textUI.selectGame();
        textUI.isPlayerReadyToStart();
        textUI.turn(true);
        textUI.viewShelf(shelf);
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
        while (!isReadyToStart) {
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

    public void viewCommonCard(List<CommonCard> card) {
    }

    public void viewPlayGround() {
    }

    public void viewGoalCard() {
    }

    public void viewShelf(Tile[][] shelf) {

        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                System.out.format("%-10s", shelf[i][j].getType());
            }
            System.out.println();
        }
    }
}
