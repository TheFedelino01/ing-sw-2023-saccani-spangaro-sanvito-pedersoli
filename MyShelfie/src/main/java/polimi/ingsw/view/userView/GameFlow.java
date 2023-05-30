package polimi.ingsw.view.userView;

import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.model.Point;
import polimi.ingsw.view.networking.RMI.RMIClient;
import polimi.ingsw.view.networking.socket.client.ClientSocket;
import polimi.ingsw.view.userView.gui.GUI;
import polimi.ingsw.view.userView.gui.GUIApplication;
import polimi.ingsw.view.userView.utilities.events.EventElement;
import polimi.ingsw.view.userView.utilities.events.EventList;
import polimi.ingsw.view.userView.utilities.events.EventType;
import polimi.ingsw.view.userView.utilities.FileDisconnection;
import polimi.ingsw.view.userView.text.TUI;
import polimi.ingsw.view.userView.utilities.InputParser;
import polimi.ingsw.view.userView.utilities.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

import static polimi.ingsw.view.userView.utilities.events.EventType.*;

public class GameFlow extends Flow implements Runnable, CommonClientActions {

    private String nickname;

    private final EventList events = new EventList();

    private CommonClientActions clientActions;
    private final FileDisconnection fileDisconnection;

    private String lastPlayerReconnected;
    private int columnChosen = -1;
    private UI ui;
    protected InputParser inputParser;
    protected InputReader inputReader;
    protected List<String> importantEvents; //events that needs to be showed always in screen
    private boolean ended = false;


