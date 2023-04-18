package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
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
import polimi.ingsw.View.userView.FileDisconnection;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import static org.fusesource.jansi.Ansi.ansi;
import static polimi.ingsw.View.userView.Events.EventType.*;

public class TextUI extends View implements Runnable, CommonClientActions {
    private final Scanner scanner = new Scanner(System.in);
    private String nickname;

    private EventList events = new EventList();

    private CommonClientActions server;
    private FileDisconnection fileDisconnection;

    private String lastPlayerReconnected;

    private Console console;


    public TextUI(ConnectionSelection selection) {
        console = new Console();
        nickname = "";
        if (selection.equals(ConnectionSelection.SOCKET)) {
            server = new ClientSocket(this);
        } else if (selection.equals(ConnectionSelection.RMI)) {
            server = new RMIClient(this);
        }
        fileDisconnection = new FileDisconnection();
        new Thread(this).start();
    }

    @Override
    public void run() {
        EventElement event;
        try {
            console.show_Publisher();
            Thread.sleep(2500);
            console.clearCMD();
            console.show_titleMyShelfie();
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
            case GAME_ID_NOT_EXISTS -> {
                nickname = null;
                System.out.println("It does not exist any game with this GameId");
                Integer gameId = askGameId();
                if (gameId != -1) {
                    joinGame(nickname, gameId);
                } else {
                    askSelectGame();
                }
            }
            case JOIN_UNABLE_NICKNAME_ALREADY_IN -> {
                nickname = null;
                askSelectGame();
                System.out.println("WARNING> Nickname already used!");
            }
            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                askSelectGame();
                System.out.println("WARNING> Game is Full!");
            }
        }
    }

    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getLastPlayer().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED:
                if (nickLastPlayer.equals(nickname)) {
                    console.showPlayerJoined(event.getModel());
                    saveGameId(fileDisconnection, event.getModel());
                    askReadyToStart(event.getModel());
                }
                break;
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_allPlayers(event.getModel());
                System.out.println("Game with id: " + event.getModel().getGameId() + ", First turn is played by: " + event.getModel().getNicknameCurrentPlaying());
            }
            case COMMON_CARD_EXTRACTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_playground(event.getModel());
                System.out.println("Common card extracted: " + event.getModel().getLastCommonCard().getCommonType());
            }
            case NEXT_TURN -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                System.out.println("Next turn! It's up to: " + event.getModel().getNicknameCurrentPlaying());
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn
                    console.show_playground(event.getModel());
                    console.showAllShelves(event.getModel());

                    if (event.getType().equals(PLAYER_RECONNECTED)) {
                        System.out.println("[EVENT]: Player reconnected!");
                        if (nickname.equals(lastPlayerReconnected)) {
                            askPickTiles();
                        }
                        //else the player who has just reconnected is not me
                    } else {
                        askPickTiles();
                    }


                } else {
                    //It's not my turn then I show the playground and the shelf of the player playing
                    console.show_playground(event.getModel());
                    console.showAllShelves(event.getModel());
                }
            }

            case GRABBED_TILE -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn, so I'm the current playing
                    console.show_playground(event.getModel());
                    console.showAllShelves(event.getModel());
                    askPlaceTile(event.getModel());
                } else {
                    console.show_playground(event.getModel());
                    console.showAllShelves(event.getModel());
                    console.show_grabbedTile(nickname, event.getModel());
                }
            }
            case POSITIONED_TILE -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_playground(event.getModel());
                //System.out.println("Player "+event.getModel().getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
                console.showAllShelves(event.getModel());
                System.out.println("Player " + event.getModel().getNicknameCurrentPlaying() + " has positioned a Tile on his shelf!");
                if (event.getModel().getHandOfCurrentPlaying().size() > 0) {
                    events.add(event.getModel(), EventType.GRABBED_TILE);
                }
            }
            case PLAYER_RECONNECTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_playground(event.getModel());
                //System.out.println("Player "+event.getModel().getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
                console.showAllShelves(event.getModel());
                System.out.println("[EVENT]: Player reconnected!");
                if (event.getModel().isMyTurn(nickname)) {
                    events.add(event.getModel(), NEXT_TURN);
                }
            }
        }

    }

    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAMEENDED:
                System.out.println("[EVENT]: " + event.getModel().getGameId() + " ended! \n" +
                        "The winner is: " + event.getModel().getWinner().getNickname() + "\n" +
                        "Score board: todo");
                resetGameId(fileDisconnection, event.getModel());
                break;
        }
    }


    //-----------------------------------------
    //METODI DI RICHIESTA INPUT DA TASTIERA

    private void askNickname() {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println("> Insert your nickname: ");
        nickname = scanner.nextLine();
        System.out.println("> Your nickname is: " + nickname);
    }


    private void askSelectGame() {
        boolean reAsk;
        String optionChoose;
        do {
            reAsk = false;
            console.clearCMD();
            console.show_titleMyShelfie();
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
                case "x" -> reconnect(nickname, fileDisconnection.getLastGameId(nickname));
                default -> {
                    System.out.println("> Selection incorrect!");
                    reAsk = true;
                }
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

    public void displayHand(GameModelImmutable model) {
        System.out.println(">This is your hand:");
        String ris = "";
        for (int i = 0; i < DefaultValue.maxTilesInHand; i++) {
            if (i < model.getPlayerEntity(nickname).getInHandTile().size()) {
                switch (model.getPlayerEntity(nickname).getInHandTile().get(i).getType()) {
                    case CAT ->
                            ris += "[" + i + "]: " + ansi().fg(GREEN).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                    case TROPHY ->
                            ris += "[" + i + "]: " + ansi().fg(CYAN).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                    case PLANT ->
                            ris += "[" + i + "]: " + ansi().fg(MAGENTA).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                    case BOOK ->
                            ris += "[" + i + "]: " + ansi().fg(WHITE).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                    case ACTIVITY ->
                            ris += "[" + i + "]: " + ansi().fg(YELLOW).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                    case FRAME ->
                            ris += "[" + i + "]: " + ansi().fg(BLUE).a(model.getPlayerEntity(nickname).getInHandTile().get(i).getType().toString()).fg(DEFAULT) + " | ";
                }
            }
            else
                ris += "[" + i + "]: " + "NONE" + " | ";
        }

        System.out.println(ris);

    }
    public void askPlaceTileAgain(GameModelImmutable model, int col) {
        displayHand(model);
        System.out.println("> Which tile do you want to place?");
        Integer indexHand;
        do {
            indexHand = askNum("> Choose Tile in hand (0,1,2):");
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile().size()) {
                System.out.println("Wrong Tile selection offset");
                indexHand = null;
            }
        } while (indexHand == null);

        try {
            positionTileOnShelf(col, model.getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void askPlaceTile(GameModelImmutable model) {
        displayHand(model);
        Integer column;
        do {
            column = askNum("> Choose column to place the tile:");
        } while (column == null);
        for (int i=model.getPlayerEntity(nickname).getInHandTile().size(); i>0; i--){
            askPlaceTileAgain(model, column);
        }
    }


    //-----------------------------------------
    //METODI CHE IL CLIENT PUÓ RICHIEDERE VERSO IL SERVER

    @Override
    public void createGame(String nick) {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println("> Creating a new game...");

        try {
            server.createGame(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinFirstAvailable(String nick) {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println("> Connecting to the first available game...");
        try {
            server.joinFirstAvailable(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinGame(String nick, int idGame) {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        try {
            server.joinGame(nick, idGame);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reconnect(String nick, int idGame) {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to reconnect");
        try {
            server.reconnect(nickname, fileDisconnection.getLastGameId(nickname));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setAsReady() throws IOException {
        server.setAsReady();
    }

    @Override
    public boolean isMyTurn() {
        //todo invoke is my turn
        return false;
    }

    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException  {
        server.grabTileFromPlayground(x, y, direction, num);
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        server.positionTileOnShelf(column, type);
    }

    @Override
    public void heartbeat() {
        server.heartbeat();
    }


    //-----------------------------------------------------------------------
    //RICEZIONE DEGLI EVENTI DAL SERVER

    @Override
    public void playerJoined(GameModelImmutable gameModel) throws IOException, InterruptedException {
        //shared.setLastModelReceived(gameModel);
        //show_allPlayers();
        events.add(gameModel, EventType.PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        console.showPlayerJoined(gameModel);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gameModel) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin+" tried to entry but the game is full!");
        events.add(null, JOIN_UNABLE_GAME_FULL);
    }

    @Override
    public void playerReconnected(GameModelImmutable gameModel, String nickPlayerReconnected) {
        lastPlayerReconnected = nickPlayerReconnected;
        events.add(gameModel, EventType.PLAYER_RECONNECTED);
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        events.add(null, GAME_ID_NOT_EXISTS);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException, InterruptedException {
        console.showPlayerJoined(gameModel);
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
