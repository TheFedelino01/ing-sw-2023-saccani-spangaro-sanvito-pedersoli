package polimi.ingsw.Model.Enumeration;

import polimi.ingsw.Model.Cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public enum CardGoalType implements CardType {
    GOAL0, GOAL1, GOAL2, GOAL3, GOAL4, GOAL5, GOAL6, GOAL7, GOAL8, GOAL9, GOAL10, GOAL11, NOT_SET;

    public static String toString(CardGoalType type){
        switch(type){
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

    public static List<CardGoalType> getValues(){return Arrays.stream(values()).filter(x -> !x.equals(CardGoalType.NOT_SET)).collect(Collectors.toList());}

    //for testing purposes ONLY
    public static List<CardGoalType> getValuesBUT(){return Arrays.stream(values()).filter(x -> !x.equals(CardGoalType.NOT_SET))
            .filter(x -> !x.equals(CardGoalType.GOAL0)).collect(Collectors.toList());}

}
