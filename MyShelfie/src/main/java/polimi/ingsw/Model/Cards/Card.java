package polimi.ingsw.Model.Cards;

public abstract class Card {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSameType(Object card) {
        return false;
    }
}
