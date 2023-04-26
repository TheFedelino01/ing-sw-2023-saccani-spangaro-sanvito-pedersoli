package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class CommonCard extends Card {
    private Queue<Point> points;
    private CardCommonType commonType;

    public CommonCard(CardCommonType type) {
        points = new ArrayDeque<>();
        commonType = type;
    }

    public CommonCard(Queue<Point> points, CardCommonType commonType) {
        this.points = points;
        this.commonType = commonType;
    }


    public abstract boolean verify(Shelf toCheck);

    public String toString(CardCommonType type, int i) {
        StringBuilder ris = new StringBuilder();
        switch (type) {
            case CommonHorizontal0 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First horizontal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Four rows made by at most three different tile types (per row)"));
                return ris.toString();
            }
            case CommonHorizontal1 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second horizontal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Two rows made all by different tile types"));
                return ris.toString();
            }
            case CommonVertical0 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First vertical card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Three cols made by at most three different tile types (per col)"));
                return ris.toString();
            }
            case CommonVertical1 -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second vertical card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Two columns made all by different tile types"));
                return ris.toString();
            }
            case CommonSameDiagonal -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First diagonal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> 5 tiles of the same type in a diagonal pattern"));
                return ris.toString();
            }
            case CommonStair -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second diagonal card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Tiles on the shelf must form a staircase"));
                return ris.toString();
            }
            case CommonVertix -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Vertexes card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> All vertexes must be of the same type"));
                return ris.toString();
            }
            case CommonX -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("X pattern card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> 5 tiles of the same type, in an X pattern"));
                return ris.toString();
            }
            case CommonSixGroups -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("First group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Six separated groups of two made by all of the same types (per single group)"));
                return ris.toString();
            }
            case CommonFourGroups -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Second group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Four separated groups of two made by all of the same types (per single group)"));
                return ris.toString();
            }
            case CommonSquares -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Third group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> Two separated 2x2 groups made all by the same type"));
                return ris.toString();
            }
            case CommonEight -> {
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i, DefaultValue.col_commonCards).a("Fourth group card!"));
                ris.append(ansi().cursor(DefaultValue.row_commonCards + i + 1, DefaultValue.col_commonCards).a("-> 8 of the same type tiles, any pattern"));
                return ris.toString();
            }
            default -> {
                return "Nan";
            }
        }
    }
   /*
    public Shelf getExampleShelf(CardCommonType type) {
        switch (type) {
            case CommonX -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonDiagonal0 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonDiagonal1 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.PLANT), new Tile(TileType.PLANT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.BOOK), new Tile(TileType.TROPHY), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.PLANT), new Tile(TileType.FRAME), new Tile(TileType.ACTIVITY), new Tile(TileType.FRAME)},
                        {new Tile(TileType.TROPHY), new Tile(TileType.ACTIVITY), new Tile(TileType.CAT), new Tile(TileType.FRAME), new Tile(TileType.BOOK)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonVertix -> {
                Tile[][] tiles = {
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonVertical0 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY), new Tile(TileType.NOT_USED), new Tile(TileType.PLANT)},
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.FRAME), new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY)},
                        {new Tile(TileType.FRAME), new Tile(TileType.NOT_USED), new Tile(TileType.BOOK), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.BOOK), new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY), new Tile(TileType.NOT_USED), new Tile(TileType.PLANT)},
                        {new Tile(TileType.BOOK), new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.FRAME), new Tile(TileType.NOT_USED), new Tile(TileType.FRAME), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonVertical1 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.PLANT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.BOOK)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.FRAME), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.PLANT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.BOOK), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.FRAME)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonHorizontal0 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.CAT), new Tile(TileType.FRAME), new Tile(TileType.BOOK), new Tile(TileType.CAT), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.TROPHY), new Tile(TileType.TROPHY), new Tile(TileType.PLANT), new Tile(TileType.TROPHY), new Tile(TileType.PLANT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.ACTIVITY), new Tile(TileType.FRAME), new Tile(TileType.ACTIVITY), new Tile(TileType.ACTIVITY), new Tile(TileType.TROPHY)},
                        {new Tile(TileType.BOOK), new Tile(TileType.FRAME), new Tile(TileType.BOOK), new Tile(TileType.FRAME), new Tile(TileType.CAT)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonHorizontal1 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.CAT), new Tile(TileType.TROPHY), new Tile(TileType.PLANT), new Tile(TileType.ACTIVITY), new Tile(TileType.FRAME)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.PLANT), new Tile(TileType.ACTIVITY), new Tile(TileType.CAT), new Tile(TileType.BOOK), new Tile(TileType.TROPHY)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonGroup0 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.ACTIVITY), new Tile(TileType.ACTIVITY)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY), new Tile(TileType.TROPHY), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.PLANT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.FRAME)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.PLANT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.FRAME)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY), new Tile(TileType.TROPHY), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonGroup1 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.TROPHY), new Tile(TileType.TROPHY), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.TROPHY), new Tile(TileType.TROPHY), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT)},
                        {new Tile(TileType.PLANT), new Tile(TileType.PLANT), new Tile(TileType.PLANT), new Tile(TileType.PLANT), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonGroup2 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.CAT)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.CAT)},
                        {new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            case CommonGroup3 -> {
                Tile[][] tiles = {
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.CAT)},
                        {new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.CAT), new Tile(TileType.NOT_USED)},
                        {new Tile(TileType.NOT_USED), new Tile(TileType.CAT), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED), new Tile(TileType.NOT_USED)}
                };
                Shelf shelf = new Shelf();
                shelf.setShelf(tiles);
                return shelf;
            }
            default -> {
                return new Shelf();
            }
        }
    }

     */


    public Queue<Point> getPoints() {
        return points;
    }

    public void setPoints(Queue<Point> points) {
        this.points = points;
    }

    public CardCommonType getCommonType() {
        return commonType;
    }

    public void setCommonType(CardCommonType commonType) {
        this.commonType = commonType;
    }


    @Override
    public boolean isSameType(Card c) {
        if (c instanceof CommonCard) {
            return this.commonType.equals(((CommonCard) c).commonType);
        }
        return false;
    }

    public boolean equals(CommonCard c) {
        return this.points.containsAll(c.getPoints()) && this.commonType == c.commonType;
    }

}
