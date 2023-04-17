package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.userView.CommonClientActions;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.Events.EventElement;
import polimi.ingsw.View.userView.Events.EventList;
import polimi.ingsw.View.userView.Events.EventType;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static polimi.ingsw.View.userView.Events.EventType.GAME_ID_NOT_EXISTS;
import static polimi.ingsw.View.userView.Events.EventType.PLAYER_IS_READY_TO_START;

public class TextUI extends View implements Runnable, CommonClientActions {
    private final Scanner scanner = new Scanner(System.in);
    private String nickname;

    private boolean joined = false, toldIAmReady = false;
    EventList events = new EventList();

    private CommonClientActions commonClientActions;


    public TextUI(ConnectionSelection selection) {
        AnsiConsole.systemInstall();
        nickname = "";
        if (selection.equals(ConnectionSelection.SOCKET)) {
            commonClientActions = new ClientSocket(this);
        } else if (selection.equals(ConnectionSelection.RMI)) {
            commonClientActions = new RMIClient(this);
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        EventElement event;
        try {
            show_Publisher();
            Thread.sleep(2500);
            clearCMD();
            show_titleMyShelfie();
            askSelectGame();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!Thread.interrupted()) {
            if (events.isJoined()) {
                //Get one event
                event = events.pop();
                if (event != null) {
                    //if something happened
                    switch (event.getModel().getStatus()) {
                        case WAIT -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> statusEnded(event);
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void statusNotInAGame(EventElement event) {
        switch (event.getType()) {
            case GAME_ID_NOT_EXISTS:
                System.out.println("It does not exist any game with this GameId");
                Integer gameId = askGameId();
                try {
                    joinGame(nickname, gameId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                break;
        }
    }

    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getLastPlayer().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED:
                if (nickLastPlayer.equals(nickname))
                    askReadyToStart(event.getModel());
                break;
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                clearCMD();
                show_titleMyShelfie();
                show_allPlayers(event.getModel());
                System.out.println("Game with id: " + event.getModel().getGameId() + ", First turn is played by: " + event.getModel().getNicknameCurrentPlaying());
                saveGameId(event);
            }
            case COMMON_CARD_EXTRACTED -> {
                clearCMD();
                show_titleMyShelfie();
                show_playground(event.getModel());
                System.out.println("Common card extracted: " + event.getModel().getLastCommonCard().getCommonType());
            }
            case NEXT_TURN, PLAYER_RECONNECTED -> {
                clearCMD();
                show_titleMyShelfie();
                System.out.println("Next turn! It's up to: " + event.getModel().getNicknameCurrentPlaying());
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn
                    show_playground(event.getModel());
                    showAllShelves(event.getModel());
                    askPickTiles();
                } else {
                    //It's not my turn then I show the playground and the shelf of the player playing
                    show_playground(event.getModel());
                    showAllShelves(event.getModel());
                }
            }
            case GRABBED_TILE -> {
                clearCMD();
                show_titleMyShelfie();
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn, so I'm the current playing
                    show_playground(event.getModel());
                    showAllShelves(event.getModel());
                    askPlaceTile(event.getModel());
                } else {
                    show_playground(event.getModel());
                    showAllShelves(event.getModel());
                    show_grabbedTile(event.getModel());
                }
            }
            case POSITIONED_TILE -> {
                clearCMD();
                show_titleMyShelfie();
                show_playground(event.getModel());
                //System.out.println("Player "+event.getModel().getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
                showAllShelves(event.getModel());
                System.out.println("Player " + event.getModel().getNicknameCurrentPlaying() + " has positioned a Tile on his shelf!");
                if (event.getModel().getHandOfCurrentPlaying().size() > 0) {
                    events.add(event.getModel(), EventType.GRABBED_TILE);
                }
            }
        }

    }

    private void statusEnded(EventElement event) {
    }

    //-----------------------------------------
    //METODI SHOW DA CONSOLE

    private void show_allPlayers(GameModelImmutable model) {
        System.out.println("Current Players: \n" + model.toStringListPlayers());
    }

    private void show_titleMyShelfie() {
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

    private void show_grabbedTile(GameModelImmutable model) {
        StringBuilder ris = new StringBuilder("| ");
        for (Tile t : model.getHandOfCurrentPlaying()) {
            switch (t.getType()) {
                case CAT -> ris.append(ansi().fg(GREEN).a(t.toString()).fg(DEFAULT)).append(" | ");
                case TROPHY -> ris.append(ansi().fg(CYAN).a(t.toString()).fg(DEFAULT)).append(" | ");
                case PLANT -> ris.append(ansi().fg(MAGENTA).a(t.toString()).fg(DEFAULT)).append(" | ");
                case BOOK -> ris.append(ansi().fg(WHITE).a(t.toString()).fg(DEFAULT)).append(" | ");
                case ACTIVITY -> ris.append(ansi().fg(YELLOW).a(t.toString()).fg(DEFAULT)).append(" | ");
                case FRAME -> ris.append(ansi().fg(BLUE).a(t.toString()).fg(DEFAULT)).append(" | ");
            }
        }
        System.out.println(nickname + ": Player: " + model.getNicknameCurrentPlaying() + " has grabbed some tiles: " + ris);
    }


    private void show_playground(GameModelImmutable model) {
        System.out.println("GameID: [" + model.getGameId().toString() + "] \n" + model.getPg().toString());
    }

    private void showAllShelves(GameModelImmutable model) {
        int i = DefaultValue.displayShelfStartingCol;
        for (Player p : model.getPlayers()) {
            System.out.print(ansi().cursor(DefaultValue.displayShelfRow, i - 3).toString() +
                    p.getNickname() + ": " + p.getShelf().toString(i));
            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(" ");
    }


    //-----------------------------------------
    //METODI DI RICHIESTA INPUT DA TASTIERA

    private void askNickname() throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println("> Insert your nickname: ");
        nickname = scanner.nextLine();
        System.out.println("> Your nickname is: " + nickname);
    }


    private void askSelectGame() throws IOException, InterruptedException {
        boolean reAsk;
        String optionChoose;
        do {
            reAsk = false;
            clearCMD();
            show_titleMyShelfie();
            System.out.println(ansi().a("""
                    > Select one option:
                    \t(c) Create a new Game
                    \t(j) Join to a random Game
                    \t(js) Join a specific Game by idGame
                    \t(x) Reconnect
                    \t(.) to leave
                    \t""").fg(DEFAULT));
            optionChoose = scanner.nextLine();
            if (optionChoose.equals("."))
                return;
            askNickname();
            try {
                switch (optionChoose) {
                    case "c" -> createGame(nickname);
                    case "j" -> joinFirstAvailable(nickname);
                    case "js" -> {
                        Integer gameId = askGameId();
                        if (gameId == -1)
                            askSelectGame();
                        else
                            joinGame(nickname, gameId);
                    }
                    case "x" -> reconnect(nickname, new Player(nickname).getLastGameId());
                    default -> {
                        System.out.println("> Selection incorrect!");
                        reAsk = true;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (reAsk);
    }

    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            System.out.println("> Input the GameId ('.' to leave): ");
            try {
                temp = scanner.nextLine();
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

    public void askReadyToStart(GameModelImmutable gameModel) {
        String ris;
        try {
            do {
                System.out.println(ansi().cursor(18, 0).fg(DEFAULT));
                ris = scanner.nextLine();
            } while (!ris.equals("y"));
            setAsReady();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void resetGameId(EventElement element) {
        for (Player p : element.getModel().getPlayers()) {
            p.setLastGameId(-1);
        }
    }

    @Override
    protected void saveGameId(EventElement element) {
        for (Player p : element.getModel().getPlayers()) {
            p.setLastGameId(element.getModel().getGameId());
        }
    }


    private Integer askNum(String msg) {
        System.out.print(msg);
        System.out.flush();

        int numT = -1;

        do {
            try {
                numT = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Nan");
            }
        } while (numT < 0);
        return numT;
    }

    public void askPickTiles() {
        Integer numTiles;
        do {
            numTiles = askNum("> How many tiles do you want to get? ");
        } while (!(numTiles >= DefaultValue.minNumOfGrabbableTiles && numTiles <= DefaultValue.maxNumOfGrabbableTiles));

        Integer row;
        do {
            row = askNum("> Which tiles do you want to get?\n> Choose row: ");
        } while (row > DefaultValue.PlaygroundSize);

        Integer column;
        do {
            column = askNum("> Choose column: ");
        } while (column > DefaultValue.PlaygroundSize);

        //Ask the direction only if the player wants to grab more than 1 tile
        Direction d = Direction.RIGHT;
        if (numTiles > 1) {
            String direction;
            do {
                System.out.println("> Choose direction (r=right,l=left,u=up,d=down): ");
                direction = new Scanner(System.in).nextLine();
                d = Direction.getDirection(direction);
            } while (d == null);
        }
        //System.out.println("> You have selected: " + numTiles + " tiles from column " + column + " and row " + row + " in direction " + direction);

        try {
            grabTileFromPlayground(row, column, d, numTiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void askPlaceTile(GameModelImmutable model) {
        System.out.println(">This is your hand:");
        StringBuilder ris = new StringBuilder();
        for (int i = 0; i < DefaultValue.maxTilesInHand; i++) {
            if (i < model.getPlayerEntity(nickname).getInHandTile().size()) {
                switch (model.getPlayerEntity(nickname).getInHandTile().get(i).getType()) {
                    case CAT ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(GREEN).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                    case TROPHY ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(CYAN).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                    case PLANT ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(MAGENTA).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                    case BOOK ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(WHITE).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                    case ACTIVITY ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(YELLOW).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                    case FRAME ->
                            ris.append("[").append(i).append("]: ").append(ansi().fg(BLUE).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT)).append(" | ");
                }
            } else
                ris.append("[").append(i).append("]: ").append("NONE").append(" | ");
        }

        System.out.println(ris);

        System.out.println("> Which tile do you want to place?");
        Integer indexHand;
        do {
            indexHand = askNum("> Choose Tile in hand (0,1,2):");
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile().size()) {
                System.out.println("Wrong Tile selection offset");
                indexHand = null;
            }
        } while (indexHand == null);


        Integer column;
        do {
            column = askNum("> Choose column to place the tile:");
        } while (column > DefaultValue.NumOfColumnsShelf);


        try {
            positionTileOnShelf(column, model.getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void showPlayerJoined(GameModelImmutable gameModel) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        System.out.flush();
        //StringBuilder players = new StringBuilder();
        StringBuilder ris = new StringBuilder();

        int i = 0;
        for (Player p : gameModel.getPlayers()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + +i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + +i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        System.out.println(ris);
        //TODO:
        // need to check if the player is ready or not, and
        // in case he's ready not show him this line, now everyone
        // will see it
        System.out.println(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        System.out.flush();
    }

    private void show_Publisher() throws IOException, InterruptedException {
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

    private void clearCMD() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }


    //-----------------------------------------
    //METODI CHE IL CLIENT PUÓ RICHIEDERE VERSO IL SERVER

    @Override
    public void createGame(String nick) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println("> Creating a new game...");
        commonClientActions.createGame(nick);
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println("> Connecting to the first available game...");
        commonClientActions.joinFirstAvailable(nick);
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        commonClientActions.joinGame(nick, idGame);
    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to reconnect");
        commonClientActions.reconnect(nickname, new Player(nickname).getLastGameId());
    }

    @Override
    public void setAsReady() throws IOException {
        commonClientActions.setAsReady();
    }

    @Override
    public boolean isMyTurn() {
        //todo invoke is my turn
        return false;
    }

    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
        commonClientActions.grabTileFromPlayground(x, y, direction, num);
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        commonClientActions.positionTileOnShelf(column, type);
    }

    @Override
    public void heartbeat() {
        commonClientActions.heartbeat();
    }


    //-----------------------------------------------------------------------
    //RICEZIONE DEGLI EVENTI DAL SERVER

    @Override
    public void playerJoined(GameModelImmutable gameModel) throws IOException, InterruptedException {
        //shared.setLastModelReceived(gameModel);
        //show_allPlayers();
        events.add(gameModel, EventType.PLAYER_JOINED);
        if (gameModel.getPlayers().get(gameModel.getPlayers().size() - 1).getNickname().equals(nickname))
            joined = true;
        showPlayerJoined(gameModel);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gameModel) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin+" tried to entry but the game is full!");
    }

    @Override
    public void playerReconnected(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]: Player reconnected!");
        events.add(gamemodel, EventType.PLAYER_RECONNECTED);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        events.add(null, GAME_ID_NOT_EXISTS);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException, InterruptedException {
        showPlayerJoined(gameModel);
        // if(nick.equals(nickname))
        //    toldIAmReady=true;
        events.add(gameModel, PLAYER_IS_READY_TO_START);
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gameModel) throws RemoteException {
        //System.out.println("[EVENT]: common card extracted");

        //shared.set(gameModel, true, shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());
        events.add(gameModel, EventType.COMMON_CARD_EXTRACTED);
    }

    @Override
    public void gameStarted(GameModelImmutable gameModel) {
        //System.out.println("[EVENT]: Game Started with id: "+gameModel.getGameId()+ ", First turn is played by: "+gameModel.getNicknameCurrentPlaying());
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, EventType.GAMESTARTED);
    }

    @Override
    public void gameEnded(GameModelImmutable gameModel) {
        System.out.println("[EVENT]: " + gameModel.getGameId() + " ended! \n" +
                "The winner is: " + gameModel.getWinner().getNickname() + "\n" +
                "Score board: todo");
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, EventType.GAMEENDED);
    }

    @Override
    public void sentMessage(Message msg) {
        System.out.println("[EVENT]: new Message: \"" + msg.toString() + "\"");

    }

    @Override
    public void grabbedTile(GameModelImmutable gameModel) {
        //System.out.println("[EVENT]: a tile has been grabbed");
        //shared.set(gameModel, shared.isNeedto_showCommonCards(), true,shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());
        events.add(gameModel, EventType.GRABBED_TILE);
    }

    //shared.set(gameModel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());
    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gameModel) {
        //System.out.println("[EVENT]: a tile has not been grabbed correctly");
        // shared.set(gameModel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),true);
        events.add(gameModel, EventType.GRABBED_TILE_NOT_CORRECT);
    }

    @Override
    public void positionedTile(GameModelImmutable gameModel, TileType type, int column) {
        //System.out.println("[EVENT]: Player "+gameModel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
        //shared.set(gameModel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),true);
        events.add(gameModel, EventType.POSITIONED_TILE);
    }

    @Override
    public void nextTurn(GameModelImmutable gameModel) {
        //System.out.println("[EVENT]:  Next turn! It's up to: "+gameModel.getNicknameCurrentPlaying());
        //shared.setLastModelReceived(gameModel);

        // if(!gameModel.getNicknameCurrentPlaying().equals(nickname)){
        //    shared.reinit(gameModel);
        // }
        events.add(gameModel, EventType.NEXT_TURN);
    }

    @Override
    public void addedPoint(Player p, Point point) {
        System.out.println("[EVENT]:  Player " + p.getNickname() + " obtained " + point.getPoint() + " points by achieving " + point.getReferredTo());

    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        System.out.println("[EVENT]:  Player " + nick + " has just disconnected");
    }


}
