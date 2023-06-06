package polimi.ingsw.model.cards.common;

import org.fusesource.jansi.Ansi;
import polimi.ingsw.model.cards.Card;
import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.Point;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.interfaces.CommonCardIC;
import polimi.ingsw.model.interfaces.PointIC;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class CommonCard extends Card implements CommonCardIC {
    private Queue<Point> points;
    private final CardCommonType commonType;

    /**
     * Constructor
     *
     * @param type
     */
    public CommonCard(CardCommonType type) {
        points = new ArrayDeque<>();
        for (int i = 4; i > 0; i--) {
            points.add(new Point(4 * 2, type));
        }
        commonType = type;
    }

    /**
     * Constructor
     *
     * @param points
     * @param commonType
     */
    public CommonCard(Queue<Point> points, CardCommonType commonType) {
        this.points = points;
        this.commonType = commonType;
    }

    /**
     * @param toCheck the player's shelf that needs checking
     * @return true if the player's shelf verifies the common card requirements, false if it doesn't
     */
    public abstract boolean verify(Shelf toCheck);

    public String toString(int i, boolean lastCycle) {
        int spacer = i;
        if (i > 1)
            spacer = i + 1;
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_commonCards - 1 + spacer, DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage)
                .fg(Ansi.Color.WHITE).bg(Ansi.Color.YELLOW)
                .a("|       |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        ris.append(ansi().cursor(DefaultValue.row_commonCards + spacer, DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage)
                .fg(Ansi.Color.WHITE).bg(Ansi.Color.YELLOW)
                .a("|   " + Objects.requireNonNullElse(this.getPoints().peek(), new Point(0)).getPoint() + "   |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        ris.append(ansi().cursor(DefaultValue.row_commonCards + 1 + spacer, DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage)
                .fg(Ansi.Color.WHITE).bg(Ansi.Color.YELLOW)
                .a("|       |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));

        if (!lastCycle){
            ris.append(ansi().cursor(DefaultValue.row_commonCards,
                            DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage + DefaultValue.display_finalPoint)
                    .fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLUE)
                    .a("|       |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
            ris.append(ansi().cursor(DefaultValue.row_commonCards + 1,
                            DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage + DefaultValue.display_finalPoint)
                    .fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLUE)
                    .a("|   1   |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
            ris.append(ansi().cursor(DefaultValue.row_commonCards + 2,
                            DefaultValue.col_commonCards + DefaultValue.longest_commonCardMessage + DefaultValue.display_finalPoint)
                    .fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLUE)
                    .a("|       |").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        }

        switch (this.commonType) {
            case CommonHorizontal0 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First horizontal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Four rows made by at most three different tile types (per row)"));

                return ris.toString();
            }
            case CommonHorizontal1 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second horizontal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Two rows made all by different tile types"));
                return ris.toString();
            }
            case CommonVertical0 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First vertical card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Three cols made by at most three different tile types (per col)"));
                return ris.toString();
            }
            case CommonVertical1 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second vertical card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Two columns made all by different tile types"));
                return ris.toString();
            }
            case CommonSameDiagonal -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First diagonal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> 5 tiles of the same type in a diagonal pattern"));
                return ris.toString();
            }
            case CommonStair -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second diagonal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Tiles on the shelf must form a staircase"));
                return ris.toString();
            }
            case CommonVertex -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Vertexes card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> All vertexes must be of the same type"));
                return ris.toString();
            }
            case CommonX -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("X pattern card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> 5 tiles of the same type, in an X pattern"));
                return ris.toString();
            }
            case CommonSixGroups -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Six separated groups of two made by all of the same types (per single group)"));
                return ris.toString();
            }
            case CommonFourGroups -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Four separated groups of two made by all of the same types (per single group)"));
                return ris.toString();
            }
            case CommonSquares -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Third group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> Two separated 2x2 groups made all by the same type"));
                return ris.toString();
            }
            case CommonEight -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Fourth group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a(" -> 8 of the same type tiles, any pattern"));
                return ris.toString();
            }
            default -> {
                return "Nan";
            }
        }
    }

    /**
     * @return the points assigned to a card
     */
    public Queue<Point> getPoints() {
        return points;
    }

    /**
     * Sets the point in a card object
     *
     * @param points
     */
    public void setPoints(Queue<Point> points) {
        this.points = points;
    }

    /**
     * @return the common type of card
     */
    public CardCommonType getCommonType() {
        return commonType;
    }

    /**
     * @param c to check
     * @return true if the card is of the same type of c
     */
    @Override
    public boolean isSameType(Card c) {
        if (c instanceof CommonCard) {
            return this.commonType.equals(((CommonCard) c).commonType);
        }
        return false;
    }

    /**
     * @param c card to check
     * @return true if the card is equal to c
     */
    public boolean equals(CommonCard c) {
        return this.points.containsAll(c.getPoints()) && this.commonType == c.commonType;
    }

    /**
     * @return the list of points referred to a card (immutable)
     */
    public Queue<PointIC> getPoints_IC() {
        return new ArrayDeque<>(points);
    }

}
