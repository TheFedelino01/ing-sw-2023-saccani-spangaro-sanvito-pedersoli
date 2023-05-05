package polimi.ingsw.View.userView.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.GameFlow;

public class GUIApplication extends Application {

    private static ConnectionSelection connectionSelection;
    private Stage primaryStage;
    private StackPane root;

    @Override
    public void start(Stage primaryStage) {
        new GameFlow(this,ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My App");

        root = new StackPane();
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeScene(String sceneName) {
        System.out.println("Load: "+sceneName);
        if (sceneName.equals("scene1")) {

            Text text = new Text("Scene 1");
            root.getChildren().clear();
            root.getChildren().add(text);

        } else if (sceneName.equals("scene2")) {

            Text text = new Text("Scene 2");
            root.getChildren().clear();
            root.getChildren().add(text);

        } else {
            System.out.println("Invalid scene name.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }



}