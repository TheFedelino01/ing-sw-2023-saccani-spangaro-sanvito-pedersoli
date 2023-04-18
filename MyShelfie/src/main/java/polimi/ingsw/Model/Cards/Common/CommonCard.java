package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.Cards.Card;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.ArrayDeque;
import java.util.Queue;

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

    public String toString(CardCommonType type) {
        return this.getExampleShelf(type).toStringCommonCard();
    }

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
