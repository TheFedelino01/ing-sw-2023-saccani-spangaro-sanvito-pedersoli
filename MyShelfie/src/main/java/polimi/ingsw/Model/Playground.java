package polimi.ingsw.Model;

import com.google.gson.Gson;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Playground {
    private Tile[][] board; //Tile sul piano di gioco che i player prendono
    private String jsonUrl = "./src/main/java/polimi/ingsw/JSON/PlaygroundFourPlayer.json";
    private List<Tile> bag; //Array contenente tutte le 22*6 Tile
    public Playground(){}
    public Playground(int NumberOfPlayers) {
        int[][] data = null;
        board = new Tile[9][9];
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            FileReader reader = new FileReader(jsonUrl);

            // read JSON data as 2D array of integers
            data = gson.fromJson(reader, int[][].class);

            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (NumberOfPlayers == 2) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] == 1) {
                        board[i][j] = new Tile(TileType.USED);
                    } else {
                        board[i][j] = new Tile(TileType.NOT_USED);
                    }
                }
            }
        }
        if (NumberOfPlayers == 3) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] == 1 || data[i][j] == 3) {
                        board[i][j] = new Tile(TileType.USED);
                    } else {
                        board[i][j] = new Tile(TileType.NOT_USED);
                    }
                }
            }
        }
        if (NumberOfPlayers== 4) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] == 1 || data[i][j] == 3 || data[i][j] == 4) {
                        board[i][j] = new Tile(TileType.USED);
                    } else {
                        board[i][j] = new Tile(TileType.NOT_USED);
                    }
                }
            }
        }
    }


    public void setFreeSide() {
        //set freeside to true where the tiles is near a not used tile or a finished using tile
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getType() != TileType.NOT_USED && board[i][j].getType() != TileType.FINISHED_USING) {
                    if (i - 1 >= 0) {
                        if (board[i - 1][j].getType() == TileType.NOT_USED || board[i - 1][j].getType() == TileType.FINISHED_USING) {
                            board[i][j].setFreeSide(true);
                        }
                    }
                    if (i + 1 < board.length) {
                        if (board[i + 1][j].getType() == TileType.NOT_USED || board[i + 1][j].getType() == TileType.FINISHED_USING) {
                            board[i][j].setFreeSide(true);
                        }
                    }
                    if (j - 1 >= 0) {
                        if (board[i][j - 1].getType() == TileType.NOT_USED || board[i][j - 1].getType() == TileType.FINISHED_USING) {
                            board[i][j].setFreeSide(true);
                        }
                    }
                    if (j + 1 < board[i].length) {
                        if (board[i][j + 1].getType() == TileType.NOT_USED || board[i][j + 1].getType() == TileType.FINISHED_USING) {
                            board[i][j].setFreeSide(true);
                        }
                    }
                }
            }
        }
    }


    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard() {
        this.board = board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getType() == TileType.USED) {
                    int random = (int) (Math.random() * bag.size());
                    board[i][j] = bag.get(random);
                    bag.remove(random);
                }
            }
        }
    }

    public List<Tile> getBag() {
        return bag;
    }

    public void setBag() {
        //A questo punto ho settato il playground
        //Riempio la bag
        bag = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            bag.add(new Tile(TileType.BOOK));
            bag.add(new Tile(TileType.TROPHY));
            bag.add(new Tile(TileType.ACTIVITY));
            bag.add(new Tile(TileType.FRAME));
            bag.add(new Tile(TileType.PLANT));
            bag.add(new Tile(TileType.CAT));
        }

        this.bag = bag;
    }


    public List<Tile> grabTailFromPlayground(int x, int y, Direction direction, int num) {
        List<Tile> ris = new ArrayList<>();
        int i = 0;
        while (i < num) {
            if (board[x][y] != null) {
                if (board[x][y].isFreeSide()) {
                    ris.add(board[x][y]);
                    board[x][y].setType(TileType.FINISHED_USING);
                }
            }
            i++;
            if (direction == Direction.UP)
                y++;
            if (direction == Direction.DOWN)
                y--;
            if (direction == Direction.LEFT)
                x--;
            if (direction == Direction.RIGHT)
                x++;

        }
        return ris;
    }

}
