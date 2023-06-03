package polimi.ingsw.view.flow.utilities.events;

import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventList {
    private Queue<EventElement> lists;
    private boolean joined = false;

    /**
     * Init
     */
    public EventList() {
        lists = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list
     * @param model
     * @param type
     */
    public synchronized void add(GameModelImmutable model, EventType type) {
        lists.add(new EventElement(model, type));

        if (type.equals(EventType.PLAYER_JOINED) || (model != null && (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)) ))
            joined = true;

        if(type.equals(EventType.APP_MENU))
            joined=false;
    }

    /**
     *
     * @return an element from the queue(FIFO)
     */
    public synchronized EventElement pop() {
        return lists.poll();
    }

    /**
     *
     * @return the list's size
     */
    public synchronized int size() {
        return lists.size();
    }

    /**
     *
     * @return true if the player has joined the game, false if not
     */
    public synchronized boolean isJoined() {
        return joined;
    }
}
