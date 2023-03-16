package at.htlleonding.game.Controller;

import at.htlleonding.game.App;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Window;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameScreen {
    private Canvas gameScreen;
    private String animationDirectory = Path.of("img/animation").toAbsolutePath().toString();

    /* next Plan:
    * make animation object with list of images and position
    * make animation set/... with id (to change or delete the animation too)
    * make Animation-Thread that animates the Animation-Object List
    */


    public GameScreen(Group group) {
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);
        adjustScreenSize();

        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);
    }

    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());
    }

    public void testMethod() {
        System.out.println(this.gameScreen.getWidth());
        System.out.println(this.gameScreen.getHeight());
        this.gameScreen.getGraphicsContext2D().fillRect(100,100,300,300);
    }

    /**
     * Returns a List with your desired animaion-pictures
     * or a list with size 0 when something went wrong
     * @param directoryName
     * @param fileName
     * @param fileEnding
     * @return List of Images
     */
    public List<Image> getAnimationImages(String directoryName, String fileName, String fileEnding) {
        List<Image> animation = new ArrayList<>();
        int counter = 1;
        String path = (this.animationDirectory + "/" + directoryName + "/" + fileName + counter + fileEnding).replace('\\', '/');
        System.out.println(path);

        while (Files.exists(Path.of(path).toAbsolutePath())) {
            animation.add(new Image(path));
            counter++;
            path = (this.animationDirectory + "/" + directoryName + "/" + fileName + counter + fileEnding).replace('\\', '/');
        }
        return animation;
    }

}
