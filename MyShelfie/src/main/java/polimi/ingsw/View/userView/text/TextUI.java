package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;
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
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import static polimi.ingsw.View.userView.Events.EventType.*;

public class TextUI extends View implements Runnable, CommonClientActions {
    private final Scanner scanner = new Scanner(System.in);
    private String nickname;

    private final EventList events = new EventList();

    private CommonClientActions server;
    private final FileDisconnection fileDisconnection;

    private String lastPlayerReconnected;
    private int columnChosen=-1;
    private final Console console;


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
            //console.resize();
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
                console.addImportantEvent("WARNING> Nickname already used!");
            }
            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                askSelectGame();
                console.addImportantEvent("WARNING> Game is Full!");
            }
        }
    }

    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getLastPlayer().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED:
                if (nickLastPlayer.equals(nickname)) {
                    console.showPlayerJoined(event.getModel(), nickname);
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
                System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("Game with id: " + event.getModel().getGameId() + ", First turn is played by: " + event.getModel().getNicknameCurrentPlaying()).toString());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }
            case COMMON_CARD_EXTRACTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_playground(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("Game with id: " + event.getModel().getGameId() + ", First turn is played by: " + event.getModel().getNicknameCurrentPlaying()).toString());
                console.showCommonCards(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case NEXT_TURN,PLAYER_RECONNECTED -> {
                console.alwaysShow(event.getModel(), nickname);
                columnChosen=-1;

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {
                        console.alwaysShow(event.getModel(), nickname);
                        console.addImportantEvent("[EVENT]: Player reconnected!");
                        System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

                        if (nickname.equals(lastPlayerReconnected)) {
                            askPickTiles(event.getModel());
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        askPickTiles(event.getModel());
                    }
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

            case GRABBED_TILE -> {
                console.alwaysShow(event.getModel(), nickname);
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn, so I'm the current playing

                    if (columnChosen == -1) {
                        //If I haven't selected the column than I select the column in which I want to place all the tiles that I have grabbed (now in Hand)
                        askColumn(event.getModel());
                    }
                    askWhichTileToPlace(event.getModel());
                } else {
                    console.show_grabbedTile(nickname, event.getModel());
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case POSITIONED_TILE -> {
                console.alwaysShow(event.getModel(), nickname);
                console.addImportantEvent("Player " + event.getModel().getNicknameCurrentPlaying() + " has positioned a Tile on his shelf!");
                if (event.getModel().getHandOfCurrentPlaying().size() > 0 && event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //Ask to place other tiles
                    events.add(event.getModel(), EventType.GRABBED_TILE);
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case GRABBED_TILE_NOT_CORRECT ->{
                console.alwaysShow(event.getModel(), nickname);
                if(event.getModel().getNicknameCurrentPlaying().equals(nickname)){
                    columnChosen=-1;
                    askPickTiles(event.getModel());
                }

                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

        }

    }

    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAMEENDED:
                console.addImportantEvent(ansi().a("[EVENT]: ").a(event.getModel().getGameId()).a(" ended! ").cursorDownLine().a(
                        "The winner is: ").a(event.getModel().getWinner().getNickname()).cursorDownLine().a(
                        "Score board: todo").toString());
                resetGameId(fileDisconnection, event.getModel());
                break;
        }
    }


    //-----------------------------------------
    //METODI DI RICHIESTA INPUT DA TASTIERA

    private void askNickname() {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("> Insert your nickname: "));
        nickname = scanner.nextLine();
        System.out.println(ansi().cursor(DefaultValue.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
    }


    private void askSelectGame() {
        boolean reAsk;
        String optionChoose;
        do {
            reAsk = false;
            console.clearCMD();
            console.show_titleMyShelfie();
            System.out.println(ansi().cursor(9, 0).a("""
                    > Select one option:
                    \t(c) Create a new Game
                    \t(j) Join to a random Game
                    \t(js) Join a specific Game by idGame
                    \t(x) Reconnect
                    \t(.) to leave
                    \tRemember! At any point in the game, if you type "/c [msg]"
                    \tyou can write in chat!
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


    private Integer askNum(String msg, GameModelImmutable gameModel) {
        System.out.print(msg);
        System.out.flush();
        String temp;
        int numT = -1;

        do {
            try {
                temp = new Scanner(System.in).nextLine();
                if(temp.equals(""))
                    continue;
                if (temp.startsWith("/c")){
                    sentMessage(new Message(temp.substring(2), gameModel.getPlayerEntity(nickname)));
                    continue;
                }
                numT = Integer.parseInt(temp);
            } catch (InputMismatchException e) {
                System.out.println("Nan");
            }
        } while (numT < 0);
        return numT;
    }

    public void askPickTiles(GameModelImmutable gameModel) {
        Integer numTiles;
        do {
            numTiles = askNum("> How many tiles do you want to get? ", gameModel);
        } while (!(numTiles >= DefaultValue.minNumOfGrabbableTiles && numTiles <= DefaultValue.maxNumOfGrabbableTiles));

        Integer row;
        do {
            row = askNum("> Which tiles do you want to get?\n\t> Choose row: ", gameModel);
        } while (row > DefaultValue.PlaygroundSize);

        Integer column;
        do {
            column = askNum("\t> Choose column: ", gameModel);
        } while (column > DefaultValue.PlaygroundSize);

        //Ask the direction only if the player wants to grab more than 1 tile
        Direction d = Direction.RIGHT;
        if (numTiles > 1) {
            String direction;
            do {
                System.out.println("\t> Choose direction (r=right,l=left,u=up,d=down): ");
                direction = new Scanner(System.in).nextLine();
                if(direction.equals(""))
                    continue;
                if (direction.startsWith("/c")){
                    sentMessage(new Message(direction.substring(2), gameModel.getPlayerEntity(nickname)));
                    continue;
                }
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

    private void askColumn(GameModelImmutable model) {
        /* Gets the current playing player

        Player currentPlaying = model.getPlayers().stream()
                .filter(x -> x.getNickname().equals(model.getNicknameCurrentPlaying()))
                .toList().get(0);

         */
        console.alwaysShow(model, nickname);
        console.show_playerHand(model);
        Integer column;
        do {
            column = askNum("> Choose column to place all the tiles:", model);
        } while (column == null || column>=DefaultValue.NumOfColumnsShelf || column<0);
        columnChosen=column;
    }

    public void askWhichTileToPlace(GameModelImmutable model) {
        console.alwaysShow(model, nickname);
        console.show_playerHand(model);
        System.out.println("> Select which tile do you want to place:");
        Integer indexHand;
        do {
            indexHand = askNum("\t> Choose Tile in hand (0,1,2):", model);
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile().size()) {
                System.out.println("\tWrong Tile selection offset");
                indexHand = null;
            }
        } while (indexHand == null);

        try {
            positionTileOnShelf(columnChosen, model.getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
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
        console.showPlayerJoined(gameModel, nickname);
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
        console.showPlayerJoined(gameModel, nickname);
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
        console.addMessage(msg);
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
        console.addImportantEvent("[EVENT]: A set of not grabbable tiles has been requested by Player: "+gameModel.getNicknameCurrentPlaying());
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
        console.addImportantEvent("[EVENT]:  Player " + p.getNickname() + " obtained " + point.getPoint() + " points by achieving " + point.getReferredTo());
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        console.addImportantEvent("[EVENT]:  Player " + nick + " has just disconnected");
    }


}
