package at.htlleonding.game;

import at.htlleonding.game.Model.Entity;
import at.htlleonding.game.Model.FrameAnimation;
import at.htlleonding.game.Controller.GameScreen;
import at.htlleonding.game.Model.SceneLevel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        // TODO: min screen size
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("2D RPG");
        stage.show();
        GameScreen gameScreen = new GameScreen(root);
        stage.setFullScreen(true);

        // example
        String directoryName = "slime";
        String fileName = "slimeJump";
        String fileEnding = ".png";
        //TODO make size and location scene based (use factory in db-class)
        FrameAnimation frameAnimation = new FrameAnimation(gameScreen.getAnimationImages(directoryName, fileName, fileEnding), 0, 200, 100, 100, 130, SceneLevel.MOBS);
        Entity entity = new Entity(frameAnimation, 0.5, scene);
        gameScreen.addEntity(0L, entity);

        FrameAnimation frameAnimation2 = new FrameAnimation(gameScreen.getAnimationImages(directoryName, fileName, fileEnding), 300, 200, 100, 100, 130, SceneLevel.BACKGROUND_OBJECTS);
        Entity entity2 = new Entity(frameAnimation2, 0, scene);
        gameScreen.addEntity(1L, entity2);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();

        //TODO: sqlite database
    }
}