package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.userView.CommonClientActions;
import polimi.ingsw.View.userView.View;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class TextUI extends Thread implements CommonClientActions, View {
    private Scanner scanner = new Scanner(System.in);
    private String nickname;
    private Integer gameID;

    private boolean joined = false, started = false, updateCame;

    private CommonCard card1, card2;

    private GameModelImmutable lastModalReceived;

    private CommonClientActions outsideWorld = new ClientSocket();


    public TextUI() {
        nickname = "";
        gameID = 0;
    }

    @Override
    public void run() {

        System.out.println("> Insert any key to start! ('e' to exit the app):");
        String ris = scanner.nextLine();
        if (ris.equals("e")) {
            exit(0);
        }

        selectGame();

        synchronized (this) {
            if (joined) {
                while (started == false) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                //Game started
                viewCommonCard(card1.getCommonType(), card2.getCommonType());

                while (true) {
                    updateCame=false;

                    if (lastModalReceived.isMyTurn(nickname)) {
                        pickTiles();
                        placeTiles();

                        viewMyShelf();
                    }
                    viewOtherShelfs();

                    synchronized (lastModalReceived){
                        //Wait until another event happens
                        while(updateCame==false) {
                            try {
                                lastModalReceived.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    //Another event happened, it's time to show

                }
            }
        }


    }

    public void setLastModalReceived(GameModelImmutable g){
        this.lastModalReceived=g;
        updateCame=true;
        lastModalReceived.notifyAll();
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
            //todo invoke creation
            outsideWorld.createGame(nick);
            joined = true;
            return;
        }
    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException {
        clearConsole();
        System.out.println("> Connecting to the first available game...");
        //todo invoke join first available
        joined = true;
        return;
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        clearConsole();
        System.out.println("> You have selected to join to Game with id: \'" + idGame + "\', trying to connect");
        //todo invoke join first available
    }

    @Override
    public void setAsReady() throws IOException {
        //todo invoke set ready
    }

    @Override
    public boolean isMyTurn() {
        //todo invoke is my turn
        return false;
    }

    @Override
    public void grabTileFromPlayground(int x, int y, Direction direction, int num) throws IOException {
        //todo invoke
    }

    @Override
    public void positionTileOnShelf(int column, TileType type) throws IOException {
        //todo invoke
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
            } while (!ris.equals("y"));

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
    }

    public void viewGoalCard() {
    }

    public void pickTiles() {
        System.out.println("> How many tiles do you want to get?");
        int numTiles = Integer.parseInt(scanner.nextLine());
        System.out.println("> Which tiles do you want to get?");
        System.out.println("> Choose column: ");
        int column = Integer.parseInt(scanner.nextLine());
        System.out.println("> Choose row: ");
        int row = Integer.parseInt(scanner.nextLine());

        System.out.println("> Choose direction (r=right,l=left,u=up,d=down): ");
        String direction = scanner.nextLine();
        Direction d = Direction.getDirection(direction);

        System.out.println("> You have selected: " + numTiles + " tiles from column " + column + " and row " + row + " in direction " + direction);


        try {
            grabTileFromPlayground(column, row, d, numTiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeTiles() {
        System.out.println("> Which tiles do you want to place?");
        System.out.println("> Choose column: ");
        int column = Integer.parseInt(scanner.nextLine());
        //TODO CONTROLLI E PARSING DA STRING A TILE TYPE !!!!!
        //TODO E QUESTO METODO DEVE CONTINUARE FINO A QUANDO TUTTE LE TILES NON SONO STATE PIAZZATE
        try {
            positionTileOnShelf(column, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO choose tile order in column
    }

    public void viewMyShelf() {
        System.out.println(lastModalReceived.getPlayerEntity(nickname).getShelf().toString());
    }

    public void viewOtherShelfs() {
        for (Player p : lastModalReceived.getPlayers()) {
            System.out.println(p.getNickname() + ": \n" + p.getShelf().toString());
        }
    }


}
