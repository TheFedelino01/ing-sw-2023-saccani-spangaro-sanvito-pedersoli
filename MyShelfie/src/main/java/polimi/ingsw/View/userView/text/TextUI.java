package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
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
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class TextUI extends View implements CommonClientActions {
    private Scanner scanner = new Scanner(System.in);
    private String nickname;
    private Integer gameID;

    private boolean joined = false, started = false, askToBeReady=true;

    private GameModelImmutable lastModelReceived=null;

    private CommonClientActions server;


    public TextUI(ConnectionSelection selection) {
        nickname = "";
        gameID = 0;

        if(selection.equals(ConnectionSelection.SOCKET)) {
            server = new ClientSocket(this);
        }else if (selection.equals(ConnectionSelection.RMI)){
            server = new RMIClient(this);
        }
    }

    public void start() {

        System.out.println("> Insert any key to start! ('e' to exit the app):");
        String ris = scanner.nextLine();
        if (ris.equals("e")) {
            exit(0);
        }

        selectGame();

        if (joined) {
            askReadyToStart();
        }

    }

    public void setLastModalReceived(GameModelImmutable g){
        this.lastModelReceived=g;;
        lastModelReceived.notifyAll();
    }


    private void insertNickname() {
        clearConsole();
        System.out.println("> Insert your nickname: ");
        nickname = scanner.nextLine();
        System.out.println("> Your nickname is: " + nickname);
    }

    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }


    private void selectGame() {
        boolean reAsk = false;
        String optionChoose;

        do {
            clearConsole();
            reAsk = false;
            System.out.println("> Select one option:\n " +
                    "\t(c) Create a new Game\n " +
                    "\t(j) Join to a random Game\n" +
                    "\t(js) Join a specific Game by idGame\n" +
                    "\t(.) to leave");
            optionChoose = scanner.nextLine();
            if (optionChoose.equals("."))
                return;
            insertNickname();

            try {
                switch (optionChoose) {
                    case "c":
                        createGame(nickname);
                        break;
                    case "j":
                        joinFirstAvailable(nickname);
                        break;
                    case "js":
                        askGameId();
                        if (gameID != -1)
                            joinGame(nickname, gameID);
                        break;
                    default:
                        System.out.println("> Selection incorrect!");
                        reAsk = true;
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (reAsk == true);


    }

    private void statusJoined() {
        clearConsole();
        System.out.println("\n------------------\n" +
                "> Connected to Game: " + gameID + "\n" +
                "Nickname: " + nickname + "\n" +
                "------------------");
        askReadyToStart();

    }


    private void askGameId() {
        boolean reAsk = false;
        System.out.println("> Input the GameId ('.' to leave): ");

        do {
            reAsk = false;
            try {
                String numberOfPlayers = scanner.nextLine();
                if (numberOfPlayers.equals(".")) {
                    gameID = -1;
                    return;
                }

                int ris = Integer.parseInt(numberOfPlayers);
                gameID = ris;
                return;

            } catch (NumberFormatException e) {
                System.out.println("> NaN");
                reAsk = true;
            }
        } while (reAsk);
    }

    @Override
    public void createGame(String nick) throws IOException {
        clearConsole();
        System.out.println("> You have selected to create a new game");

        /*
        int numberOfPlayers = -1;

        boolean reAsk = false;
        do {

            reAsk = false;
            System.out.println("> Insert the number of players: ");

            try {
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (!(numberOfPlayers >= DefaultValue.minNumOfPlayer && numberOfPlayers <= DefaultValue.MaxNumOfPlayer)) {
                    System.out.println("> Range of player wrong (number of players must be between " + DefaultValue.minNumOfPlayer + " and " + DefaultValue.MaxNumOfPlayer + ")");
                    reAsk = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("> NaN");
                reAsk = true;
            }


        } while (reAsk);

        if (numberOfPlayers >= DefaultValue.minNumOfPlayer && numberOfPlayers <= DefaultValue.MaxNumOfPlayer) {
            System.out.println("> You have selected to create a new game with ID: " + gameID + " and " + numberOfPlayers + " players");
            server.createGame(nick);
            joined = true;
            return;
        }*/

        server.createGame(nick);
        joined = true;
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        clearConsole();
        System.out.println("> Connecting to the first available game...");
        server.joinFirstAvailable(nick);
        joined = true;
        return;
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        clearConsole();
        System.out.println("> You have selected to join to Game with id: \'" + idGame + "\', trying to connect");
        server.joinGame(nick,idGame);
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
        server.grabTileFromPlayground(x,y,direction,num);
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        server.positionTileOnShelf(column,type);
    }

    public void askReadyToStart() {
        String ris;

        try {
            do {
                System.out.println("> Are you ready to start? (y)");
                ris = scanner.nextLine();
                if (ris.equals("y")) {
                    setAsReady();
                }
            } while (askToBeReady);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void turn(boolean isYourTurn) {
        if (isYourTurn)
            System.out.println("> It's your turn!");
        else
            System.out.println("> It's not your turn!");
    }

    public void viewCommonCard(CardCommonType card1, CardCommonType card2) {
        System.out.println("> The two extracted common cards are:");
        viewSingleCommonCard(card1);
        viewSingleCommonCard(card2);
    }

    public void viewSingleCommonCard(CardCommonType card) {
        //TODO BISOGNA ADATTARLO CON ENUM !!!!!
        /*
        System.out.println("");
        switch (card) {
            case 0:
                System.out.println("> Six groups of 2 tiles of the same type (tile type can change between groups)");
                break;

            case 1:
                System.out.println("> Same tiles in the shelf vertexes");
                break;

            case 2:
                System.out.println("> Four groups of 4 tiles of the same type (tile type can change between groups)");
                break;

            case 3:
                System.out.println("> Two groups of 2x2 tiles of the same type (tile type CANNOT change between groups)");
                break;

            case 4:
                System.out.println("> Three columns made with max three different tile types (combinations may vary, but AT MAX three different types)");
                break;

            case 5:
                System.out.println("> Eight tiles of the same type");
                break;

            case 6:
                System.out.println("> Five tiles of the same type, creating a diagonal line across the shelf");
                break;

            case 7:
                System.out.println("> Four rows made with max three different tile types (combinations may vary, but AT MAX three different types)");
                break;

            case 8:
                System.out.println("> Two columns made with all different tile types");
                break;

            case 9:
                System.out.println("> Two rows made with all different tile types");
                break;

            case 10:
                System.out.println("> An X pattern is formed on the shelf, with tiles of the same type");
                break;

            case 11:
                System.out.println("> Five columns in ascending or descending order: from 5 to 1 tile or from 1 to 5 tiles, types may vary");
                break;

            default:
                System.out.println("> Error CommonCard");
                break;
        }
        ;*/
    }

    public void viewPlayGround() {
        System.out.println(lastModelReceived.getPg().toString());
    }

    public void viewGoalCard() {
    }

    private Integer askNum(String msg){
        System.out.print(msg);
        System.out.flush();

        Integer numT=null;

        try {
            numT = new Scanner(System.in).nextInt();
        }catch(InputMismatchException e){
            System.out.println("Nan");
        }
        return numT;
    }

    public void pickTiles() {
        /*try {
            grabTileFromPlayground(1, 3, Direction.UP, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        Integer numTiles;
        do {
            numTiles = askNum("> How many tiles do you want to get?");
        }while(numTiles==null);


        Integer row;
        do {
            row = askNum("> Which tiles do you want to get?\n> Choose row:");
        }while(row==null);


        Integer column;
        do {
            column = askNum("> Choose column:");
        }while(column==null);

        String direction;
        Direction d;
        do {
            System.out.println("> Choose direction (r=right,l=left,u=up,d=down): ");
            direction = new Scanner(System.in).nextLine();
            d = Direction.getDirection(direction);
        }while(d==null);

        //System.out.println("> You have selected: " + numTiles + " tiles from column " + column + " and row " + row + " in direction " + direction);

        try {
            grabTileFromPlayground(row, column, d, numTiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeTile() {
        viewMyShelf();
        System.out.println(">This is your hand:");
        int i=0;
        String ris="";
        for(Tile t: lastModelReceived.getPlayerEntity(nickname).getInHandTile()){
            ris+="["+i+"]: "+t.toString()+" | ";
            i++;
        }
        if(ris.equals(""))
            ris="empty";

        System.out.println(ris);

        System.out.println("> Which tiles do you want to place?");
        Integer indexHand;
        do {
            indexHand = askNum("> Choose Tile in hand (0,1,2):");
            if(indexHand<0 || indexHand>=lastModelReceived.getPlayerEntity(nickname).getInHandTile().size()){
                System.out.println("Wrong Tile selection offset");
                indexHand=null;
            }
        }while(indexHand==null);


        Integer column;
        do {
            column = askNum("> Choose column of your shelf:");
        }while(column==null);


        try {
            positionTileOnShelf(column, lastModelReceived.getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void viewMyShelf() {
        System.out.println(lastModelReceived.getPlayerEntity(nickname).getShelf().toString());
    }

    public void viewOtherShelfs() {
        for (Player p : lastModelReceived.getPlayers()) {
            System.out.println(p.getNickname() + ": \n" + p.getShelf().toString());
        }
    }


    //-----------------------------------------------------------------------
    @Override
    public void playerJoined(String nickNewPlayer) {
        System.out.println(nickname+" [VIEW]> "+nickNewPlayer+" has just joined!");
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        System.out.println(nickname+" [VIEW]> "+ wantedToJoin+" tried to entry but the game is full!");
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        System.out.println(nickname+" [VIEW]> "+ wantedToJoin.getNickname() + " has already in");
    }

    @Override
    public void playerIsReadyToStart(String nick) {
        System.out.println(nickname+" [VIEW]> "+ nick + " ready to start!");
        if(nick.equals(nickname)){
            askToBeReady=false;
        }
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        if(gamemodel.getCommonCards().size()==2) {
            for (CommonCard c : gamemodel.getCommonCards()) {
                System.out.println(nickname + " [VIEW]> " + c.getCommonType() + " card common extracted!");
            }
            viewCommonCard(gamemodel.getCommonCards().get(0).getCommonType(),gamemodel.getCommonCards().get(1).getCommonType());
        }
        setModel(gamemodel);


    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) {
        System.out.println(nickname+" [VIEW]> Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying());
        setModel(gamemodel);
        started=true;
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) {
        System.out.println(nickname+" [VIEW]> "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: todo");
        setModel(gamemodel);
    }

    @Override
    public void sentMessage(Message msg) {
        System.out.println(nickname+" [VIEW]> new Message: \""+msg.toString()+"\"");
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) {
        String ris = "| ";
        for(Tile t: gamemodel.getHandOfCurrentPlaying()){
            ris+=t.toString()+" | ";
        }
        System.out.println(nickname+" [VIEW]> Player: "+gamemodel.getNicknameCurrentPlaying()+" has grabbed some tiles: "+ris);
        setModel(gamemodel);

        if(lastModelReceived.getNicknameCurrentPlaying().equals(nickname)){
            placeTile();
        }else {
            viewPlayGround();
        }
    }

    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) {
        System.out.println(nickname+" [VIEW]> a set of non grabbable tiles have been required");
        setModel(gamemodel);
    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) {
        System.out.println(nickname+" [VIEW]> Player: "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
        setModel(gamemodel);

        if(!gamemodel.getNicknameCurrentPlaying().equals(nickname)){
            viewOtherShelfs();
        }else{
            if(gamemodel.getHandOfCurrentPlaying().size()>0)
                placeTile();
        }

    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) {
        System.out.println(nickname+" [VIEW]> Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying());
        setModel(gamemodel);

        if(gamemodel.getNicknameCurrentPlaying().equals(nickname)){
            viewPlayGround();

            pickTiles();
        }
    }

    @Override
    public void addedPoint(Player p, Point point) {
        System.out.println(nickname+" [VIEW]> Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo());
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        System.out.println(nickname+" [VIEW]>  Player "+nick +" just disconnected");
    }

    public GameModelImmutable getLastModelReceived(){
        return lastModelReceived;
    }

    public synchronized void setModel(GameModelImmutable m){
        lastModelReceived = m;
    }
}
