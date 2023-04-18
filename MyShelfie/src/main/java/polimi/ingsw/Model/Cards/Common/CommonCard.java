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
        StringBuilder ris = new StringBuilder();
        switch (type){
            case CommonHorizontal0 -> {
                ris.append("First horizontal card!\n");
                ris.append("Four rows made by at most three different tile types (per row)\n");
                return ris.toString();
            }
            case CommonHorizontal1 -> {
                ris.append("Second horizontal card!\n");
                ris.append("Two rows made all by different tile types\n");
                return ris.toString();
            }
            case CommonVertical0 -> {
                ris.append("First vertical card!\n");
                ris.append("Three cols made by at most three different tile types (per col)\n");
                return ris.toString();
            }
            case CommonVertical1 -> {
                ris.append("Second vertical card!\n");
                ris.append("Two columns made all by different tile types\n");
                return ris.toString();
            }
            case CommonDiagonal0 -> {
                ris.append("First diagonal card!\n");
                ris.append("5 tiles of the same type in a diagonal pattern\n");
                return ris.toString();
            }
            case CommonDiagonal1 -> {
                ris.append("Second diagonal card!\n");
                ris.append("Tiles on the shelf must form a ladder\n");
                return ris.toString();
            }
            case CommonVertix -> {
                ris.append("Vertexes card!\n");
                ris.append("All vertexes must be of the same type\n");
                return ris.toString();
            }
            case CommonX -> {
                ris.append("X pattern card!\n");
                ris.append("5 tiles of the same type, in an X pattern\n");
                return ris.toString();
            }
            case CommonGroup0 -> {
                ris.append("First group card!\n");
                ris.append("Six separated groups of two made by all of the same types (per single group)\n");
                return ris.toString();
            }
            case CommonGroup1 -> {
                ris.append("Second group card!\n");
                ris.append("Four separated groups of two made by all of the same types (per single group)\n");
                return ris.toString();
            }
            case CommonGroup2 -> {
                ris.append("Third group card!\n");
                ris.append("Two separated 2x2 groups made all by the same type\n");
                return ris.toString();
            }
            case CommonGroup3 -> {
                ris.append("Fourth group card!\n");
                ris.append("8 of the same type tiles, any pattern\n");
                return ris.toString();
            }
            default -> {return "Nan";}
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
