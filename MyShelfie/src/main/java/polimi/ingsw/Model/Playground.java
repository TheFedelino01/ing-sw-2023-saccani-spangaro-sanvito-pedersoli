package polimi.ingsw.Model;

import com.google.gson.Gson;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Playground {
    private final Tile[][] playground; //playground formed by tiles
    private final List<Tile> bag; //All tiles are contained in this array

    private int[][] data;
    //if we want to not use a json file we could just uncomment the
    //below declaration, and delete all the GSON stuff from the file
    /*
    private final static int[][] data = {
                            {0,0,0,3,4,0,0,0,0},
                            {0,0,0,1,1,4,0,0,0},
                            {0,0,3,1,1,1,3,0,0},
                            {0,4,1,1,1,1,1,1,3},
                            {4,1,1,1,1,1,1,1,4}
                            {3,1,1,1,1,1,1,4,0},
                            {0,0,3,1,1,1,3,0,0},
                            {0,0,0,4,1,1,0,0,0},
                            {0,0,0,0,4,3,0,0,0}
                            };
                                 */

    public Playground(int numberOfPlayers) {
        bag = new ArrayList<>();
        data = new int[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];

        playground = new Tile[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
        try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            String jsonUrl = "./src/main/java/polimi/ingsw/JSON/PlaygroundFourPlayer.json";
            FileReader reader = new FileReader(jsonUrl);

            // read JSON data as 2D array of integers
            data = gson.fromJson(reader, int[][].class);

            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initialisePlayground(numberOfPlayers);
    }


    public void initialisePlayground(int num){
        switch (num) {
            case 2 -> {
                for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        if (data[i][j] == 1) {
                            playground[i][j] = new Tile(TileType.USED);
                        } else {
                            playground[i][j] = new Tile(TileType.NOT_USED);
                        }
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        if (data[i][j] == 1 || data[i][j] == 3) {
                            playground[i][j] = new Tile(TileType.USED);
                        } else {
                            playground[i][j] = new Tile(TileType.NOT_USED);
                        }
                    }
                }
            }
            case 4 -> {
                for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        if (data[i][j] == 1 || data[i][j] == 3 || data[i][j] == 4) {
                            playground[i][j] = new Tile(TileType.USED);
                        } else {
                            playground[i][j] = new Tile(TileType.NOT_USED);
                        }
                    }
                }
            }
        }
    }

    public void setFreeSide() {
        //set free side to true where the tiles are near a not used tile or a finished using tile
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                if (!playground[i][j].isSameType(TileType.NOT_USED) &&
                        !(playground[i][j].isSameType(TileType.FINISHED_USING))) {
                    if (i > 0) {
                        if (playground[i - 1][j].isSameType(TileType.NOT_USED) ||
                                playground[i - 1][j].isSameType(TileType.FINISHED_USING)) {
                            playground[i][j].setFreeSide(true);
                        }
                    }
                    if (i < DefaultValue.PlaygroundSize - 1) {
                        if (playground[i + 1][j].isSameType(TileType.NOT_USED) ||
                                playground[i + 1][j].isSameType(TileType.FINISHED_USING)) {
                            playground[i][j].setFreeSide(true);
                        }
                    }
                    if (j >= 1) {
                        if (playground[i][j - 1].isSameType(TileType.NOT_USED) ||
                                playground[i][j - 1].isSameType(TileType.FINISHED_USING)) {
                            playground[i][j].setFreeSide(true);
                        }
                    }
                    if (j < DefaultValue.PlaygroundSize - 1) {
                        if (playground[i][j + 1].isSameType(TileType.NOT_USED) ||
                                playground[i][j + 1].isSameType(TileType.FINISHED_USING)) {
                            playground[i][j].setFreeSide(true);
                        }
                    }
                }
            }
        }
    }


    public Tile[][] getPlayground() {
        return playground;
    }

    public void setPlayground() {
        int random;
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                if (playground[i][j].isSameType(TileType.USED)) {
                    random = (int) (Math.random() * bag.size());
                    playground[i][j] = bag.get(random);
                    bag.remove(random);
                }
            }
        }
    }

    public List<Tile> getBag() {
        return bag;
    }

    public void setBag() {
        //after having done a playground setup
        //fill the bag with tiles in a random order
        int c0 = 0, c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, check;
        Random rand = new Random();
        while ((c0 < DefaultValue.NumOfTilesPerType - 1) ||
                (c1 < DefaultValue.NumOfTilesPerType - 1) ||
                (c2 < DefaultValue.NumOfTilesPerType - 1) ||
                (c3 < DefaultValue.NumOfTilesPerType - 1) ||
                (c4 < DefaultValue.NumOfTilesPerType - 1) ||
                (c5 < DefaultValue.NumOfTilesPerType - 1)) {
            check = rand.nextInt(DefaultValue.NumOfTileTypes);
            switch (check) {
                case (0) -> {
                    bag.add(new Tile(TileType.CAT));
                    c0++;
                }
                case (1) -> {
                    bag.add(new Tile(TileType.BOOK));
                    c1++;
                }
                case (2) -> {
                    bag.add(new Tile(TileType.ACTIVITY));
                    c2++;
                }
                case (3) -> {
                    bag.add(new Tile(TileType.FRAME));
                    c3++;
                }
                case (4) -> {
                    bag.add(new Tile(TileType.TROPHY));
                    c4++;
                }
                case (5) -> {
                    bag.add(new Tile(TileType.PLANT));
                    c5++;
                }
            }
        }
    }


    public List<Tile> grabTile(int x, int y, Direction direction, int num) {
        List<Tile> ris = new ArrayList<>();
        int i = 0;
        while (i < num) {
            if (((y == DefaultValue.PlaygroundSize - 1) && (direction.equals(Direction.DOWN))) ||
                    ((y == 0) && (direction.equals(Direction.UP))) ||
                    ((x == DefaultValue.PlaygroundSize - 1) && (direction.equals(Direction.RIGHT))) ||
                    ((x == 0) && (direction.equals(Direction.LEFT))))
                return ris;
            if ((playground[x][y] != null) ||
                    !(Objects.requireNonNull(playground[x][y]).isSameType(TileType.NOT_USED)) ||
                    !(playground[x][y].isSameType(TileType.NOT_USED))) {
                if (playground[x][y].isFreeSide()) {
                    ris.add(playground[x][y]);
                    playground[x][y].setType(TileType.FINISHED_USING);
                }
            }
            i++;
            switch (direction) {
                case UP -> y--;
                case DOWN -> y++;
                case LEFT -> x--;
                case RIGHT -> x++;
            }
        }
        return ris;
    }

}
