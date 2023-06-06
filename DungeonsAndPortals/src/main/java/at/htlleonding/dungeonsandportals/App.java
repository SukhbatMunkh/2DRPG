package at.htlleonding.dungeonsandportals;

import at.htlleonding.dungeonsandportals.Controller.SceneLoader;
import at.htlleonding.dungeonsandportals.Model.Entity;
import at.htlleonding.dungeonsandportals.Model.FrameAnimation;
import at.htlleonding.dungeonsandportals.Controller.GameScreen;
import at.htlleonding.dungeonsandportals.Model.SceneLevel;
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
        // for the gamescreen
        stage.setHeight(800);
        stage.setWidth(650);

        GameScreen gameScreen = new GameScreen(root);

        //region <example>
        String directoryName = "slime";
        String fileName = "slimeJump";
        String fileEnding = "png";
        //TODO make size and location scene based (use factory in db-class)
        FrameAnimation frameAnimation = new FrameAnimation(gameScreen.getAnimationImages(directoryName, fileName, fileEnding, true), 0, 200, 10, 10, 130, SceneLevel.MOBS);
        Entity entity = new Entity(0, frameAnimation, 0.5, scene);
        gameScreen.addEntity(entity);

        FrameAnimation frameAnimation2 = new FrameAnimation(gameScreen.getAnimationImages(directoryName, fileName, fileEnding, true), 300, 200, 20, 20, 130, SceneLevel.BACKGROUND_OBJECTS);
        Entity entity2 = new Entity(1, frameAnimation2, 0, scene);
        gameScreen.addEntity(entity2);
        //endregion
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //DatabaseController.getAllMobsForScene(0);
        launch();
    }
}