    public GameFlow(ConnectionSelection connectionSelection){
        //Invoked for starting with TUI
        switch (connectionSelection) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new RMIClient(this);
        }
        ui = new TUI();

        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();
        this.inputReader = new inputReaderTUI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);

        new Thread(this).start();
    }


    public GameFlow(GUIApplication guiApplication,ConnectionSelection connectionSelection){
        //Invoked for starting with GUI
        switch (connectionSelection) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new RMIClient(this);
        }
        this.inputReader = new inputReaderGUI();

        ui = new GUI(guiApplication,(inputReaderGUI) inputReader);
        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();

        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    public BufferData getBuffer_ForTesting(){
        return this.inputReader.getBuffer();
    }
    public boolean isEnded_ForTesting(){
        return this.ended;
    }


    @Override
    public void run() {
        EventElement event;



        try {
            ui.show_publisher();
            events.add(null, APP_MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
                        case RUNNING,LAST_CIRCLE -> {
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
                nickname=null;
                ui.show_returnToMenuMsg();
                try {
                    this.inputParser.getDataToProcess().popData();
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
                    ui.show_playerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, event.getModel());
                    askReadyToStart();
                }
            }
        }

    }

    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case GAMESTARTED -> {
                ui.show_gameStarted(event.getModel());
                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

                this.inputParser.setPlayer(event.getModel().getPlayerEntity(nickname));
                this.inputParser.setIdGame(event.getModel().getGameId());

            }
            case COMMON_CARD_EXTRACTED -> {
                ui.show_commonCards(event.getModel());
                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case SENT_MESSAGE ->{
                ui.show_sentMessage(event.getModel(),nickname);
            }

            case NEXT_TURN, PLAYER_RECONNECTED -> {
                ui.show_nextTurnOrPlayerReconnected(event.getModel(),nickname);

                columnChosen = -1;

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {

                    if (event.getType().equals(PLAYER_RECONNECTED)) {

                        if (nickname.equals(lastPlayerReconnected)) {
                            askPickTiles(event.getModel());
                            if (ended) return;
                        }
                        //else the player who has just reconnected is not me, and so I do nothing
                    } else {
                        askPickTiles(event.getModel());
                        if (ended) return;
                    }
                }
                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

            case GRABBED_TILE,ASK_TO_SELECT_TILE_TO_PLACE -> {
                if(!event.getType().equals(ASK_TO_SELECT_TILE_TO_PLACE))
                    ui.show_grabbedTileMainMsg(event.getModel(), nickname);

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //It's my turn, so I'm the current playing

                    if (columnChosen == -1) {
                        //If I haven't selected the column than I select the column in which I want to place all the tiles that I have grabbed (now in Hand)
                        askColumn(event.getModel());
                        if (ended) return;
                    }
                    askWhichTileToPlace(event.getModel());
                } else {
                    ui.show_grabbedTile(nickname, event.getModel());
                }
                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case POSITIONED_TILE -> {
                ui.show_positionedTile(event.getModel(), nickname);

                ui.addImportantEvent("Player " + event.getModel().getNicknameCurrentPlaying() + " has positioned a Tile on his shelf!");
                if (event.getModel().getHandOfCurrentPlaying().size() > 0 && event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    //Ask to place other tiles
                    events.add(event.getModel(), EventType.ASK_TO_SELECT_TILE_TO_PLACE);
                }
                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());

            }
            case GRABBED_TILE_NOT_CORRECT -> {
                ui.show_grabbedTileNotCorrect(event.getModel(), nickname);

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)) {
                    columnChosen = -1;
                    askPickTiles(event.getModel());
                }

                //System.out.println(ansi().cursor(DefaultValue.row_input, 0).toString());
            }

        }

    }

    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAMEENDED -> {
                ui.show_returnToMenuMsg();
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


    public void youleft() {
        ended = true;
        ui.resetImportantEvents();
        events.add(null, APP_MENU);
        //inputReader.interrupt();//TODO NEED TO READ INPUT ALWAYS WITH THIS SO I DONT NEED TO STOP AND RESTART IT
        //inputParser.interrupt();

        this.inputParser.setPlayer(null);
        this.inputParser.setIdGame(null);
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



    ///////////////////////////////
    //ASK

    private void askNickname() {
        ui.show_insertNicknameMsg();
        //nickname = scanner.nextLine();
        try {
            nickname=this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ui.show_choosenNickname(nickname);
    }


    //return: false need to recall askSelectGame
    private boolean askSelectGame() {
        String optionChoose;
        ended = false;
        ui.show_menuOptions();

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
                //System.out.println("> Selection incorrect!");
                return false;
            }
        }

        return true;
    }

    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        do {
            ui.show_inputGameIdMsg();
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
                ui.show_NaNMsg();
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
                //System.out.println(ansi().cursor(18, 0).fg(DEFAULT)); todo ?
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
                ui.show_askNum(msg,gameModel,nickname);
                //System.out.flush();

                try {
                    temp = this.inputParser.getDataToProcess().popData();
                    if (ended) return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                numT = Integer.parseInt(temp);
            } catch (InputMismatchException | NumberFormatException e) {
                ui.show_NaNMsg();
            }
        } while (numT < 0);
        return numT;
    }

    public void askPickTiles(GameModelImmutable gameModel) {
        ui.show_askPickTilesMainMsg();
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
                ui.show_direction();

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
        Integer column;
        ui.show_askColumnMainMsg();
        boolean isColumnBigEnough;
        do {
            isColumnBigEnough=true;
            column = askNum("> Choose column to place all the tiles:", model);
            ui.show_playerHand(model);
            if (ended) return;

            //Check by client side (// to server)
            if(!(model.getPlayerEntity(this.nickname).getNumofFreeSpacesInCol(column)>=model.getPlayerEntity(this.nickname).getInHandTile_IC().size())){
                ui.columnShelfTooSmall(model);
                isColumnBigEnough=false;
            }
        } while (column == null || column >= DefaultValue.NumOfColumnsShelf || column < 0 || !isColumnBigEnough);
        columnChosen = column;
    }

    public void askWhichTileToPlace(GameModelImmutable model) {

        ui.show_whichTileToPlaceMsg();
        Integer indexHand;
        do {
            indexHand = Objects.requireNonNullElse(askNum("\t> Choose Tile in hand (0,1,2):", model), -1);
            ui.show_playerHand(model);
            if (ended) return;
            if (indexHand < 0 || indexHand >= model.getPlayerEntity(nickname).getInHandTile_IC().size()) {
                ui.show_wrongSelectionHandMsg();
                indexHand = null;
            }
        } while (indexHand == null);

        try {
            positionTileOnShelf(columnChosen, model.getPlayerEntity(nickname).getInHandTile_IC().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





    //-----------------------------------------
    //METODI CHE IL CLIENT PUÓ RICHIEDERE VERSO IL SERVER

    @Override
    public void createGame(String nick) {
        ui.show_creatingNewGameMsg(nick);

        try {
            clientActions.createGame(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void joinFirstAvailable(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        try {
            clientActions.joinFirstAvailable(nick);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinGame(String nick, int idGame) {
        ui.show_joiningToGameIdMsg(idGame,nick);
        try {
            clientActions.joinGame(nick, idGame);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reconnect(String nick, int idGame) {
        //System.out.println("> You have selected to join to Game with id: '" + idGame + "', trying to reconnect");
        if(idGame!=-1) {
            ui.show_joiningToGameIdMsg(idGame, nick);
            try {
                clientActions.reconnect(nickname, fileDisconnection.getLastGameId(nickname));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            ui.show_noAvailableGamesToJoin("No disconnection previously detected");
        }
    }

    @Override
    public void leave(String nick, int idGame) throws IOException {
        clientActions.leave(nick, idGame);
    }


    @Override
    public void setAsReady() throws IOException {
        clientActions.setAsReady();
    }

    @Override
    public boolean isMyTurn() {
        //todo invoke is my turn
        return false;
    }

    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
        clientActions.grabTileFromPlayground(x, y, direction, num);
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        clientActions.positionTileOnShelf(column, type);
    }

    @Override
    public void heartbeat() {
        clientActions.heartbeat();
    }

    @Override
    public void sendMessage(Message msg) {
        clientActions.sendMessage(msg);
    }


    //-----------------------------------------------------------------------
    //RICEZIONE DEGLI EVENTI DAL SERVER

    @Override
    public void playerJoined(GameModelImmutable gameModel) {
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, EventType.PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        ui.show_playerJoined(gameModel, nickname);

    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {
        if (gamemodel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gamemodel, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }

    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gameModel) throws RemoteException {
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
        //Show the message only if is for everyone or is for me (or I sent it)
        if (msg.whoIsReceiver().equals("*") || msg.whoIsReceiver().toUpperCase().equals(nickname.toUpperCase()) || msg.getSender().getNickname().toUpperCase().equals(nickname.toUpperCase())) {
            ui.addMessage(msg,gameModel);
            events.add(gameModel, SENT_MESSAGE);
            //msg.setText("[PRIVATE]: " + msg.getText());
        }
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("No currently game available with the following GameID: "+gameid);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        events.add(null, GENERIC_ERROR_WHEN_ENTRYING_GAME);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);

        if(nick.equals(nickname)){
            ui.show_youReadyToStart(gameModel, nickname);
        }
        // if(nick.equals(nickname))
        //    toldIAmReady=true;
        events.add(gameModel, PLAYER_IS_READY_TO_START);
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gameModel) throws RemoteException {
        events.add(gameModel, EventType.COMMON_CARD_EXTRACTED);
    }

    @Override
    public void gameStarted(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.GAMESTARTED);
    }

    @Override
    public void gameEnded(GameModelImmutable gameModel) {
        ended = true;
        events.add(gameModel, EventType.GAMEENDED);
        ui.show_gameEnded(gameModel);
        resetGameId(fileDisconnection, gameModel);

    }

    @Override
    public void grabbedTile(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.GRABBED_TILE);
    }


    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.GRABBED_TILE_NOT_CORRECT);
        ui.addImportantEvent("[EVENT]: A set of not grabbable tiles has been requested by Player: " + gameModel.getNicknameCurrentPlaying());
    }

    @Override
    public void positionedTile(GameModelImmutable gameModel, TileType type, int column) {
        events.add(gameModel, EventType.POSITIONED_TILE);
    }

    @Override
    public void nextTurn(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);

        //I remove all the input that the user sends when It is not his turn
        this.inputParser.getDataToProcess().popAllData();
    }

    @Override
    public void addedPoint(Player p, Point point, GameModelImmutable gamemodel) {
        ui.addImportantEvent("Player " + p.getNickname() + " obtained " + point.getPoint() + " points by achieving " + point.getReferredTo());
        ui.show_addedPoint(p,point,gamemodel);
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        if (gameModel.getStatus().equals(GameStatus.WAIT)) {
                ui.show_playerJoined(gameModel, nickname);
        }
    }

    @Override
    public void columnShelfTooSmall(GameModelImmutable gameModel, int column) throws RemoteException {
        ui.addImportantEvent("Cannot place Tiles in "+column+" column because there are no spaces available to place all");
    }

    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        ui.addImportantEvent("Only one player is connected, waiting "+secondsToWaitUntilGameEnded+" seconds before calling Game Ended!");
    }

    @Override
    public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {
        ui.addImportantEvent("Last circle begin!");
    }


}
