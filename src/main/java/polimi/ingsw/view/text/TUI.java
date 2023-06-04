package polimi.ingsw.view.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.interfaces.CommonCardIC;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.model.interfaces.TileIC;
import polimi.ingsw.view.flow.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class TUI extends UI {

    private String nickname;

    /**
     * constructor
     */
    public TUI() {
        init();
    }

    /**
     * init
     */
    @Override
    public void init() {
        AnsiConsole.systemInstall();
        importantEvents = new ArrayList<>();
    }

    /**
     * @param input the string of the important event to add
     */
    @Override
    public void addImportantEvent(String input) {
        //Want to show only the first maxnum_of_last_event_tobe_showed important event happened
        if (importantEvents.size() + 1 >= DefaultValue.maxnum_of_last_event_tobe_showed) {
            importantEvents.remove(0);
        }
        importantEvents.add(input);
        show_important_events();
    }

    /**
     * Resizes the console
     */
    public void resize() {
        try {
            new ProcessBuilder("cmd", "/c", "mode con:cols=160 lines=50").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            //couldn't resize the terminal window
        }
    }

    /**
     * Shows all players' nicknames
     *
     * @param model
     */
    public void show_allPlayers(GameModelImmutable model) {
        System.out.println("Current Players: \n" + model.toStringListPlayers());
    }

    /**
     * Shows the game title
     */
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

    /**
     * @param gameModel the model that has the player hand that needs to be shown
     */
    @Override
    public void show_playerHand(GameModelImmutable gameModel) {

        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input - 2, 0).a(">This is your hand:").cursor(DefaultValue.row_input - 1, 0));

        for (int i = 0; i < gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().size(); i++) {
            switch (gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType()) {
                case CAT ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(GREEN).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                case TROPHY ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(CYAN).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                case PLANT ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(MAGENTA).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                case BOOK ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(WHITE).fg(BLACK).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                case ACTIVITY ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(YELLOW).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
                case FRAME ->
                        ris.append("[").append(i).append("]: ").append(ansi().bg(BLUE).fg(WHITE).a(gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile_IC().get(i).getType().toString()).fg(DEFAULT).bg(DEFAULT)).append(" | ");
            }
        }
        System.out.println(ris);
    }

    /**
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    @Override
    public void show_grabbedTile(String nickname, GameModelImmutable model) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input - 1, 0).a(nickname).a(": Player: ").a(model.getNicknameCurrentPlaying()).a(" has grabbed some tiles: | "));
        for (TileIC t : model.getHandOfCurrentPlaying()) {
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

    /**
     * Shows the playground
     *
     * @param model
     */
    public void show_playground(GameModelImmutable model) {
        System.out.println(model.getPg().toString());
    }

    /**
     * Shows all players' shelves
     *
     * @param model
     */
    public void show_allShelves(GameModelImmutable model) {
        int i = DefaultValue.col_shelves;

        StringBuilder ris = new StringBuilder();

        for (PlayerIC p : model.getPlayers()) {
            if (model.getFirstTurnIndex() == (model.getPlayers().indexOf(model.getPlayerEntity(p.getNickname())))) {
                new PrintStream(System.out, true, System.console() != null
                        ? System.console().charset()
                        : Charset.defaultCharset()
                ).println(ansi()
                        .cursor(DefaultValue.row_shelves, i + DefaultValue.chair_index).fg(WHITE)
                        .a(" .-===-.")
                        .cursor(DefaultValue.row_shelves + 1, i + DefaultValue.chair_index).fg(WHITE)
                        .a(" | . . |")
                        .cursor(DefaultValue.row_shelves + 2, i + DefaultValue.chair_index).fg(WHITE)
                        .a(" | .'. |")
                        .cursor(DefaultValue.row_shelves + 3, i + DefaultValue.chair_index).fg(WHITE)
                        .a("()_____()")
                        .cursor(DefaultValue.row_shelves + 4, i + DefaultValue.chair_index).fg(WHITE)
                        .a("||_____||")
                        .cursor(DefaultValue.row_shelves + 5, i + DefaultValue.chair_index).fg(WHITE)
                        .a(" W     W").reset());
            }
            ris.append(ansi().cursor(DefaultValue.row_playerName, i - 3).a(p.getNickname() + ": "));
            ris.append(ansi().cursor(DefaultValue.row_shelves, i - 3).a(p.getShelf().toString(i)).toString());
            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(ris);
    }

    /**
     * @param gameModel the model that has the common cards to show
     */
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
        for (CommonCardIC c : gameModel.getCommonCards()) {
            if (gameModel.getFirstFinishedPlayer() == -1)
                ris.append(c.toString(i, false));
            else
                ris.append(c.toString(i, true));
            i += 3;
        }
        System.out.println(ris);
    }

    /**
     * Shows the player's points
     *
     * @param gameModel
     */
    public void show_points(GameModelImmutable gameModel) {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_points, DefaultValue.col_points));

        String title = String.valueOf(ansi().fg(RED).cursor(DefaultValue.row_points, DefaultValue.col_points - 1).bold().a("Points: ").fg(DEFAULT).boldOff());
        System.out.println(title);

        int i = 1;
        for (PlayerIC p : gameModel.getPlayers()) {
            ris.append(ansi().cursor(DefaultValue.row_points + i, DefaultValue.col_points).a(p.getNickname() + ": " + p.getTotalPoints() + " points"));
            i++;
        }
        System.out.println(ris);
    }

    /**
     * Shows the player's goal card
     *
     * @param toShow
     */
    public void show_goalCards(PlayerIC toShow) {
        System.out.println(toShow.getSecretGoal_IC().getLayoutToMatch_IC().toStringGoalCard());
    }

    /**
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    @Override
    public void show_playerJoined(GameModelImmutable gameModel, String nick) {
        clearScreen();
        show_titleMyShelfie();
        System.out.println(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        System.out.flush();
        //StringBuilder players = new StringBuilder();
        StringBuilder ris = new StringBuilder();

        int i = 0;
        for (PlayerIC p : gameModel.getPlayers()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        System.out.println(ris);


        for (PlayerIC p : gameModel.getPlayers())
            if (!p.getReadyToStart() && p.getNickname().equals(nick))
                System.out.println(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        System.out.flush();

    }

    /**
     * @param gameModel     model where events happen
     * @param nicknameofyou player's nickname
     */
    @Override
    protected void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou) {

    }

    /**
     * Shows the publisher's name
     */
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
            Thread.sleep(DefaultValue.time_publisher_showing_seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.show_titleMyShelfie();
    }

    /**
     * Shows the important events
     */
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

    /**
     * Clears the console
     */
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

    /**
     * Shows the chat messages
     *
     * @param model
     */
    public void show_messages(GameModelImmutable model) {
        String ris = String.valueOf(ansi().fg(GREEN).cursor(DefaultValue.row_chat, DefaultValue.col_chat - 1).bold().a("Latest Messages:").fg(DEFAULT).boldOff()) +
                ansi().fg(WHITE).cursor(DefaultValue.row_chat + 1, DefaultValue.col_chat).a(model.getChat().toString(this.nickname)).fg(DEFAULT);
        System.out.println(ris);
        if (model.getChat().getMsgs().size() > 0) {
            System.out.println(ansi().cursor(DefaultValue.row_input, 0));
        }
    }

    /**
     * @param model the model in which search for the longest message
     * @return the length of the longest message
     */
    @Override
    public int getLengthLongestMessage(GameModelImmutable model) {
        return model.getChat().getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .toString().length();
    }

    /**
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    @Override
    public void addMessage(Message msg, GameModelImmutable model) {
        show_messages(model);
    }

    /**
     * Error message when there are no games to join
     *
     * @param msgToVisualize message that needs visualisation
     */
    @Override
    public void show_noAvailableGamesToJoin(String msgToVisualize) {
        String ris = ansi().fg(RED).cursor(11, 4).bold().a(msgToVisualize).fg(DEFAULT).boldOff() +
                String.valueOf(ansi().fg(RED).cursor(12, 4).bold().a(" Try later or create a new game!").fg(DEFAULT).boldOff());
        ansi().fg(DEFAULT);


        System.out.println(ris);
    }

    /**
     * Shows the last panel
     *
     * @param model where the game is ended
     */
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

    /**
     * Stuff that always needs to be visible
     *
     * @param model
     */
    public void show_alwaysShowForAll(GameModelImmutable model) {
        this.clearScreen();
        //resize();
        show_titleMyShelfie();
        show_playground(model);
        show_commonCards(model);
        show_messages(model);
        show_points(model);
        show_important_events();
    }

    /**
     * Shows the game id
     *
     * @param gameModel
     */
    public void show_gameId(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }

    /**
     * Shows the next player
     *
     * @param gameModel
     */
    public void show_nextTurn(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn, 0).bold().a("Next turn! It's up to: " + gameModel.getNicknameCurrentPlaying()).boldOff());
    }

    /**
     * Shows a welcome message
     *
     * @param nick
     */
    public void show_welcome(String nick) {
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn + 1, 0).bold().a("Welcome " + nick).boldOff());
    }

    /**
     * Clears important events' list
     */
    @Override
    public void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
        this.nickname = null;
    }

    /**
     * Shows error if there's no connection
     */
    @Override
    protected void show_noConnectionError() {
        this.clearScreen();
        System.out.println(ansi().fg(WHITE).bg(RED).bold().a("CONNECTION TO SERVER LOST!")
                .boldOff().fg(DEFAULT).bgDefault());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(-1);
    }

    /**
     * Asks the player to pick a direction
     */
    @Override
    public void show_direction() {
        System.out.println("\t> Choose direction (r=right,l=left,u=up,d=down): ");
    }

    /**
     * Asks the player to pick up tiles
     */
    @Override
    protected void show_askPickTilesMainMsg() {

    }

    /**
     * Shows the player's points
     *
     * @param p         the player to whom the point was added
     * @param point     the point added to that player
     * @param gameModel the model in which the player and point exist
     */
    @Override
    protected void show_addedPoint(Player p, Point point, GameModelImmutable gameModel) {

    }

    /**
     * @param model the model to check
     */
    @Override
    protected void columnShelfTooSmall(GameModelImmutable model) {

    }

    /**
     * clears console
     *
     * @param msg
     * @param model
     */
    public void removeInput(String msg, GameModelImmutable model) {
        System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(getLengthLongestMessage(model))));
    }

    /**
     * Shows the messages sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    @Override
    public void show_sentMessage(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Shows the player's hand
     *
     * @param model    the model that called the event
     * @param nickname the player that grabbed the tiles
     */
    @Override
    public void show_grabbedTileMainMsg(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Shows the updated shelves
     *
     * @param model    the model in which the player is found
     * @param nickname the player who positioned the tile
     */
    @Override
    public void show_positionedTile(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Error message on wrong tile selection on pick up
     *
     * @param model    the model where to show the tiles grabbed weren't correct
     * @param nickname the player who tried to grab the wrong tiles
     */
    @Override
    public void show_grabbedTileNotCorrect(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Asks the player to choose number of tiles to pick up
     *
     * @param msg       message to be shown
     * @param gameModel model where the message needs to be shown
     * @param nickname  nickname of the player that needs to have the message shown
     */
    @Override
    public void show_askNum(String msg, GameModelImmutable gameModel, String nickname) {
        this.show_alwaysShow(gameModel, nickname);
        this.removeInput(msg, gameModel);
    }

    /**
     * Shows messages on game start
     *
     * @param model model where the game has started
     */
    @Override
    public void show_gameStarted(GameModelImmutable model) {
        this.clearScreen();
        this.show_titleMyShelfie();
        this.show_allPlayers(model);
        this.show_alwaysShowForAll(model);
        this.show_gameId(model);
    }

    /**
     * Shows a message for the next turn or when a player reconnects
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    @Override
    public void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Tells the player to return to the main menu
     */
    @Override
    public void show_returnToMenuMsg() {
        System.out.println("\nPress any key to return to the menu");
    }

    /**
     * Asks the player to choose a column
     */
    @Override
    protected void show_askColumnMainMsg() {

    }

    /**
     * Asks the player to insert his nickname
     */
    @Override
    public void show_insertNicknameMsg() {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("> Insert your nickname: "));
    }

    /**
     * Shows chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    @Override
    public void show_chosenNickname(String nickname) {
        System.out.println(ansi().cursor(DefaultValue.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
    }

    /**
     * Shows initial menu
     */
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

    /**
     * Asks for the game id
     */
    @Override
    public void show_inputGameIdMsg() {
        System.out.println("> Input the GameId ('.' to leave): ");
    }

    /**
     * Generic error message
     */
    @Override
    public void show_NaNMsg() {
        System.out.println("> NaN");
    }

    /**
     * Asks which tile to place
     */
    @Override
    public void show_whichTileToPlaceMsg() {
        System.out.println("> Select which tile do you want to place:");
    }

    /**
     * Error when trying to place a wrong in hand tile
     */
    @Override
    public void show_wrongSelectionHandMsg() {
        System.out.println("\tWrong Tile selection offset");
    }

    /**
     * Creating game message
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_creatingNewGameMsg(String nickname) {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> Creating a new game...");
        this.nickname = nickname;
    }

    /**
     * Join first available game message
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningFirstAvailableMsg(String nickname) {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> Connecting to the first available game...");
        this.nickname = nickname;
    }

    /**
     * Shows the game id of the game that it's been joined
     *
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningToGameIdMsg(int idGame, String nickname) {
        this.clearScreen();
        this.show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        this.nickname = nickname;
    }

    /**
     * Messages that always need to be on screen
     *
     * @param model
     * @param nick
     */
    public void show_alwaysShow(GameModelImmutable model, String nick) {
        show_alwaysShowForAll(model);
        show_goalCards(model.getPlayerEntity(nick));
        if(model.getPlayerEntity(nick).getInHandTile_IC().size()>0)
            show_playerHand(model);
        else
            show_grabbedTile(model.getNicknameCurrentPlaying(), model);
        show_playerHand(model);
        show_commonCards(model);
        show_allShelves(model);
        show_gameId(model);
        show_nextTurn(model);
        show_messages(model);
        show_welcome(nick);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }


}
