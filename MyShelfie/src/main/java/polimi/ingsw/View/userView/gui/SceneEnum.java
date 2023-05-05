package polimi.ingsw.View.userView.gui;

public enum SceneEnum {
    PUBLISHER("/Publisher.fxml"),
    MENU("/Menu.fxml");


    private final String value;


    SceneEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
