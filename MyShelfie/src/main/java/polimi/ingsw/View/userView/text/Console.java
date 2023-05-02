package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Console {

    private List<String> importantEvents; //events that needs to be showed always in screen
    private Chat chat;

    public Console() {
        init();
    }

    public void init() {
        AnsiConsole.systemInstall();
        importantEvents = new ArrayList<>();
        chat = new Chat();
    }

    public void addImportantEvent(String imp) {
        //Want to show only the first maxnum_of_last_event_tobe_showed important event happened
        if (importantEvents.size() + 1 >= DefaultValue.maxnum_of_last_event_tobe_showed) {
            importantEvents.remove(0);
        }
        importantEvents.add(imp);
        show_important_events();
    }
    public void resize() {
        try {
            new ProcessBuilder("cmd", "/c", "mode con:cols=160 lines=50").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            //couldn't resize the terminal window
        }
    }

    public List<String> getImportantEvents() {
        return new ArrayList<>(importantEvents);
    }

    public void show_allPlayers(GameModelImmutable model) {
        System.out.println("Current Players: \n" + model.toStringListPlayers());
    }

    public void show_titleMyShelfie() {
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().fg(YELLOW).a("""

                ███╗░░░███╗██╗░░░██╗        ░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
                ████╗░████║╚██╗░██╔╝        ██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
                ██╔████╔██║░╚████╔╝░        ╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
                ██║╚██╔╝██║░░╚██╔╝░░        ░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
                ██║░╚═╝░██║░░░██║░░░        ██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
                ╚═╝░░░░░╚═╝░░░╚═╝░░░        ╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝
                """).reset());
    }

    public void show_playerHand(GameModelImmutable gameModel) {
        System.out.println(">This is your hand:");
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input-1, 0));
        for (int i = 0; i < DefaultValue.maxTilesInHand; i++) {
            if (i < gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().size()) {
                switch (gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType()) {
                    case CAT ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(GREEN).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                    case TROPHY ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(CYAN).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                    case PLANT ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(MAGENTA).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                    case BOOK ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(WHITE).fg(BLACK).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                    case ACTIVITY ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(YELLOW).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                    case FRAME ->
                            ris.append("[").append(i).append("]: ").append(ansi().bg(BLUE).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                }
            } else
                ris.append("[").append(i).append("]: ").append("NONE").append(" | ");
        }
        System.out.println(ris);
    }

    public void show_grabbedTile(String nickname, GameModelImmutable model) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input-1, 0).a(nickname).a(": Player: ").a(model.getNicknameCurrentPlaying()).a(" has grabbed some tiles"));
        for (Tile t : model.getHandOfCurrentPlaying()) {
            switch (t.getType()) {
                case CAT -> ris.append(ansi().bg(GREEN).a(t.toString()).bg(DEFAULT)).append(" | ");
                case TROPHY -> ris.append(ansi().bg(CYAN).a(t.toString()).bg(DEFAULT)).append(" | ");
                case PLANT -> ris.append(ansi().bg(MAGENTA).a(t.toString()).bg(DEFAULT)).append(" | ");
                case BOOK -> ris.append(ansi().bg(WHITE).a(t.toString()).bg(DEFAULT)).append(" | ");
                case ACTIVITY -> ris.append(ansi().bg(YELLOW).a(t.toString()).bg(DEFAULT)).append(" | ");
                case FRAME -> ris.append(ansi().bg(BLUE).a(t.toString()).bg(DEFAULT)).append(" | ");
            }
        }
        System.out.println(ris);
    }


    public void show_playground(GameModelImmutable model) {
        System.out.println(model.getPg().toString());
    }

    public void showAllShelves(GameModelImmutable model) {
        int i = DefaultValue.col_shelves;

        StringBuilder ris = new StringBuilder();

        for (Player p : model.getPlayers()) {
            ris.append(ansi().cursor(DefaultValue.row_playerName, i - 3).a(p.getNickname() + ": "));
            ris.append(ansi().cursor(DefaultValue.row_shelves,i - 3).a(p.getShelf().toString(i)).toString());

            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(ris);
    }

    public void showCommonCards(GameModelImmutable gameModel) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_commonCards, DefaultValue.col_commonCards));

        String title = String.valueOf(ansi().fg(WHITE).cursor(DefaultValue.row_commonCards, DefaultValue.col_commonCards-1).bold().a("Common Cards: ").fg(DEFAULT).boldOff());
        System.out.println(title);

        int i = 1;
        for (CommonCard c : gameModel.getCommonCards()) {
            ris.append(c.toString(i));
            i += 3;
        }
        System.out.println(ris);
    }

    public void showPoints(GameModelImmutable gameModel) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_points, DefaultValue.col_points));

        String title = String.valueOf(ansi().fg(RED).cursor(DefaultValue.row_points, DefaultValue.col_points-1).bold().a("Points: ").fg(DEFAULT).boldOff());
        System.out.println(title);

        int i = 1;
        for (Player p : gameModel.getPlayers()) {
            ris.append(ansi().cursor(DefaultValue.row_points + i, DefaultValue.col_points).a(p.getNickname()+": "+p.getTotalPoints()+" points"));
            i++;
        }
        System.out.println(ris);
    }


    public void showGoalCards(Player toShow) {
        System.out.println(toShow.getSecretGoal().getLayoutToMatch().toStringGoalCard());
    }

    public void showPlayerJoined(GameModelImmutable gameModel, String nick) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        System.out.flush();
        //StringBuilder players = new StringBuilder();
        StringBuilder ris = new StringBuilder();

        int i = 0;
        for (Player p : gameModel.getPlayers()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        System.out.println(ris);

        for (Player p : gameModel.getPlayers())
            if (!p.getReadyToStart() && p.getNickname().equals(nick))
                System.out.println(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        System.out.flush();

    }

    public void show_Publisher() throws IOException, InterruptedException {
        clearCMD();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(1, 1).fg(YELLOW).a("""
                                                                                                           \s
                                                                                                           \s
                  ,----..                                                                                  \s
                 /   /   \\                                   ,--,                                          \s
                |   :     :  __  ,-.                 ,---, ,--.'|    ,---.                                 \s
                .   |  ;. /,' ,'/ /|             ,-+-. /  ||  |,    '   ,'\\                                \s
                .   ; /--` '  | |' | ,--.--.    ,--.'|'   |`--'_   /   /   |                               \s
                ;   | ;    |  |   ,'/       \\  |   |  ,"' |,' ,'| .   ; ,. :                               \s
                |   : |    '  :  / .--.  .-. | |   | /  | |'  | | '   | |: :                               \s
                .   | '___ |  | '   \\__\\/: . . |   | |  | ||  | : '   | .; :                               \s
                '   ; : .'|;  : |   ," .--.; | |   | |  |/ '  : |_|   :    |                               \s
                '   | '/  :|  , ;  /  /  ,.  | |   | |--'  |  | '.'\\   \\  /                                \s
                |   :    /  ---'  ;  :   .'   \\|   |/      ;  :    ;`----'                                 \s
                 \\   \\ .'         |  ,     .-./'---'       |  ,   /                                        \s
                  `---`            `--`---'                 ---`-'                                         \s
                  ,----..                                   ___                                            \s
                 /   /   \\                                ,--.'|_    ,--,                                  \s
                |   :     :  __  ,-.                      |  | :,' ,--.'|    ,---.        ,---,            \s
                .   |  ;. /,' ,'/ /|                      :  : ' : |  |,    '   ,'\\   ,-+-. /  | .--.--.   \s
                .   ; /--` '  | |' | ,---.     ,--.--.  .;__,'  /  `--'_   /   /   | ,--.'|'   |/  /    '  \s
                ;   | ;    |  |   ,'/     \\   /       \\ |  |   |   ,' ,'| .   ; ,. :|   |  ,"' |  :  /`./  \s
                |   : |    '  :  / /    /  | .--.  .-. |:__,'| :   '  | | '   | |: :|   | /  | |  :  ;_    \s
                .   | '___ |  | ' .    ' / |  \\__\\/: . .  '  : |__ |  | : '   | .; :|   | |  | |\\  \\    `. \s
                '   ; : .'|;  : | '   ;   /|  ," .--.; |  |  | '.'|'  : |_|   :    ||   | |  |/  `----.   \\\s
                '   | '/  :|  , ; '   |  / | /  /  ,.  |  ;  :    ;|  | '.'\\   \\  / |   | |--'  /  /`--'  /\s
                |   :    /  ---'  |   :    |;  :   .'   \\ |  ,   / ;  :    ;`----'  |   |/     '--'.     / \s
                 \\   \\ .'          \\   \\  / |  ,     .-./  ---`-'  |  ,   /         '---'        `--'---'  \s
                  `---`             `----'   `--`---'               ---`-'                                 \s
                """).reset());
    }

    private void show_important_events() {

        StringBuilder ris = new StringBuilder();
        int i = 0;
        ris.append(ansi().fg(GREEN).cursor(DefaultValue.row_important_events + i, DefaultValue.col_important_events-1 ).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : importantEvents) {
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_important_events + 1 + i, DefaultValue.col_important_events).a(s).fg(DEFAULT));
            i++;
        }
        System.out.println(ris);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }

    public void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            //if not on a Windows machine
        } catch (IOException | InterruptedException e) {
            //for mac
            System.out.print("\033\143");

            /*This might work too, but exec is deprecated
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

             */
        }
    }

    public void showMessages() {
        String ris = String.valueOf(ansi().fg(GREEN).cursor(DefaultValue.row_chat, DefaultValue.col_chat-1).bold().a("Latest Messages:").fg(DEFAULT).boldOff()) +
                ansi().fg(WHITE).cursor(DefaultValue.row_chat + 1, DefaultValue.col_chat).a(chat.toString()).fg(DEFAULT);
        System.out.println(ris);
        if (chat.getMsgs().size() > 0) {
            System.out.println(ansi().cursor(DefaultValue.row_input, 0));
        }
    }



    public int getLengthLongestMessage() {
        return chat.getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .toString().length();
    }

    public void addMessage(Message msg) {
        chat.addMsg(msg);
        showMessages();
    }

    public void showNoAvailableGamesToJoin(String msgToVisualize){
        String ris = String.valueOf(ansi().fg(RED).cursor(11, 4).bold().a(msgToVisualize).fg(DEFAULT).boldOff()) +
                String.valueOf(ansi().fg(RED).cursor(12, 4).bold().a("Try later or create a new game!").fg(DEFAULT).boldOff());
        ansi().fg(WHITE).cursor(13, 4).a(chat.toString()).fg(DEFAULT);


        System.out.println(ris);
    }

    public void showGameEnded(GameModelImmutable model){
        clearCMD();
        resize();
        show_titleMyShelfie();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(DefaultValue.row_nextTurn, 0).fg(GREEN).a("""

                ░██████╗░░█████╗░███╗░░░███╗███████╗        ███████╗███╗░░██╗██████╗░███████╗██████╗░
                ██╔════╝░██╔══██╗████╗░████║██╔════╝        ██╔════╝████╗░██║██╔══██╗██╔════╝██╔══██╗
                ██║░░██╗░███████║██╔████╔██║█████╗░░        █████╗░░██╔██╗██║██║░░██║█████╗░░██║░░██║
                ██║░░╚██╗██╔══██║██║╚██╔╝██║██╔══╝░░        ██╔══╝░░██║╚████║██║░░██║██╔══╝░░██║░░██║
                ╚██████╔╝██║░░██║██║░╚═╝░██║███████╗        ███████╗██║░╚███║██████╔╝███████╗██████╔╝
                ░╚═════╝░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚══════╝        ╚══════╝╚═╝░░╚══╝╚═════╝░╚══════╝╚═════╝░
                
                """).reset());

        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(DefaultValue.row_nextTurn + 10, 0).fg(CYAN).a("""

                █     █▀▀▀  █▀▀█  █▀▀▄  █▀▀▀  █▀▀█    █▀▀█  █▀▀▀█  █▀▀█  █▀▀█  █▀▀▄
                █     █▀▀▀  █▄▄█  █  █  █▀▀▀  █▄▄▀    █▀▀▄  █   █  █▄▄█  █▄▄▀  █  █
                █▄▄█  █▄▄▄  █  █  █▄▄▀  █▄▄▄  █  █    █▄▄█  █▄▄▄█  █  █  █  █  █▄▄▀
        
                """).reset());




        int i=1;
        int classif = 1;
        StringBuilder ris = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : model.getLeaderBoard().entrySet()) {
            System.out.println();
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_nextTurn + 17 + i, 30).a("#"+classif+" "+model.getPlayers().get(entry.getKey()).getNickname() + ": " + entry.getValue()+" points").fg(DEFAULT));
            i+=2;
            classif++;
        }

        System.out.println(ris);

    }

    public void alwaysShowForAll(GameModelImmutable model){
        clearCMD();
        // TODO: MERGE THE alwaysShowForAll and AlwaysShow and correct the resizing
        //resize();
        show_titleMyShelfie();
        show_playground(model);
        showCommonCards(model);
        showMessages();
        showPoints(model);
        show_important_events();
    }

    public void showGameId(GameModelImmutable gameModel){
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }

    public void showNextTurn(GameModelImmutable gameModel){
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn, 0).bold().a("Next turn! It's up to: " + gameModel.getNicknameCurrentPlaying()).boldOff());
    }

    public void showWelcome(String nick){
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn+1, 0).bold().a("Welcome " + nick).boldOff());
    }

    public void alwaysShow(GameModelImmutable model, String nick) {
        alwaysShowForAll(model);
        for (Player p : model.getPlayers())
            if (p.getNickname().equals(nick))
                showGoalCards(p);
        showAllShelves(model);
        showGameId(model);
        showNextTurn(model);
        showWelcome(nick);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }


}
