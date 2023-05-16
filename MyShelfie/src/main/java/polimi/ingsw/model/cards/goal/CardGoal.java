package polimi.ingsw.model.cards.goal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardGoal extends Card {
    private Shelf layoutToMatch; //La tavola del giocatore deve matchare questo layout (per acquisire punti a seconda della Map legendPoint)
    private static Map<Integer, Point> legendPoint;
    private CardGoalType goalType;

    public CardGoal() {
        initialiseLayout(CardGoalType.NOT_SET);
        legendPoint = new HashMap<>();
        goalType = CardGoalType.NOT_SET;
    }

    public CardGoal(CardGoalType type) {
        initialiseLayout(type);
        legendPoint = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case (0) -> legendPoint.put(i, new Point(0, type));
                case (1) -> legendPoint.put(i, new Point(1, type));
                case (2) -> legendPoint.put(i, new Point(2, type));
                case (3) -> legendPoint.put(i, new Point(4, type));
                case (4) -> legendPoint.put(i, new Point(6, type));
                case (5) -> legendPoint.put(i, new Point(9, type));
                case (6) -> legendPoint.put(i, new Point(12, type));
            }
        }
        goalType = type;
    }

    public CardGoal(Shelf layoutToMatch, CardGoalType type) {
        legendPoint = new HashMap<>();
        this.layoutToMatch = layoutToMatch;
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case (0) -> legendPoint.put(i, new Point(0, type));
                case (1) -> legendPoint.put(i, new Point(1, type));
                case (2) -> legendPoint.put(i, new Point(2, type));
                case (3) -> legendPoint.put(i, new Point(4, type));
                case (4) -> legendPoint.put(i, new Point(6, type));
                case (5) -> legendPoint.put(i, new Point(9, type));
                case (6) -> legendPoint.put(i, new Point(12, type));
            }
        }
        this.goalType = type;
    }

    private void initialiseLayout(CardGoalType type) {
        String rowSplit = "-";
        String colSplit = ",";
        String s = null;
        JSONParser parser = new JSONParser();
        try (InputStream is = CardGoal.class.getClassLoader().getResourceAsStream("polimi/ingsw/Json/GoalCardsLayout.json");
             Reader reader = new InputStreamReader(Objects.requireNonNull(is, "Couldn't find json file"), StandardCharsets.UTF_8)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            s = (String) obj.get(CardGoalType.toString(type));
        } catch (ParseException | FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert s != null;

        //get the matrix's dimensions
        int sizeRow = Arrays.asList(s.split(rowSplit)).size();
        int sizeCol = Arrays.asList(s.split(rowSplit)).get(0).split(colSplit).length;
        layoutToMatch = new Shelf();

        //this method is for splitting the string returned from the json file in a matrix
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeCol; j++) {
                layoutToMatch.setSingleTile(new Tile(TileType.getValues().get(
                        Integer.parseInt(s.split(rowSplit)[i]
                                .split(colSplit)[j]))), i, j);
            }
        }
    }

    public Point verify(Shelf toCheck) {
        int check = 0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if ((!(layoutToMatch.get(i, j).isSameType(TileType.NOT_USED))) && (layoutToMatch.get(i, j).isSameType(toCheck.get(i, j).getType()))) {
                    check++;
                }
            }
        }
        if (check > 6) {
            System.out.println("Check error in goalCheck!"); //error
            return legendPoint.get(0);
        }
        return legendPoint.get(check);
    }

    public Shelf getLayoutToMatch() {
        return layoutToMatch;
    }

    public void setLayoutToMatch(Shelf layoutToMatch) {
        this.layoutToMatch = layoutToMatch;
    }

    public Map<Integer, Point> getLegendPoint() {
        return legendPoint;
    }

    public CardGoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(CardGoalType goalType) {
        this.goalType = goalType;
    }


    @Override
    public boolean isSameType(Card c) {
        if (c instanceof CardGoal) {
            return this.goalType.equals(((CardGoal) c).goalType);
        }
        return false;
    }

    public String toString(int col) {
        return layoutToMatch.toString(col);
    }

}