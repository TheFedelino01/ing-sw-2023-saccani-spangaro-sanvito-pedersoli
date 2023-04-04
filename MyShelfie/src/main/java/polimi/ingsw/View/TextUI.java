package polimi.ingsw.View;

public class TextUI {
    private String nickname;
    public void insertNickname() {
        System.out.println(this.hashCode()+"> Insert your nickname: ");
        nickname = System.console().readLine();
    }

    public void playerJoined(String nickNewPlayer) {
        System.out.println(this.hashCode()+"> "+nickNewPlayer+" has just joined!");
    }
}
