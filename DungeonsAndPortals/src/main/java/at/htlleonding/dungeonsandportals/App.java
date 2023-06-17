package at.htlleonding.dungeonsandportals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) {
        App.stage = stage;

        try {
            Scene scene = new Scene(loadFXML("startScreen"), 600, 450);
            App.stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("2D RPG");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}