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
        int[][] data = new int[DefaultValue.PlaygroundSize][DefaultValue.PlaygroundSize];
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
        setUsedNotUsed(numberOfPlayers, data);
        fillBag();
        setPlayground();
        calculateFreeSide();
    }
    private void setUsedNotUsed(int numPlayers, int data[][]){

        for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j]!=0 && ((data[i][j]) <= numPlayers)) {
                    playground[i][j] = new Tile(TileType.USED);
                } else {
                    playground[i][j] = new Tile(TileType.NOT_USED);
                }
            }
        }

    }


    public void calculateFreeSide() {
        //set free side to true where the tiles are near a not used tile or a finished using tile
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {
                if (!playground[i][j].isSameType(TileType.NOT_USED) &&
                        !(playground[i][j].isSameType(TileType.FINISHED_USING))) {

                    if(haveAtLeastOneSideFree(i,j)){
                        playground[i][j].setFreeSide(true);
                    }else{
                        playground[i][j].setFreeSide(false);
                    }

                }
            }
        }
    }



    public void setPlayground() {
        int random;
        Tile extractedTile;


        //Cycle all the matrix and substitute the tile type USED with a random Tile
        for (int i = 0; i < DefaultValue.PlaygroundSize; i++) {
            for (int j = 0; j < DefaultValue.PlaygroundSize; j++) {

                //If I need to replace the tile (because is type used)
                if (playground[i][j].isSameType(TileType.USED)) {

                    do {
                        random = (int) (Math.random() * DefaultValue.NumOfTileTypes);

                        //Get one tile
                        extractedTile = bag.get(random);

                        //If there is at least one tile available of that type (not needed to re-randomize)
                    }while(extractedTile.getNumOfAvailable()>=1);

                    playground[i][j] = new Tile(extractedTile.getType(),1);

                    extractedTile.decrementAvailableBy1();

                }

            }
        }
    }
    private boolean haveAtLeastOneSideFree(int r, int c){
        if (r >= 1) {
            if (playground[r - 1][c].isSameType(TileType.NOT_USED) ||
                    playground[r - 1][c].isSameType(TileType.FINISHED_USING)) {
                return true;
            }
        }
        if (r < DefaultValue.PlaygroundSize - 1) {
            if (playground[r + 1][c].isSameType(TileType.NOT_USED) ||
                    playground[r + 1][c].isSameType(TileType.FINISHED_USING)) {
                return true;
            }
        }
        if (c >= 1) {
            if (playground[r][c - 1].isSameType(TileType.NOT_USED) ||
                    playground[r][c - 1].isSameType(TileType.FINISHED_USING)) {
                return true;
            }
        }
        if (c < DefaultValue.PlaygroundSize - 1) {
            if (playground[r][c + 1].isSameType(TileType.NOT_USED) ||
                    playground[r][c + 1].isSameType(TileType.FINISHED_USING)) {
                return true;
            }
        }
        return false;
    }

    public void fillBag() {
        bag.add(new Tile(TileType.CAT,DefaultValue.NumOfTilesPerType));
        bag.add(new Tile(TileType.BOOK,DefaultValue.NumOfTilesPerType));
        bag.add(new Tile(TileType.ACTIVITY,DefaultValue.NumOfTilesPerType));
        bag.add(new Tile(TileType.FRAME,DefaultValue.NumOfTilesPerType));
        bag.add(new Tile(TileType.TROPHY,DefaultValue.NumOfTilesPerType));
        bag.add(new Tile(TileType.PLANT,DefaultValue.NumOfTilesPerType));
    }


    public List<Tile> grabTile(int x, int y, Direction direction, int num) {
        List<Tile> ris = new ArrayList<>();
        int i = 0;
        //Only if num is plausible
        if(num>=1 && num<=3) {
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
        }
        return ris;
    }

}
