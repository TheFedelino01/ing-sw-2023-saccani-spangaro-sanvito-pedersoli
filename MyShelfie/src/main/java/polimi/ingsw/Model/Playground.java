package polimi.ingsw.Model;

import org.json.simple.JSONObject;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Playground {
    private final Tile[][] playground; //playground formed by tiles
    private final List<Tile> bag; //All tiles are contained in this array
    private List<List<Integer>> data;

    public Playground() {
        bag = new ArrayList<>();
        playground = new Tile[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
    }

    //to write in the json file
    //the "-" is the separator between rows
    //the "," is the separator between columns, so
    //0,0,0,0-1,1,1,1 equals the matrix
    // 0 0 0 0
    // 1 1 1 1
    public Playground(int numberOfPlayers) {
        bag = new ArrayList<>();
        playground = new Tile[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
        String rowSplit = "-";
        String colSplit = ",";
        JSONParser parser = new JSONParser();
        String jsonUrl = "./src/main/java/polimi/ingsw/JSON/PlaygroundFourPlayer.json";
        try (Reader reader = new FileReader(jsonUrl)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            String s = (String) obj.get(Integer.toString(numberOfPlayers));
            int size = Arrays.asList(s.split(rowSplit)).size();
            data = new ArrayList<>();

            //this method is for splitting the string returned from the json file in a matrix
            for(int i = 0; i<size; i++){
                data.add(new ArrayList<>(size));
                for(int j = 0; j<size; j++){
                    data.get(i).add(j, Integer
                            .parseInt(s.split(rowSplit)[i]
                                    .split(colSplit)[j]));
                }
            }
        } catch (ParseException | FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialisePlayground();

    }

    public void initialisePlayground() {
        for (int i = 0; i < Objects.requireNonNull(data).size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                if (data.get(i).get(j) == 1) {
                    playground[i][j] = new Tile(TileType.USED);
                } else {
                    playground[i][j] = new Tile(TileType.NOT_USED);
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
