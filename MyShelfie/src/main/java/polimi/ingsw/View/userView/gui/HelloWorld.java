package polimi.ingsw.View.userView.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import polimi.ingsw.Main.rmiMain.App;

import java.util.Objects;

public class HelloWorld extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Si trova in resources
        Parent parent = FXMLLoader.load(getClass().getResource("/ui.fxml"));


        Scene scene = new Scene(parent, 300, 275);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}