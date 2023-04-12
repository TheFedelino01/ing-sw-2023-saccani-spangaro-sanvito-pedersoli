package polimi.ingsw.Model.Enumeration;

public enum Direction {
    UP,DOWN,LEFT,RIGHT;

    public static Direction getDirection(String txt){
        String ris =txt.toLowerCase();

        switch(ris){
            case "up":
            case "u":
                return Direction.UP;

            case "down":
            case "d":
                return Direction.DOWN;

            case "left":
            case "l":
                return Direction.LEFT;

            case "right":
            case "r":
                return Direction.RIGHT;

            default:
                return null;
        }

    }
}
