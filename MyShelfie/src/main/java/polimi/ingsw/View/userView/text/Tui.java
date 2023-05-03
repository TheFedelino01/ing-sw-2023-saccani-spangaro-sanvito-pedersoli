package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.userView.GameFlow;
import polimi.ingsw.View.userView.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Tui extends UI {
    private List<String> importantEvents; //events that needs to be showed always in screen
    private Chat chat;
    private polimi.ingsw.View.userView.text.inputParser inputParser;
    private polimi.ingsw.View.userView.text.inputReader inputReader;
    private Integer columnChosen;
    private String nickname;

    public Tui(GameFlow gameFlow) {
        init(gameFlow);
    }

    @Override
    public void init(GameFlow gameFlow) {
        columnChosen = -1;
        AnsiConsole.systemInstall();
        importantEvents = new ArrayList<>();
        chat = new Chat();
        //Change input from scanf to threads
        this.inputReader = new inputReader();
        this.inputParser = new inputParser(this.inputReader.getBuffer(), gameFlow);
        //Now all the input must be read with inputParse!!!
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

    @Override
    public void resize() {
        try {
            new ProcessBuilder("cmd", "/c", "mode con:cols=160 lines=50").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            //couldn't resize the terminal window
        }
    }

    @Override
    public void show_allPlayers(GameModelImmutable model) {
        System.out.println("Current Players: \n" + model.toStringListPlayers());
    }

    @Override
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
        System.out.println(">This is your hand:");
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_input - 1, 0));
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

    @Override
    public void show_playground(GameModelImmutable model) {
        System.out.println(model.getPg().toString());
    }

    @Override
    public void showAllShelves(GameModelImmutable model) {
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
    public void showCommonCards(GameModelImmutable gameModel) {
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

    @Override
    public void showPoints(GameModelImmutable gameModel) {
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

    @Override
    public void showGoalCards(Player toShow) {
        System.out.println(toShow.getSecretGoal().getLayoutToMatch().toStringGoalCard());
    }

    @Override
    public void showPlayerJoined(GameModelImmutable gameModel, String nick) {
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

    @Override
    public void show_Publisher() {
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

    @Override
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

    @Override
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

    @Override
    public void showMessages() {
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
        showMessages();
    }

    @Override
    public void showNoAvailableGamesToJoin(String msgToVisualize) {
        String ris = ansi().fg(RED).cursor(11, 4).bold().a(msgToVisualize).fg(DEFAULT).boldOff() +
                String.valueOf(ansi().fg(RED).cursor(12, 4).bold().a("Try later or create a new game!").fg(DEFAULT).boldOff());
        ansi().fg(WHITE).cursor(13, 4).a(chat.toString()).fg(DEFAULT);


        System.out.println(ris);
    }

    @Override
    public void showGameEnded(GameModelImmutable model) {
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
                                
                """).fg(DEFAULT).reset());

        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(DefaultValue.row_nextTurn + 10, 0).bg(CYAN).a("""

                ██████████████████████████████████████████████████████████████████
                █▄─▄███▄─▄▄─██▀▄─██▄─▄▄▀█▄─▄▄─█▄─▄▄▀█▄─▄─▀█─▄▄─██▀▄─██▄─▄▄▀█▄─▄▄▀█
                ██─██▀██─▄█▀██─▀─███─██─██─▄█▀██─▄─▄██─▄─▀█─██─██─▀─███─▄─▄██─██─█
                ▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀▄▄▄▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀▄▄▄▄▀▄▄▀▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀
                        
                """).bg(DEFAULT).reset());


        int i = 1;
        int classif = 1;
        StringBuilder ris = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : model.getLeaderBoard().entrySet()) {
            System.out.println();
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_nextTurn + 20 + i, 30).a("#" + classif + " " + model.getPlayers().get(entry.getKey()).getNickname() + ": " + entry.getValue() + " points").fg(DEFAULT));
            i += 2;
            classif++;
        }

        System.out.println(ris);

    }

    @Override
    public void alwaysShowForAll(GameModelImmutable model) {
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

    @Override
    public void showGameId(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }

    @Override
    public void showNextTurn(GameModelImmutable gameModel) {
        System.out.println(ansi().cursor(DefaultValue.row_nextTurn, 0).bold().a("Next turn! It's up to: " + gameModel.getNicknameCurrentPlaying()).boldOff());
    }

    @Override
    public void showWelcome(String nick) {
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
    public void alwaysShow(GameModelImmutable model, String nick) {
        alwaysShowForAll(model);
        for (Player p : model.getPlayers()) {
            if (p.getNickname().equals(nick))
                showGoalCards(p);
            if (p.getInHandTile().size() > 0)
                if (p.getNickname().equals(nick)) {
                    show_playerHand(model);
                } else
                    show_grabbedTile(model.getNicknameCurrentPlaying(), model);
        }
        showAllShelves(model);
        showGameId(model);
        showNextTurn(model);
        showWelcome(nick);

        System.out.println(ansi().cursor(DefaultValue.row_input, 0));
    }

    @Override
    protected String askNickname() {
        clearCMD();
        show_titleMyShelfie();
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("> Insert your nickname: "));
        //nickname = scanner.nextLine();
        try {
            nickname = this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ansi().cursor(DefaultValue.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
        return nickname;
    }

    @Override
    protected boolean askSelectGame(GameFlow gameFlow) {
        String optionChoose;

        gameFlow.setEnded(false);
        clearCMD();
        show_titleMyShelfie();
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
        //optionChoose = scanner.nextLine();
        try {
            optionChoose = this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (optionChoose.equals("."))
            System.exit(1);
        nickname = askNickname();

        switch (optionChoose) {
            case "c" -> gameFlow.createGame(nickname);
            case "j" -> gameFlow.joinFirstAvailable(nickname);
            case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    gameFlow.joinGame(nickname, gameId);
            }
            case "x" -> gameFlow.reconnect(nickname, gameFlow.getFileDisconnection().getLastGameId(nickname));
            default -> {
                System.out.println("> Selection incorrect!");
                return false;
            }
        }

        return true;
    }

    @Override
    protected Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            System.out.println("> Input the GameId ('.' to leave): ");
            try {
                //temp = scanner.nextLine();
                try {
                    temp = this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (temp.equals(".")) {
                    return -1;
                }
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("> NaN");
            }

        } while (gameId == null);
        /*
        checks from all the gameId's in a model if one is equal to the one inserted
        while (!events.getGames().stream()
                .map(EventElement::getModel)
                .map(GameModelImmutable::getGameId)
                .toList()
                .contains(Integer.parseInt(Objects.requireNonNull(gameId, "Null gameId detected"))));*/
        return gameId;
    }

    @Override
    protected void askReadyToStart(GameFlow gameFlow) {
        String ris;
        try {
            do {
                //ris = scanner.nextLine();
                try {
                    ris = this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!ris.equals("y"));
            gameFlow.setAsReady();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Integer askNum(String msg, GameModelImmutable gameModel, GameFlow gameFlow) {
        String temp;
        int numT = -1;
        do {
            try {
                alwaysShow(gameModel, nickname);
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(getLengthLongestMessage())));
                System.out.flush();

                try {
                    temp = this.inputParser.getDataToProcess().popData();
                    if (gameFlow.isEnded()) return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                numT = Integer.parseInt(temp);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Nan");
            }
        } while (numT < 0);
        return numT;
    }

    @Override
    protected void askPickTiles(GameModelImmutable gameModel, GameFlow gameFlow) {
        Integer numTiles;
        do {
            numTiles = Objects.requireNonNullElse(askNum("> How many tiles do you want to get? ", gameModel, gameFlow), DefaultValue.minNumOfGrabbableTiles - 1);
            if (gameFlow.isEnded()) return;
        } while (!(numTiles >= DefaultValue.minNumOfGrabbableTiles && numTiles <= DefaultValue.maxNumOfGrabbableTiles));

        Integer row;
        do {
            row = Objects.requireNonNullElse(askNum("> Which tiles do you want to get?\n\t> Choose row: ", gameModel, gameFlow), DefaultValue.PlaygroundSize + 11);
            if (gameFlow.isEnded()) return;
        } while (row > DefaultValue.PlaygroundSize);

        Integer column;
        do {
            column = Objects.requireNonNullElse(askNum("\t> Choose column: ", gameModel, gameFlow), DefaultValue.PlaygroundSize + 1);
            if (gameFlow.isEnded()) return;
        } while (column > DefaultValue.PlaygroundSize);

        //Ask the direction only if the player wants to grab more than 1 tile
        Direction d = Direction.RIGHT;
        if (numTiles > 1) {
            String direction;
            do {
                System.out.println("\t> Choose direction (r=right,l=left,u=up,d=down): ");

                try {
                    direction = this.inputParser.getDataToProcess().popData();
                    if (gameFlow.isEnded()) return;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                d = Direction.getDirection(direction);
            } while (d == null);
        }
        //System.out.println("> You have selected: " + numTiles + " tiles from column " + column + " and row " + row + " in direction " + direction);

        try {
            gameFlow.grabTileFromPlayground(row, column, d, numTiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Integer askColumn(GameModelImmutable model, GameFlow gameFlow) {
        Integer column;
        do {
            column = askNum("> Choose column to place all the tiles:", model, gameFlow);
            show_playerHand(model);
            if (gameFlow.isEnded()) return -1;
        } while (column == null || column >= DefaultValue.NumOfColumnsShelf || column < 0);
        columnChosen = column;
        return column;
    }

    @Override
    protected void askWhichTileToPlace(GameModelImmutable model, GameFlow gameFlow) {
        System.out.println("> Select which tile do you want to place:");
        Integer indexHand;
        do {
            indexHand = Objects.requireNonNullElse(askNum("\t> Choose Tile in hand (0,1,2):", model, gameFlow), -1);
            show_playerHand(model);
            if (gameFlow.isEnded()) return;
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile().size()) {
                System.out.println("\tWrong Tile selection offset");
                indexHand = null;
            }
        } while (indexHand == null);

        try {
            gameFlow.positionTileOnShelf(columnChosen, model.getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
