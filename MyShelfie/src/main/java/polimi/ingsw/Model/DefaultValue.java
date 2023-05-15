package polimi.ingsw.Model;

import java.io.Serializable;

public class DefaultValue implements Serializable {
    public final static boolean DEBUG=false;

    public final static int time_publisher_showing_seconds = 1;

    public final static int MaxNumOfPlayer = 4;
    public final static int maxTilesInHand = 3;
    public final static int minNumOfPlayer = 2;

    public final static int defButtonXSize = 200;
    public final static int defButtonYSize = 50;
    public final static int NumOfCommonCards = 2;

    public final static int startingButtons = 4;
    public final static int displayTitleRow = 0;
    public final static int displayTitleCol = 10;
    public final static String gameIdData = "GameId";
    public final static String gameIdTime = "Created";
    public final static int twelveHS = 43200;
    public final static int displayShelfNextCol = 30;

    public final static int NumOfColumnsShelf = 5;
    public final static int NumOfRowsShelf = 6;

    public final static int minNumOfGrabbableTiles = 1;
    public final static int maxNumOfGrabbableTiles = 3;
    public final static int NumOfTileTypes = 6;
    public final static int NumOfTilesPerType = 22;
    public final static int PlaygroundSize = 9;
    public final static int[] pointsValue = {8, 6, 4, 2};

    public final static int Default_port_RMI = 4321;
    public final static int Default_port_Socket = 4320;
    public final static String Default_servername_RMI = "MyShelfie";

    public final static String Remote_ip = "127.0.0.1";

    public final static Long timeout_for_detecting_disconnection = 4000L;

    public final static int maxnum_of_last_event_tobe_showed = 6;

    //CONSOLE OUTPUT INDEXES

    //INPUT
    public final static int row_input = 34;

    //IMPORTANT EVENTS
    public final static int row_important_events = 2;
    public final static int col_important_events = 86;

    //PLAYGROUND
    public final static int row_playground = 13;
    public final static int col_playground = 0;

    //SHELVES
    public final static int row_playerName = 24;
    public final static int row_shelves = 25;
    public final static int col_shelves = 3;

    //COMMON CARDS
    public final static int row_commonCards = 9;
    public final static int col_commonCards = 50;


    //POINTS
    public final static int row_points = 16;
    public final static int col_points = 78;

    //GOAL CARDS
    public final static int row_goalCards = 16;
    public final static int col_goalCards = 50;

    //CHAT
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    //LEADERBOARD
    public final static int row_leaderboard = 25;
    public final static int col_leaderboard = 15;

    //GAME INFO
    public final static int row_gameID = 9;
    public final static int row_nextTurn = 10;



    public final static int num_of_attempt_to_connect_toServer_before_giveup = 5;
    public final static int seconds_between_reconnection = 5;

    public final static int max_messagesShown = 5;


}
