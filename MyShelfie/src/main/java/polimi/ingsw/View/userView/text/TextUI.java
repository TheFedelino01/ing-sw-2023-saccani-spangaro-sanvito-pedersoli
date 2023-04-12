package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Message;
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
import polimi.ingsw.View.userView.SharedData;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI extends View implements Runnable,CommonClientActions {
    private Scanner scanner = new Scanner(System.in);
    private String nickname;

    private boolean joined = false, toldIAmReady=false;
    SharedData shared = new SharedData();

    private CommonClientActions server;


    public TextUI(ConnectionSelection selection) {
        nickname = "";
        if(selection.equals(ConnectionSelection.SOCKET)) {
            server = new ClientSocket(this);
        }else if (selection.equals(ConnectionSelection.RMI)){
            server = new RMIClient(this);
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        askSelectGame();
        while (true) {
            if(joined) {
                switch (shared.getLastModelReceived().getStatus()) {
                    case WAIT:
                        statusWait();
                        break;
                    case RUNNING:
                        statusRunning();
                        break;
                    case ENDED:
                        statusEnded();
                        break;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
    private void statusWait(){
        if(!toldIAmReady){
            askReadyToStart();
        }
    }
    private void statusRunning(){
        if(shared.isNeedto_showCommonCards()){
            show_allCommonCards();
        }
        if(shared.isNeedto_showGrabbedTile()){
            show_grabbedTile();
        }
        if(shared.isNeedto_showPositionedTile()){
            show_positionedTile();
        }

        if(shared.getLastModelReceived().getNicknameCurrentPlaying().equals(nickname)){
            if(!shared.isGrabbed()) {
                askPickTiles();
                shared.setGrabbed(true);
            }
            if(!shared.isPlaced()){
                askPlaceTile();
                shared.setPlaced(true);
            }
        }
    }
    private void statusEnded(){

    }

    //-----------------------------------------
    //METODI SHOW DA CONSOLE

    private void show_allPlayers(){
        System.out.println("Current Players: \n"+shared.getLastModelReceived().toStringListPlayers());
    }

    private void show_allCommonCards(){
        if(shared.getLastModelReceived().getCommonCards().size()==2) {
            for (CommonCard c : shared.getLastModelReceived().getCommonCards()) {
                System.out.println(nickname + ": " + c.getCommonType() + " card common extracted!");
            }
            //viewCommonCard();
            shared.setNeedto_showCommonCards(false);
        }
    }
    private void show_grabbedTile(){
        String ris = "| ";
        for(Tile t: shared.getLastModelReceived().getHandOfCurrentPlaying()){
            ris+=t.toString()+" | ";
        }
        System.out.println(nickname+": Player: "+shared.getLastModelReceived().getNicknameCurrentPlaying()+" has grabbed some tiles: "+ris);
        viewPlayGround();
        shared.setNeedto_showGrabbedTile(false);
    }
    private void show_positionedTile(){
        viewOtherShelfs();
        shared.setNeedto_showPositionedTile(false);
    }





    //-----------------------------------------
    //METODI DI RICHIESTA INPUT DA TASTIERA

    private void askNickname() {
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

    private void askSelectGame() {
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
            askNickname();

            try {
                switch (optionChoose) {
                    case "c":
                        createGame(nickname);
                        break;
                    case "j":
                        joinFirstAvailable(nickname);
                        break;
                    case "js":
                        Integer gameId = askGameId();
                        if (gameId != -1)
                            joinGame(nickname, gameId);
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

    private Integer askGameId() {
        boolean reAsk = false;
        System.out.println("> Input the GameId ('.' to leave): ");

        do {
            reAsk = false;
            try {
                String numberOfPlayers = scanner.nextLine();
                if (numberOfPlayers.equals(".")) {
                    return -1;
                }

                int ris = Integer.parseInt(numberOfPlayers);
                return ris;

            } catch (NumberFormatException e) {
                System.out.println("> NaN");
                reAsk = true;
            }
        } while (reAsk);
        return -1;
    }
    public void askReadyToStart() {
        String ris;

        try {
            System.out.println("> When you are ready to start, enter (y):");
            ris = scanner.nextLine();
            if (ris.equals("y")) {
                setAsReady();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void viewCommonCard() {
        System.out.println("> The two extracted common cards are:");
        System.out.println(shared.getLastModelReceived().getCommonCards().get(0).getCommonType());
        System.out.println(shared.getLastModelReceived().getCommonCards().get(1).getCommonType());
    }


    public void viewPlayGround() {
        System.out.println(shared.getLastModelReceived().getPg().toString());
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

    public void askPickTiles() {
        /*try {
            grabTileFromPlayground(1, 3, Direction.UP, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        viewPlayGround();
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

    public void askPlaceTile() {
        viewMyShelf();
        System.out.println(">This is your hand:");
        int i=0;
        String ris="";
        for(Tile t: shared.getLastModelReceived().getPlayerEntity(nickname).getInHandTile()){
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
            if(indexHand<0 || indexHand>=shared.getLastModelReceived().getPlayerEntity(nickname).getInHandTile().size()){
                System.out.println("Wrong Tile selection offset");
                indexHand=null;
            }
        }while(indexHand==null);


        Integer column;
        do {
            column = askNum("> Choose column of your shelf:");
        }while(column==null);


        try {
            positionTileOnShelf(column, shared.getLastModelReceived().getPlayerEntity(nickname).getInHandTile().get(indexHand).getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void viewMyShelf() {
        System.out.println(shared.getLastModelReceived().getPlayerEntity(nickname).getShelf().toString());
    }

    public void viewOtherShelfs() {
        for (Player p : shared.getLastModelReceived().getPlayers()) {
            System.out.println(p.getNickname() + ": \n" + p.getShelf().toString());
        }
    }





    //-----------------------------------------
    //METODI CHE IL CLIENT PUÓ RICHIEDERE VERSO IL SERVER

    @Override
    public void createGame(String nick) throws IOException {
        clearConsole();
        System.out.println("> You have selected to create a new game");
        server.createGame(nick);
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        clearConsole();
        System.out.println("> Connecting to the first available game...");
        server.joinFirstAvailable(nick);
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



    //-----------------------------------------------------------------------
    //RICEZIONE DEGLI EVENTI DAL SERVER

    @Override
    public void playerJoined(GameModelImmutable gamemodel) {
        String p = gamemodel.getPlayers().get(gamemodel.getPlayers().size()-1).getNickname();
        System.out.println("[EVENT]: "+p+" has just joined!");

        shared.setLastModelReceived(gamemodel);
        show_allPlayers();

        if(p.equals(nickname))
            joined=true;
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        System.out.println("[EVENT]: "+ wantedToJoin+" tried to entry but the game is full!");
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
    }

    @Override
    public void playerIsReadyToStart(String nick) {
        System.out.println("[EVENT]: "+ nick + " ready to start!");

        if(nick.equals(nickname))
            toldIAmReady=true;
    }

    @Override
    public void commonCardsExtracted(GameModelImmutable gamemodel) throws RemoteException {
        System.out.println("[EVENT]: common card extracted");

        shared.set(gamemodel, true, shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());

    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]: Game Started with id: "+gamemodel.getGameId()+ ", First turn is played by: "+gamemodel.getNicknameCurrentPlaying());
        shared.setLastModelReceived(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]: "+gamemodel.getGameId()+" ended! \n" +
                "The winner is: "+gamemodel.getWinner().getNickname()+"\n" +
                "Score board: todo");
        shared.setLastModelReceived(gamemodel);
    }

    @Override
    public void sentMessage(Message msg) {
        System.out.println("[EVENT]: new Message: \""+msg.toString()+"\"");
    }

    @Override
    public void grabbedTile(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]: a tile has been grabbed");
        shared.set(gamemodel, shared.isNeedto_showCommonCards(), true,shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());

    }
//shared.set(gamemodel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),shared.isNeedto_showPositionedTile());
    @Override
    public void grabbedTileNotCorrect(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]: a tile has not been grabbed correctly");
        shared.set(gamemodel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),true);

    }

    @Override
    public void positionedTile(GameModelImmutable gamemodel, TileType type, int column) {
        System.out.println("[EVENT]: Player "+gamemodel.getNicknameCurrentPlaying()+" has positioned ["+type+"] Tile in column "+column+" on his shelf!");
        shared.set(gamemodel, shared.isNeedto_showCommonCards(), shared.isNeedto_showGrabbedTile(),shared.isGrabbed(),shared.isPlaced(),true);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) {
        System.out.println("[EVENT]:  Next turn! It's up to: "+gamemodel.getNicknameCurrentPlaying());
        shared.setLastModelReceived(gamemodel);

        if(!gamemodel.getNicknameCurrentPlaying().equals(nickname)){
            shared.reinit(gamemodel);
        }
    }

    @Override
    public void addedPoint(Player p, Point point) {
        System.out.println("[EVENT]:  Player "+p.getNickname()+" obtained "+point.getPoint()+" points by achieving "+point.getReferredTo());
    }

    @Override
    public void playerDisconnected(String nick) throws RemoteException {
        System.out.println("[EVENT]:  Player "+nick +" has just disconnected");
    }





}
