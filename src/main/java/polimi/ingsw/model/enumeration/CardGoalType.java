package polimi.ingsw.model.enumeration;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Enumeration for the card goal type
 */
public enum CardGoalType implements CardType {
    GOAL0("pc1"),
    GOAL1("pc2"),
    GOAL2("pc3"),
    GOAL3("pc4"),
    GOAL4("pc5"),
    GOAL5("pc6"),
    GOAL6("pc7"),
    GOAL7("pc8"),
    GOAL8("pc9"),
    GOAL9("pc10"),
    GOAL10("pc11"),
    GOAL11("pc12"),

    NOT_SET("pc0");

    private String backgroundClass;

    CardGoalType(String backgroundClass){
        this.backgroundClass=backgroundClass;
    }

    public String getBackgroundClass() {
        return backgroundClass;
    }


    /**
     * This method returns the CardGoalType corresponding to the string passed as parameter
     * @param type the string to convert
     * @return the CardGoalType corresponding to the string passed as parameter
     */
    public static String toString(CardGoalType type) {
        switch (type) {
            case GOAL0 -> {
                return "GOAL0";
            }
            case GOAL1 -> {
                return "GOAL1";
            }
            case GOAL2 -> {
                return "GOAL2";
            }
            case GOAL3 -> {
                return "GOAL3";
            }
            case GOAL4 -> {
                return "GOAL4";
            }
            case GOAL5 -> {
                return "GOAL5";
            }
            case GOAL6 -> {
                return "GOAL6";
            }
            case GOAL7 -> {
                return "GOAL7";
            }
            case GOAL8 -> {
                return "GOAL8";
            }
            case GOAL9 -> {
                return "GOAL9";
            }
            case GOAL10 -> {
                return "GOAL10";
            }
            case GOAL11 -> {
                return "GOAL11";
            }
            case NOT_SET -> {
                return "NOT_SET";
            }
        }
        return "NOT_SET";
    }

    public static List<CardGoalType> getValues() {
        return Arrays.stream(values()).filter(x -> !x.equals(CardGoalType.NOT_SET)).collect(Collectors.toList());
    }

    //for testing purposes ONLY
    @Deprecated
    public static List<CardGoalType> getValuesBUT() {
        return Arrays.stream(values()).filter(x -> !x.equals(CardGoalType.NOT_SET))
                .filter(x -> !x.equals(CardGoalType.GOAL0)).collect(Collectors.toList());
    }


}
