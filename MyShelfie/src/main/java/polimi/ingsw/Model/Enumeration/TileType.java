package polimi.ingsw.Model.Enumeration;

import java.util.Arrays;
import java.util.Collections;
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
        FINISHED_USING;



        //for testing purposes
        private static final List<TileType> values = List.of(values());
        private static final int size = 6;
        private static final Random rand = new Random();
        public static TileType randomTile(){
                return values.get(rand.nextInt(size));
        }
}

