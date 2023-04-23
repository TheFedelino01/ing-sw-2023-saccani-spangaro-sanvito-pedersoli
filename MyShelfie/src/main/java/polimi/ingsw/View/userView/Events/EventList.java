package polimi.ingsw.View.userView.Events;

import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventList {
    private Queue<EventElement> lists;
    private boolean joined = false;


    public EventList() {
        lists = new ArrayDeque<>();
    }

    public synchronized void add(GameModelImmutable model, EventType type) {
        lists.add(new EventElement(model, type));

        if (type.equals(EventType.PLAYER_JOINED) || (model != null && model.getStatus().equals(GameStatus.RUNNING)))
            joined = true;

    }

    public synchronized EventElement pop() {
        return lists.poll();
    }

    public synchronized int size() {
        return lists.size();
    }

    public synchronized boolean isJoined() {
        return joined;
    }
}
