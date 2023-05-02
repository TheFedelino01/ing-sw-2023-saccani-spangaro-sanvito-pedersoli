package polimi.ingsw.Model;

import org.fusesource.jansi.Ansi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.TileGrabbedNotCorrectException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class Playground implements Serializable {
    private final Tile[][] playground; //playground formed by tiles
    private final List<Tile> bag; //All tiles are contained in this array
    private List<List<Integer>> data;

    public Playground() {
        bag = new ArrayList<>();
        playground = new Tile[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
    }

    /*
    Watch out:  to write in the json file:
                the "-" is the row separator
                the "," is the column separator, so
                0,0,0,0-1,1,1,1 is equal to the matrix
                0 0 0 0
                1 1 1 1
                Also, the number that identifies the long strings in the json
                is the player number that the matrix/string implements
                    E.G.: "2":"0,0,0,0-1,1,1,1" is the json transcription
                    of the playground default for 2 player based games, and so on
     */
    public Playground(int numberOfPlayers) {
        bag = new ArrayList<>();
        playground = new Tile[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
        String rowSplit = "-";
        String colSplit = ",";
        String s = null;
        JSONParser parser = new JSONParser();
        try (InputStream is = Playground.class.getClassLoader().getResourceAsStream("polimi/ingsw/Json/PlaygroundFourPlayer.json");
             Reader reader = new InputStreamReader(Objects.requireNonNull(is, "Couldn't find json file"), StandardCharsets.UTF_8)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            s = (String) obj.get(Integer.toString(numberOfPlayers));
        } catch (ParseException | FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert s != null;
        int size = Arrays.asList(s.split(rowSplit)).size();
        data = new ArrayList<>();

        //this method is for splitting the string returned from the json file in a matrix
        for (int i = 0; i < size; i++) {
            data.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                data.get(i).add(j, Integer
                        .parseInt(s.split(rowSplit)[i]
                                .split(colSplit)[j]));
            }
        }

        initialisePlayground();
        setBag();
        setPlayground();
    }

    public Tile getTile(int r, int c) {
        return playground[r][c];
    }

    public int getNumOfTileinTheBag() {
        return bag.size();
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

    private boolean isABorderTile(int r, int c) {
        if (r > 0) {
            if (playground[r - 1][c].isSameType(TileType.NOT_USED)) {
                return true;
            }
        }
        if (r < DefaultValue.PlaygroundSize - 1) {
            if (playground[r + 1][c].isSameType(TileType.NOT_USED)) {
                return true;
            }
        }
        if (c > 0) {
            if (playground[r][c - 1].isSameType(TileType.NOT_USED)) {
                return true;
            }
        }
        if (c < DefaultValue.PlaygroundSize - 1) {
            return playground[r][c + 1].isSameType(TileType.NOT_USED);
        }
        return false;
    }

    public void updateFreeSide() {
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


    public void setPlayground() {
        int random;
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                if (playground[i][j].isSameType(TileType.USED) || playground[i][j].isSameType(TileType.FINISHED_USING)) {
                    random = (int) (Math.random() * bag.size());
                    playground[i][j] = bag.get(random);
                    //If the tile is a border-tile then set free side to true (sure at least 1 side free)
                    if (isABorderTile(i, j)) {
                        playground[i][j].setFreeSide(true);
                    }
                    bag.remove(random);
                }
            }
        }
    }

    public void setBag() {
        for (int i = 0; i < DefaultValue.NumOfTilesPerType; i++) {
            for (int j = 0; j < DefaultValue.NumOfTileTypes; j++) {
                bag.add(new Tile(TileType.values()[j], false));
            }
        }

        Collections.shuffle(bag);
    }

    public void checkBeforeGrab(int x, int y, Direction direction, int num) throws TileGrabbedNotCorrectException {
        int i = 0;
        while (i < num) {
            if (!playground[x][y].isFreeSide()) {
                throw new TileGrabbedNotCorrectException();
            }
            if (playground[x][y].isSameType(TileType.NOT_USED)) {
                throw new TileGrabbedNotCorrectException();
            }
            i++;
            switch (direction) {
                case UP -> x--;
                case DOWN -> x++;
                case LEFT -> y--;
                case RIGHT -> y++;
            }
        }
    }

    public List<Tile> grabTile(int x, int y, Direction direction, int num) throws TileGrabbedNotCorrectException {
        List<Tile> ris = new ArrayList<>();
        //check if all the tile are not used or finished using
        int nums = 0;
        for (int k = 0; k < DefaultValue.PlaygroundSize; k++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                if ((playground[k][j].isSameType(TileType.NOT_USED)) || (playground[k][j].isSameType(TileType.FINISHED_USING))) {
                    nums++;
                    if (nums == DefaultValue.PlaygroundSize * DefaultValue.PlaygroundSize) {
                        setPlayground();
                    }
                }
            }
        }
        checkBeforeGrab(x, y, direction, num);
        int i = 0;
        while (i < num) {
            if (playground[x][y].isSameType(TileType.NOT_USED)) {
                throw new TileGrabbedNotCorrectException();

            }
            if (((y == DefaultValue.PlaygroundSize - 1) && (direction.equals(Direction.DOWN))) ||
                    ((y == 0) && (direction.equals(Direction.UP))) ||
                    ((x == DefaultValue.PlaygroundSize - 1) && (direction.equals(Direction.RIGHT))) ||
                    ((x == 0) && (direction.equals(Direction.LEFT)))) {
                updateFreeSide();
                return ris;
            }
            if ((playground[x][y] != null) ||
                    !(Objects.requireNonNull(playground[x][y]).isSameType(TileType.NOT_USED)) ||
                    !(playground[x][y].isSameType(TileType.NOT_USED))) {
                if (playground[x][y].isFreeSide()) {
                    ris.add(playground[x][y]);
                    playground[x][y] = new Tile(TileType.FINISHED_USING);
                } else {
                    throw new TileGrabbedNotCorrectException();
                }
            }
            i++;
            switch (direction) {
                case UP -> x--;
                case DOWN -> x++;
                case LEFT -> y--;
                case RIGHT -> y++;
            }
        }
        updateFreeSide();

        return ris;
    }

    public String toString() {
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_playground, DefaultValue.col_playground).a("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |").toString());
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            ris.append(ansi().cursor(DefaultValue.row_playground + i + 1, DefaultValue.col_playground).a(i)).append(":");
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                switch (playground[i][j].getType()) {
                    case CAT ->
                            ris.append(ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.WHITE).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    case BOOK ->
                            ris.append(ansi().bg(Ansi.Color.WHITE).fg(Ansi.Color.BLACK).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    case TROPHY ->
                            ris.append(ansi().bg(Ansi.Color.CYAN).fg(Ansi.Color.WHITE).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    case FRAME ->
                            ris.append(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.WHITE).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    case ACTIVITY ->
                            ris.append(ansi().bg(Ansi.Color.YELLOW).fg(Ansi.Color.WHITE).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    case PLANT ->
                            ris.append(ansi().bg(Ansi.Color.MAGENTA).fg(Ansi.Color.WHITE).a(" ").a(playground[i][j].toString().substring(0, 1)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                    default ->
                            ris.append(ansi().bg(Ansi.Color.BLACK).fg(Ansi.Color.BLACK).a(" ").a("N").a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()).append("|");
                }
            }
        }
        return ris.toString();
    }

}