package polimi.ingsw.View;

import polimi.ingsw.Model.Cards.Common.CommonCard;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    Scanner scanner = new Scanner(System.in);
    private String nickname;
    private Integer gameID;
    private boolean isReadyToStart;
    private String answer;

    public TextUI() {
        nickname = "";
        gameID = 0;
        isReadyToStart = false;
        answer = "";
    }

    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        //textUI.insertNickname();
        //textUI.selectGame();
       // textUI.isPlayerReadyToStart();
       // textUI.turn(true);
        textUI.viewCommonCard(null);
    }

    public void insertNickname() {
        System.out.println(this.hashCode() + "> Insert your nickname: ");
        nickname = scanner.nextLine();
        System.out.println(this.hashCode() + "> Your nickname is: " + nickname);
    }

    public void selectGame() {
        System.out.println(this.hashCode() + "> Select the game you want to join: ");
        gameID = Integer.parseInt(scanner.nextLine());
        System.out.println(this.hashCode() + "> You have selected the game: " + gameID);
    }

    public void isPlayerReadyToStart() {
        System.out.println(this.hashCode() + "> Are you ready to start? (y/n)");
        answer = scanner.nextLine();
        if (answer.equals("y"))
            isReadyToStart = true;
        else
            isReadyToStart = false;

        System.out.println(this.hashCode() + "> You have selected: " + isReadyToStart);
    }

    public void turn(boolean isYourTurn) {
        if (isYourTurn)
            System.out.println(this.hashCode() + "> It's your turn!");
        else
            System.out.println(this.hashCode() + "> It's not your turn!");
    }

    public void viewCommonCard(List<CommonCard> card) {
    }

    public void viewPlayGround() {
    }

    public void viewGoalCard() {
    }

    public void viewShelf() {
    }
}
