package polimi.ingsw.Model.Enumeration;

import polimi.ingsw.Model.DefaultValue;

import java.util.List;
import java.util.Random;

public enum TileType {
        CAT,
        BOOK,
        ACTIVITY,
        FRAME,
        TROPHY,
        PLANT,
        NOT_USED,
        FINISHED_USING,
        USED;
        //for testing purposes
        private static final List<TileType> values = List.of(values());
        private static final Random rand = new Random();

        public static TileType randomTile(){
                return values.get(rand.nextInt(DefaultValue.NumOfTileTypes));
        }
        //done like this so that the CAT tile is never chosen
        //also, so that the NOT_USED and FINISHED_USING are never chosen
        public static TileType randomTileCAT(){
                return values.get(rand.nextInt(DefaultValue.NumOfTileTypes-1)+1);
        }
        public static TileType randomTileCATeBOOK(){
                return values.get(rand.nextInt(DefaultValue.NumOfTileTypes-2)+2);
        }
        public static List<TileType> getValues(){return values;}

}

