package polimi.ingsw.View.userView;

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
import polimi.ingsw.View.userView.Events.EventElement;
import polimi.ingsw.View.userView.Events.EventList;
import polimi.ingsw.View.userView.Events.EventType;
import polimi.ingsw.View.userView.text.Tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import static polimi.ingsw.View.userView.Events.EventType.*;

public class GameFlow extends View implements Runnable, CommonClientActions {

    private String nickname;

    private final EventList events = new EventList();

    private CommonClientActions server;
    private final FileDisconnection fileDisconnection;

    private String lastPlayerReconnected;
    private int columnChosen = -1;
    private final UI ui;


    private boolean ended = false;


    public GameFlow(ConnectionSelection connectionSelection, UiSelection uiSelection) {
        nickname = "";
        switch (uiSelection) {
            case TUI -> ui = new Tui(this);
            case GUI -> {
                System.out.println("Not yet implemented!");
                ui = new Tui(this);
                //TODO: implement an actual gui
                //ui = new Gui();
            }
            default -> {
                ui = new Tui(this);
                System.out.println("Error, game will start in TUI mode");
            }

        }
        switch (connectionSelection) {
            case SOCKET -> server = new ClientSocket(this);
            case RMI -> server = new RMIClient(this);
        }
        fileDisconnection = new FileDisconnection();
        new Thread(this).start();

    }

    @Override
    public void run() {
        EventElement event;
        try {
            ui.resize();
            ui.show_Publisher();
            Thread.sleep(2500);
            ui.clearCMD();
            ui.show_titleMyShelfie();
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
                    selectionok = ui.askSelectGame(this);
                } while (!selectionok);
            }
            case GAME_ID_NOT_EXISTS -> {
                nickname = null;
                System.out.println("It does not exist any game with this GameId");
                Integer gameId = ui.askGameId();
                if (gameId != -1) {
                    joinGame(nickname, gameId);
                } else {
                    events.add(null, APP_MENU);
                }
            }
            case JOIN_UNABLE_NICKNAME_ALREADY_IN -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Nickname already used!");
            }
            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Game is Full!");
            }
            case GENERIC_ERROR_WHEN_ENTRYING_GAME -> {
                System.out.println("\nPress any key to return to the menu");
                try {
                    ui.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, APP_MENU);
            }
        }
    }

    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getLastPlayer().getNickname();
        //If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    ui.showPlayerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, event.getModel());
                    ui.askReadyToStart(this);
                }
            }
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.clearCMD();
                ui.show_titleMyShelfie();
                ui.show_allPlayers(event.getModel());
                ui.alwaysShowForAll(event.getModel());
                ui.showGameId(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

                ui.inputParser.setPlayer(event.getModel().getPlayerEntity(nickname));
                ui.inputParser.setIdGame(event.getModel().getGameId());

            }
            case COMMON_CARD_EXTRACTED -> {
                ui.clearCMD();
                ui.show_titleMyShelfie();
                ui.show_playground(event.getModel());
                ui.showGameId(event.getModel());
                ui.showCommonCards(event.getModel());
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());


            }
            case SENT_MESSAGE -> ui.alwaysShow(event.getModel(), nickname);

            case NEXT_TURN, PLAYER_RECONNECTED -> {
                ui.alwaysShow(event.getModel(), nickname);
                columnChosen = -1;

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {

                        if (nickname.equals(lastPlayerReconnected)) {
                            ui.askPickTiles(event.getModel(), this);
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        ui.askPickTiles(event.getModel(), this);
                        if (ended) return;
                    }
                } else {
                    //I remove all the input that the user sends when It is not his turn
                    ui.inputParser.getDataToProcess().popAllData();
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

            case GRABBED_TILE -> {
                ui.alwaysShow(event.getModel(), nickname);
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn, so I'm the current playing

                    if (columnChosen == -1) {
                        //If I haven't selected the column than I select the column in which I want to place all the tiles that I have grabbed (now in Hand)
                        columnChosen = ui.askColumn(event.getModel(), this);
                        if (ended) return;
                    }
                    ui.askWhichTileToPlace(event.getModel(), this);
                } else {
                    ui.show_grabbedTile(nickname, event.getModel());
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case POSITIONED_TILE -> {
                ui.alwaysShow(event.getModel(), nickname);
                ui.addImportantEvent("Player " + event.getModel().getNicknameCurrentPlaying() + " has positioned a Tile on his shelf!");
                if (event.getModel().getHandOfCurrentPlaying().size() > 0 && event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //Ask to place other tiles
                    events.add(event.getModel(), EventType.GRABBED_TILE);
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case GRABBED_TILE_NOT_CORRECT -> {
                ui.alwaysShow(event.getModel(), nickname);
                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    columnChosen = -1;
                    ui.askPickTiles(event.getModel(), this);
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
                    ui.inputParser.getDataToProcess().popData();
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


    public void youleft() {
        ended = true;
        ui.resetChat();
        ui.resetImportantEvents();
        events.add(null, APP_MENU);
        //inputReader.interrupt();//TODO NEED TO READ INPUT ALWAYS WITH THIS SO I DONT NEED TO STOP AND RESTART IT
        //inputParser.interrupt();

        ui.inputParser.setPlayer(null);
        ui.inputParser.setIdGame(null);
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public FileDisconnection getFileDisconnection() {
        return fileDisconnection;
    }
    //-----------------------------------------
    //METODI CHE IL CLIENT PUÓ RICHIEDERE VERSO IL SERVER

    @Override
    public void createGame(String nick) {
        ui.clearCMD();
        ui.show_titleMyShelfie();
        System.out.println("> Creating a new game...");

        try {
            server.createGame(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void joinFirstAvailable(String nick) {
        ui.clearCMD();
        ui.show_titleMyShelfie();
        System.out.println("> Connecting to the first available game...");
        try {
            server.joinFirstAvailable(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinGame(String nick, int idGame) {
        ui.clearCMD();
        ui.show_titleMyShelfie();
        System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        try {
            server.joinGame(nick, idGame);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reconnect(String nick, int idGame) {
        ui.clearCMD();
        ui.show_titleMyShelfie();
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
            ui.showPlayerJoined(gameModel, nickname);
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
                ui.showPlayerJoined(gamemodel, nickname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
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
        ui.addImportantEvent("[EVENT]: Player reconnected!");
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) {
        //Visualizzo il messaggio solo se e' per tutti o e' solo per me
        if (msg.whoIsReceiver().equals("*")) {
            ui.addMessage(msg);
            events.add(gameModel, SENT_MESSAGE);
        } else if (msg.whoIsReceiver().equals(nickname) || msg.getSender().getNickname().equals(nickname)) {
            msg.setText("[PRIVATE]: " + msg.getText());
            ui.addMessage(msg);
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
        ui.showNoAvailableGamesToJoin(why);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException {
        try {
            ui.showPlayerJoined(gameModel, nickname);
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
        ui.showGameEnded(gameModel);
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
        ui.addImportantEvent("[EVENT]: A set of not grabbable tiles has been requested by Player: " + gameModel.getNicknameCurrentPlaying());
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
        ui.addImportantEvent("[EVENT]:  Player " + p.getNickname() + " obtained " + point.getPoint() + " points by achieving " + point.getReferredTo());
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel, String nick) {
        ui.addImportantEvent("[EVENT]:  Player " + nick + " has just disconnected");

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        if (gameModel.getStatus().equals(GameStatus.WAIT)) {
            try {
                ui.showPlayerJoined(gameModel, nickname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }
    }


}
