package polimi.ingsw.Model.Cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSameType(Card card) {
        return false;
    }
}
