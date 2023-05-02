package polimi.ingsw.View.userView.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainWindowsJavaFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Si trova in resources
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/WelcomeScene1.fxml")));


        Scene scene = new Scene(parent, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}