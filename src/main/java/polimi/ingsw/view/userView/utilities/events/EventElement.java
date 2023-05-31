package polimi.ingsw.view.userView.utilities.events;

import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;

public class EventElement {
    private GameModelImmutable model;
    private EventType type;

    /**
     * Init
     *
     * @param model
     * @param type
     */
    public EventElement(GameModelImmutable model, EventType type) {
        this.model = model;
        this.type = type;
    }

    /**
     * @return model
     */
    public GameModelImmutable getModel() {
        return model;
    }

    /**
     * @return event type
     */
    public EventType getType() {
        return type;
    }
}
