package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.userView.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class TUI extends UI {


    public TUI() {
        init();
    }

    @Override
    public void init() {
        AnsiConsole.systemInstall();
        importantEvents = new ArrayList<>();
        chat = new Chat();

    }

    @Override
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

    @Override
    public void show_playerHand(GameModelImmutable gameModel) {

        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input - 2, 0).a(">This is your hand:").cursor(DefaultValue.row_input-1,0));

        for (int i = 0; i < gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().size(); i++) {
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
        }
        System.out.println(ris);
    }

    @Override
    public void show_grabbedTile(String nickname, GameModelImmutable model) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input - 1, 0).a(nickname).a(": Player: ").a(model.getNicknameCurrentPlaying()).a(" has grabbed some tiles: | "));
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


    public void show_allShelves(GameModelImmutable model) {
        int i = DefaultValue.col_shelves;

        StringBuilder ris = new StringBuilder();

        for (Player p : model.getPlayers()) {
            ris.append(ansi().cursor(DefaultValue.row_playerName, i - 3).a(p.getNickname() + ": "));
            ris.append(ansi().cursor(DefaultValue.row_shelves, i - 3).a(p.getShelf().toString(i)).toString());

            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(ris);
    }

    @Override
    public void show_commonCards(GameModelImmutable gameModel) {
        this.clearScreen();
        this.show_titleMyShelfie();
        this.show_playground(gameModel);
        this.show_gameId(gameModel);

        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_commonCards, DefaultValue.col_commonCards));

        String title = String.valueOf(ansi().fg(WHITE).cursor(DefaultValue.row_commonCards, DefaultValue.col_commonCards - 1).bold().a("Common Cards: ").fg(DEFAULT).boldOff());
        System.out.println(title);

        int i = 1;
        for (CommonCard c : gameModel.getCommonCards()) {
            ris.append(c.toString(i));
            i += 3;
        }
        System.out.println(ris);
    }


    public void show_points(GameModelImmutable gameModel) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_points, DefaultValue.col_points));

        String title = String.valueOf(ansi().fg(RED).cursor(DefaultValue.row_points, DefaultValue.col_points - 1).bold().a("Points: ").fg(DEFAULT).boldOff());
        System.out.println(title);

        int i = 1;
        for (Player p : gameModel.getPlayers()) {
            ris.append(ansi().cursor(DefaultValue.row_points + i, DefaultValue.col_points).a(p.getNickname() + ": " + p.getTotalPoints() + " points"));
            i++;
        }
        System.out.println(ris);
    }


    public void show_goalCards(Player toShow) {
        System.out.println(toShow.getSecretGoal().getLayoutToMatch().toStringGoalCard());
    }

    @Override
    public void show_playerJoined(GameModelImmutable gameModel, String nick) {
        clearScreen();
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

    @Override
    public void show_publisher() {
        this.resize();


        clearScreen();
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

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.show_titleMyShelfie();
    }


    public void show_important_events() {

        StringBuilder ris = new StringBuilder();
        int i = 0;
        ris.append(ansi().fg(GREEN).cursor(DefaultValue.row_important_events + i, DefaultValue.col_important_events - 1).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : importantEvents) {
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_important_events + 1 + i, DefaultValue.col_important_events).a(s).fg(DEFAULT));
            i++;
        }
        System.out.println(ris);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }


    public void clearScreen() {
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


    public void show_messages() {
        String ris = String.valueOf(ansi().fg(GREEN).cursor(DefaultValue.row_chat, DefaultValue.col_chat - 1).bold().a("Latest Messages:").fg(DEFAULT).boldOff()) +
                ansi().fg(WHITE).cursor(DefaultValue.row_chat + 1, DefaultValue.col_chat).a(chat.toString()).fg(DEFAULT);
        System.out.println(ris);
        if (chat.getMsgs().size() > 0) {
            System.out.println(ansi().cursor(DefaultValue.row_input, 0));
        }
    }

    @Override
    public int getLengthLongestMessage() {
        return chat.getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .toString().length();
    }

    @Override
    public void addMessage(Message msg) {
        chat.addMsg(msg);
        show_messages();
    }

    @Override
    public void show_noAvailableGamesToJoin(String msgToVisualize) {
        String ris = ansi().fg(RED).cursor(11, 4).bold().a(msgToVisualize).fg(DEFAULT).boldOff() +
                String.valueOf(ansi().fg(RED).cursor(12, 4).bold().a("Try later or create a new game!").fg(DEFAULT).boldOff());
        ansi().fg(WHITE).cursor(13, 4).a(chat.toString()).fg(DEFAULT);


        System.out.println(ris);
    }

    @Override
    public void show_gameEnded(GameModelImmutable model) {
        clearScreen();
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
                                
                """).fg(DEFAULT).reset());

        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(DefaultValue.row_nextTurn + 10, 0).bg(CYAN).a("""

                █     █▀▀▀  █▀▀█  █▀▀▄  █▀▀▀  █▀▀█    █▀▀█  █▀▀▀█  █▀▀█  █▀▀█  █▀▀▄
                █     █▀▀▀  █▄▄█  █  █  █▀▀▀  █▄▄▀    █▀▀▄  █   █  █▄▄█  █▄▄▀  █  █
                █▄▄█  █▄▄▄  █  █  █▄▄▀  █▄▄▄  █  █    █▄▄█  █▄▄▄█  █  █  █  █  █▄▄
                        
                """).bg(DEFAULT).reset());


        int i = 1;
        int classif = 1;
        StringBuilder ris = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : model.getLeaderBoard().entrySet()) {
            System.out.println();
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_leaderboard + i, DefaultValue.col_leaderboard).a("#" + classif + " " + model.getPlayers().get(entry.getKey()).getNickname() + ": " + entry.getValue() + " points").fg(DEFAULT));
            i += 2;
            classif++;
        }

        System.out.println(ris);

    }


    public void show_alwaysShowForAll(GameModelImmutable model) {
        this.clearScreen();
        // TODO: MERGE THE alwaysShowForAll and AlwaysShow and correct the resizing
        //resize();
        show_titleMyShelfie();
        show_playground(model);
        show_commonCards(model);
        show_messages();
        show_points(model);
        show_important_events();
    }


    public void show_gameId(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }


    public void show_nextTurn(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn, 0).bold().a("Next turn! It's up to: " + gameModel.getNicknameCurrentPlaying()).boldOff());
    }


    public void show_welcome(String nick) {
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn + 1, 0).bold().a("Welcome " + nick).boldOff());
    }

    @Override
    public void resetChat() {
        this.chat = new Chat();
    }

    @Override
    public void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
    }

    @Override
    public void show_direction() {
        System.out.println("\t> Choose direction (r=right,l=left,u=up,d=down): ");
    }


    public void removeInput(String msg) {
        System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(getLengthLongestMessage())));
    }

    @Override
    public void show_sentMessage(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    @Override
    public void show_grabbedTileMainMsg(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    @Override
    public void show_positionedTile(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    @Override
    public void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    @Override
    public void show_askNum(String msg, GameModelImmutable gameModel, String nickname) {
        this.show_alwaysShow(gameModel, nickname);
        this.removeInput(msg);
    }

    @Override
    public void show_gameStarted(GameModelImmutable model) {
        this.clearScreen();
        this.show_titleMyShelfie();
        this.show_allPlayers(model);
        this.show_alwaysShowForAll(model);
        this.show_gameId(model);
    }

    @Override
    public void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    @Override
    public void show_returnToMenuMsg() {
        System.out.println("\nPress any key to return to the menu");
    }

    @Override
    public void show_insertNicknameMsg() {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("> Insert your nickname: "));
    }

    @Override
    public void show_choosenNickname(String nickname) {
        System.out.println(ansi().cursor(DefaultValue.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
    }

    @Override
    public void show_menuOptions() {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println(ansi().cursor(9, 0).a("""
                > Select one option:
                \t(c) Create a new Game
                \t(j) Join to a random Game
                \t(js) Join a specific Game by idGame
                \t(x) Reconnect
                \t(.) to leave
                \t
                \t -> Useful commands that can be used at any point in the game:
                \t\t  type "/c [msg]" to send a public message!
                \t\t  type "/cs [playerName] [msg]" to send a private message!
                \t\t  type "/quit" and you can leave the game!
                \t""").fg(DEFAULT));
    }

    @Override
    public void show_inputGameIdMsg() {
        System.out.println("> Input the GameId ('.' to leave): ");
    }

    @Override
    public void show_NaNMsg() {
        System.out.println("> NaN");
    }

    @Override
    public void show_whichTileToPlaceMsg() {
        System.out.println("> Select which tile do you want to place:");
    }

    @Override
    public void show_wrongSelectionHandMsg() {
        System.out.println("\tWrong Tile selection offset");
    }

    @Override
    public void show_creatingNewGameMsg() {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> Creating a new game...");
    }

    @Override
    public void show_joiningFirstAvailableMsg() {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> Connecting to the first available game...");
    }

    @Override
    public void show_joiningToGameIdMsg(int idGame) {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
    }


    public void show_alwaysShow(GameModelImmutable model, String nick) {
        show_alwaysShowForAll(model);
        for (Player p : model.getPlayers()) {
            if (p.getNickname().equals(nick))
                show_goalCards(p);
            if (p.getInHandTile().size() > 0)
                if (p.getNickname().equals(nick)) {
                    show_playerHand(model);
                } else
                    show_grabbedTile(model.getNicknameCurrentPlaying(), model);
        }
        show_allShelves(model);
        show_gameId(model);
        show_nextTurn(model);
        show_welcome(nick);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }




}
