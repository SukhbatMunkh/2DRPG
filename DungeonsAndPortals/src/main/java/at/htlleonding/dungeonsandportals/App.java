package at.htlleonding.dungeonsandportals;

import at.htlleonding.dungeonsandportals.Controller.GameScreen;
import at.htlleonding.dungeonsandportals.Controller.SceneLoader;
import at.htlleonding.dungeonsandportals.Model.FrameAnimation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("2D RPG");
        stage.show();
        SceneLoader.getInstance(root);
        // for the gamescreen to activate its adjustscreensize method
        stage.setHeight(1);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}