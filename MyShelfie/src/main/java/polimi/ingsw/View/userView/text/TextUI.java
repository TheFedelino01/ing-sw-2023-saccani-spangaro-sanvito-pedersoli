package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.GameStatus;
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
import java.util.Objects;
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
    private int columnChosen = -1;
    private final Console console;

    private inputParser inputParser = null;
    private inputReader inputReader = null;
    private boolean ended = false;

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


        //Change input from scanf to threads
        this.inputReader = new inputReader();
        this.inputParser = new inputParser(this.inputReader.getBuffer(), this);
        //Now all the input must be read with inputParse!!!
    }

    @Override
    public void run() {
        EventElement event;
        try {
            console.resize();
            console.show_Publisher();
            Thread.sleep(2500);
            console.clearCMD();
            console.show_titleMyShelfie();
            events.add(null, APP_MENU);
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
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }
            case GAME_ID_NOT_EXISTS -> {
                nickname = null;
                System.out.println("It does not exist any game with this GameId");
                Integer gameId = askGameId();
                if (gameId != -1) {
                    joinGame(nickname, gameId);
                } else {
                    events.add(null, APP_MENU);
                }
            }
            case JOIN_UNABLE_NICKNAME_ALREADY_IN -> {
                nickname = null;
                events.add(null, APP_MENU);
                console.addImportantEvent("WARNING> Nickname already used!");
            }
            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                events.add(null, APP_MENU);
                console.addImportantEvent("WARNING> Game is Full!");
            }
            case GENERIC_ERROR_WHEN_ENTRYING_GAME -> {
                System.out.println("\nPress any key to return to the menu");
                try {
                    this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null,APP_MENU);
            }
        }
    }

    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getLastPlayer().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    console.showPlayerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, event.getModel());
                    askReadyToStart();
                }
            }
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_allPlayers(event.getModel());
                console.alwaysShowForAll(event.getModel());
                console.showGameId(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

                this.inputParser.setPlayer(event.getModel().getPlayerEntity(nickname));
                this.inputParser.setIdGame(event.getModel().getGameId());

            }
            case COMMON_CARD_EXTRACTED -> {
                console.clearCMD();
                console.show_titleMyShelfie();
                console.show_playground(event.getModel());
                console.showGameId(event.getModel());
                console.showCommonCards(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());


            }
            case SENT_MESSAGE -> console.alwaysShow(event.getModel(), nickname);

            case NEXT_TURN, PLAYER_RECONNECTED -> {
                console.alwaysShow(event.getModel(), nickname);
                columnChosen = -1;

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {
                        console.alwaysShow(event.getModel(), nickname);
                        System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

                        if (nickname.equals(lastPlayerReconnected)) {
                            askPickTiles(event.getModel());
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        askPickTiles(event.getModel());
                        if (ended) return;
                    }
                } else {
                    //I remove all the input that the user sends when It is not his turn
                    this.inputParser.getDataToProcess().popAllData();
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
                        if (ended) return;
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
            case GRABBED_TILE_NOT_CORRECT -> {
                console.alwaysShow(event.getModel(), nickname);
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    columnChosen = -1;
                    askPickTiles(event.getModel());
                }

                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

        }

    }

    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAMEENDED -> {
                System.out.println("\nPress any key to return to the menu");
                //new Scanner(System.in).nextLine();
                try {
                    this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    this.leave(nickname, event.getModel().getGameId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.youleft();
            }
        }
    }


    //-----------------------------------------
    //METODI DI RICHIESTA INPUT DA TASTIERA

    private void askNickname() {
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println(ansi().cursor(DefaultValue.row_gameID, 0).a("> Insert your nickname: "));
        //nickname = scanner.nextLine();
        try {
            nickname=this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ansi().cursor(DefaultValue.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
    }


    //return: false need to recall askSelectGame
    private boolean askSelectGame() {
        String optionChoose;

        ended = false;
        console.clearCMD();
        console.show_titleMyShelfie();
        System.out.println(ansi().cursor(9, 0).a("""
                > Select one option:
                \t(c) Create a new Game
                \t(j) Join to a random Game
                \t(js) Join a specific Game by idGame
                \t(x) Reconnect
                \t(.) to leave
                \t
                \t -> Useful commands that can be used at any point in the game:
                \t\t  type "/c [msg]" (public msg) or "/cs [playerName] [msg]" (private msg) and you can write in chat!
                \t\t  type "/quit" and you can leave the game!
                \t""").fg(DEFAULT));
        //optionChoose = scanner.nextLine();
        try {
            optionChoose=this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (optionChoose.equals("."))
            System.exit(1);
        askNickname();

        switch (optionChoose) {
            case "c" -> createGame(nickname);
            case "j" -> joinFirstAvailable(nickname);
            case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }
            case "x" -> reconnect(nickname, fileDisconnection.getLastGameId(nickname));
            default -> {
                System.out.println("> Selection incorrect!");
                return false;
            }
        }

        return true;
    }

    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            System.out.println("> Input the GameId ('.' to leave): ");
            try {
                //temp = scanner.nextLine();
                try {
                    temp=this.inputParser.getDataToProcess().popData();
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

    public void askReadyToStart() {
        String ris;
        try {
            do {
                System.out.println(ansi().cursor(18, 0).fg(DEFAULT));
                //ris = scanner.nextLine();
                try {
                    ris=this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!ris.equals("y"));
            setAsReady();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Integer askNum(String msg, GameModelImmutable gameModel) {
        String temp;
        int numT = -1;
        do {
            try {
                console.alwaysShow(gameModel, nickname);
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(console.getLengthLongestMessage())));
                System.out.flush();

                try {
                    temp = this.inputParser.getDataToProcess().popData();
                    if (ended) return null;
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

    public void askPickTiles(GameModelImmutable gameModel) {
        Integer numTiles;
        do {
            numTiles = Objects.requireNonNullElse(askNum("> How many tiles do you want to get? ", gameModel), DefaultValue.minNumOfGrabbableTiles - 1);
            if (ended) return;
        } while (!(numTiles >= DefaultValue.minNumOfGrabbableTiles && numTiles <= DefaultValue.maxNumOfGrabbableTiles));

        Integer row;
        do {
            row = Objects.requireNonNullElse(askNum("> Which tiles do you want to get?\n\t> Choose row: ", gameModel), DefaultValue.PlaygroundSize + 11);
            if (ended) return;
        } while (row > DefaultValue.PlaygroundSize);

        Integer column;
        do {
            column = Objects.requireNonNullElse(askNum("\t> Choose column: ", gameModel), DefaultValue.PlaygroundSize + 1);
            if (ended) return;
        } while (column > DefaultValue.PlaygroundSize);

        //Ask the direction only if the player wants to grab more than 1 tile
        Direction d = Direction.RIGHT;
        if (numTiles > 1) {
            String direction;
            do {
                System.out.println("\t> Choose direction (r=right,l=left,u=up,d=down): ");

                try {
                    direction = this.inputParser.getDataToProcess().popData();
                    if (ended) return;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
            if (ended) return;
        } while (column == null || column >= DefaultValue.NumOfColumnsShelf || column < 0);
        columnChosen = column;
    }

    public void askWhichTileToPlace(GameModelImmutable model) {
        console.alwaysShow(model, nickname);
        console.show_playerHand(model);
        System.out.println("> Select which tile do you want to place:");
        Integer indexHand;
        do {
            indexHand = Objects.requireNonNullElse(askNum("\t> Choose Tile in hand (0,1,2):", model), -1);
            if (ended) return;
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

    public void youleft() {
        ended=true;
        events.add(null,APP_MENU);
        //inputReader.interrupt();//TODO NEED TO READ INPUT ALWAYS WITH THIS SO I DONT NEED TO STOP AND RESTART IT
        //inputParser.interrupt();

        this.inputParser.setPlayer(null);
        this.inputParser.setIdGame(null);
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
    public void leave(String nick, int idGame) throws IOException {
        server.leave(nick, idGame);
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

    @Override
    public void sendMessage(Message msg) {
        server.sendMessage(msg);
    }


    //-----------------------------------------------------------------------
    //RICEZIONE DEGLI EVENTI DAL SERVER

    @Override
    public void playerJoined(GameModelImmutable gameModel) {
        //shared.setLastModelReceived(gameModel);
        //show_allPlayers();
        events.add(gameModel, EventType.PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        try {
            console.showPlayerJoined(gameModel, nickname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {
        if (gamemodel.getStatus().equals(GameStatus.WAIT)) {
            try {
                console.showPlayerJoined(gamemodel, nickname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            console.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }

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
        console.addImportantEvent("[EVENT]: Player reconnected!");
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) {
        //Visualizzo il messaggio solo se e' per tutti o e' solo per me
        if (msg.whoIsReceiver().equals("*")) {
            console.addMessage(msg);
            events.add(gameModel, SENT_MESSAGE);
        } else if (msg.whoIsReceiver().equals(nickname) || msg.getSender().getNickname().equals(nickname)) {
            msg.setText("[PRIVATE]: " + msg.getText());
            console.addMessage(msg);
            events.add(gameModel, SENT_MESSAGE);
        }
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
    public void genericErrorWhenEntryingGame(String why) throws RemoteException {
        console.showNoAvailableGamesToJoin(why);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException {
        try {
            console.showPlayerJoined(gameModel, nickname);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        ended = true;
        events.add(gameModel, EventType.GAMEENDED);
        console.showGameEnded(gameModel);
        resetGameId(fileDisconnection, gameModel);

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
        console.addImportantEvent("[EVENT]: A set of not grabbable tiles has been requested by Player: " + gameModel.getNicknameCurrentPlaying());
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
    public void playerDisconnected(GameModelImmutable gameModel, String nick) {
        console.addImportantEvent("[EVENT]:  Player " + nick + " has just disconnected");

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        try {
            console.showPlayerJoined(gameModel, nickname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
