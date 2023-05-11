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

    /**
     * Watch out:  to write in the json file:
     * the "-" is the row separator
     * the "," is the column separator, so
     * 0,0,0,0-1,1,1,1 is equal to the matrix
     * 0 0 0 0
     * 1 1 1 1
     * Also, the number that identifies the long strings in the json
     * is the player number that the matrix/string implements
     * E.G.: "2":"0,0,0,0-1,1,1,1" is the json transcription
     * of the playground default for 2 player based games, and so on
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
        for (int r = 0; r < Objects.requireNonNull(data).size(); r++) {
            for (int c = 0; c < data.get(r).size(); c++) {
                if (data.get(r).get(c) == 1) {
                    playground[r][c] = new Tile(TileType.USED);
                } else {
                    playground[r][c] = new Tile(TileType.NOT_USED);
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
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                if (playground[r][c].isSameType(TileType.NOT_USED) || playground[r][c].isSameType(TileType.FINISHED_USING))
                    switch (r) {
                        case (0) -> {
                            switch (c) {
                                case (0) -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                }
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                                default -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                            }
                        }
                        case (DefaultValue.PlaygroundSize - 1) -> {
                            switch (c) {
                                case (0) -> {
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                }
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                                default -> {
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                            }
                        }
                        default -> {
                            switch (c) {
                                case (0) -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                }
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                                default -> {
                                    playground[r + 1][c].setFreeSide(true);
                                    playground[r - 1][c].setFreeSide(true);
                                    playground[r][c + 1].setFreeSide(true);
                                    playground[r][c - 1].setFreeSide(true);
                                }
                            }
                        }
                    }
            }
        }
    }


    public void setPlayground() {
        int random;
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                if (playground[r][c].isSameType(TileType.USED) || playground[r][c].isSameType(TileType.FINISHED_USING)) {
                    random = (int) (Math.random() * bag.size());
                    playground[r][c] = new Tile(bag.get(random).getType());
                    //If the tile is a border-tile then set free side to true (sure at least 1 side free)
                    if (isABorderTile(r, c)) {
                        playground[r][c].setFreeSide(true);
                    }
                    bag.remove(random);
                }
            }
        }
    }

    public void setBag() {
        for (int r = 0; r < DefaultValue.NumOfTilesPerType; r++) {
            for (int c = 0; c < DefaultValue.NumOfTileTypes; c++) {
                bag.add(new Tile(TileType.values()[c], false));
            }
        }

        Collections.shuffle(bag);
    }

    public boolean checkBeforeGrab(int r, int c, Direction direction, int num) {
        int i = 0;
        while (i < num) {
            if (r >= DefaultValue.PlaygroundSize || c >= DefaultValue.PlaygroundSize) {
                return false;
            }
            if (playground[r][c] == null) {
                return false;
            }
            if (!playground[r][c].isFreeSide()) {
                return false;
            }

            if (playground[r][c].isSameType(TileType.NOT_USED)) {
                return false;
            }
            if (playground[r][c].isSameType(TileType.FINISHED_USING)) {
                return false;
            }
            i++;
            switch (direction) {
                case UP -> r--;
                case DOWN -> r++;
                case LEFT -> c--;
                case RIGHT -> c++;
            }
        }
        return true;
    }

    boolean allTileHaveAllFreeSide() {
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                if (!(playground[r][c].isSameType(TileType.NOT_USED) ||
                        playground[r][c].isSameType(TileType.FINISHED_USING)))
                    switch (r) {
                        case (0) -> {
                            switch (c) {
                                //checks [0][0] top row, top col, a vertex
                                case (0) -> {
                                    if (!((playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [0][last] top row, bottom col, a vertex
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    if (!((playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c - 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [0][rand] top row (no vertexes)
                                default -> {
                                    if (!((playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                                    playground[r][c - 1].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        case (DefaultValue.PlaygroundSize - 1) -> {
                            switch (c) {
                                //checks [last][0] bottom row, first col, a vertex
                                case (0) -> {
                                    if (!((playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                            playground[c][r + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [last][last] last row, last col, a vertex
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    if (!((playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c - 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [last][rand] last row (no vertexes)
                                default -> {
                                    if (!((playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c - 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                                    playground[r][c + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        default -> {
                            switch (c) {
                                //checks [rand][0] first column (no vertexes)
                                case (0) -> {
                                    if (!((playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [rand][last] last column (no vertexes)
                                case (DefaultValue.PlaygroundSize - 1) -> {
                                    if (!((playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c - 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                                //checks [rand][rand] middle of playground
                                default -> {
                                    if (!((playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                                            playground[r][c + 1].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                                                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)) &&
                                            (playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                                                    playground[r][c - 1].isSameType(TileType.FINISHED_USING)))) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
            }
        }
        return true;
    }

    public List<Tile> grabTile(int r, int c, Direction direction, int num) throws TileGrabbedNotCorrectException {
        List<Tile> ris = new ArrayList<>();
        //check if all the tile are not used or finished using

        if (!checkBeforeGrab(r, c, direction, num)) {
            throw new TileGrabbedNotCorrectException();
        }

        int i = 0;
        while (i < num) {
            ris.add(new Tile(playground[r][c].getType()));
            System.out.println(ris);
            playground[r][c].setType(TileType.FINISHED_USING);
            updateFreeSide();
            i++;
            switch (direction) {
                case UP -> r--;
                case DOWN -> r++;
                case LEFT -> c--;
                case RIGHT -> c++;
            }
        }
        if (allTileHaveAllFreeSide()) {
            setPlayground();
        }
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


    //for testing ONLY (Deprecated so that no one uses them)
    @Deprecated
    public void setEmptyPlayground() {
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                playground[r][c] = new Tile(TileType.FINISHED_USING);
            }
        }
    }

    @Deprecated
    public void setSingleTile(TileType type, int r, int c) {
        playground[r][c] = new Tile(type);
        playground[r][c].setFreeSide(true);
    }

    @Deprecated
    public List<List<Tile>> getPlayground() {
        return Arrays.stream(this.playground).toList()
                .stream()
                .map(x -> Arrays.stream(x).toList())
                .toList();
    }

}