package polimi.ingsw.View.userView.Events;

import polimi.ingsw.Model.GameModelView.GameModelImmutable;

public class EventElement {
    private GameModelImmutable model;
    private EventType type;

    public EventElement(GameModelImmutable model, EventType type) {
        this.model = model;
        this.type = type;
    }

    public GameModelImmutable getModel() {
        return model;
    }

    public EventType getType() {
        return type;
    }
}
