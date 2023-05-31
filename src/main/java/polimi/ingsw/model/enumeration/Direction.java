package polimi.ingsw.model.enumeration;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getDirection(String txt) {
        String ris = txt.toLowerCase();

        return switch (ris) {
            case "up", "u" -> Direction.UP;
            case "down", "d" -> Direction.DOWN;
            case "left", "l" -> Direction.LEFT;
            case "right", "r" -> Direction.RIGHT;
            default -> null;
        };

    }
}
