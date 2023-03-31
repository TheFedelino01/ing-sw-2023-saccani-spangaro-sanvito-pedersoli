package polimi.ingsw.View.RMI;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.Cards.Goal.CardGoal;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Point;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PlayerInterface extends Remote {
    public String getNickname() throws RemoteException;

    public void setNickname(String nickname) throws RemoteException;

    public Shelf getShelf() throws RemoteException;

    public void setShelfS(Shelf shelf) throws RemoteException;

    public void setShelf(Shelf shelf) throws RemoteException;

    public CardGoal getSecretGoal() throws RemoteException;

    public void setSecretGoal(CardGoal secretGoal) throws RemoteException;

    public List<Tile> getInHandTile() throws RemoteException;

    public void setInHandTile(List<Tile> inHandTile) throws RemoteException;


    public void addPoint(Point obtainedPoints) throws RemoteException;

    public int getTotalPoints() throws RemoteException;

    public boolean getReadyToStart() throws RemoteException;
    public void setReadyToStart() throws RemoteException;
    public boolean equals(Player p) throws RemoteException;


    public void addListener(GameListener obj) throws RemoteException;

    public void removeListener(GameListener lis) throws RemoteException;
}
