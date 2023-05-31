package polimi.ingsw.model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private String name;

    /**
     *
     * @return the card name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the card name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param card
     * @return true if the card is the same type of the param
     */
    public boolean isSameType(Card card) {
        return false;
    }
}
