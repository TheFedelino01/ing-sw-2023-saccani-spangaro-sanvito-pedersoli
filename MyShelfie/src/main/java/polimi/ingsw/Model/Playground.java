package polimi.ingsw.Model;

import java.util.List;

public class Playground {
    private Tile[][] board; //Tile sul piano di gioco che i player prendono

    private List<Tile> bag; //Array di 6 elementi contenente tutte le 22*6 Tile

    public Playground(){

    }

    public Playground(Tile[][] board, List<Tile> bag) {
        this.board = board;
        this.bag = bag;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public List<Tile> getBag() {
        return bag;
    }

    public void setBag(List<Tile> bag) {
        this.bag = bag;
    }
}
