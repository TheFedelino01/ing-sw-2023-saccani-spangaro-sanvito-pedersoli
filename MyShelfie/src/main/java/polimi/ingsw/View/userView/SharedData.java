package polimi.ingsw.View.userView;

import polimi.ingsw.Model.GameModelView.GameModelImmutable;

public class SharedData {
    private GameModelImmutable lastModelReceived;
    private boolean needto_showCommonCards = false, needto_showGrabbedTile = false, grabbed = false, placed = false, needto_showPositionedTile = false;

    public synchronized void set(GameModelImmutable model, boolean needto_showCommonCards, boolean needto_showGrabbedTile, boolean grabbed, boolean placed, boolean needto_showPositionedTile) {
        this.lastModelReceived = model;
        this.needto_showCommonCards = needto_showCommonCards;
        this.needto_showGrabbedTile = needto_showGrabbedTile;
        this.grabbed = grabbed;
        this.placed = placed;
        this.needto_showPositionedTile = needto_showPositionedTile;
    }

    public synchronized GameModelImmutable getLastModelReceived() {
        return lastModelReceived;
    }

    public synchronized void setLastModelReceived(GameModelImmutable lastModelReceived) {
        this.lastModelReceived = lastModelReceived;
    }

    public synchronized boolean isNeedto_showCommonCards() {
        return needto_showCommonCards;
    }

    public synchronized void setNeedto_showCommonCards(boolean needto_showCommonCards) {
        this.needto_showCommonCards = needto_showCommonCards;
    }

    public synchronized boolean isNeedto_showGrabbedTile() {
        return needto_showGrabbedTile;
    }

    public synchronized void setNeedto_showGrabbedTile(boolean needto_showGrabbedTile) {
        this.needto_showGrabbedTile = needto_showGrabbedTile;
    }

    public synchronized boolean isGrabbed() {
        return grabbed;
    }

    public synchronized void setGrabbed(boolean grabbed) {
        this.grabbed = grabbed;
    }

    public synchronized boolean isPlaced() {
        return placed;
    }

    public synchronized void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public synchronized boolean isNeedto_showPositionedTile() {
        return needto_showPositionedTile;
    }

    public synchronized void setNeedto_showPositionedTile(boolean needto_showPositionedTile) {
        this.needto_showPositionedTile = needto_showPositionedTile;
    }

    public synchronized void reinit(GameModelImmutable model) {
        this.lastModelReceived = model;
        needto_showCommonCards = false;
        needto_showGrabbedTile = false;
        grabbed = false;
        placed = false;
        needto_showPositionedTile = false;
    }
}
