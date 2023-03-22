package at.htlleonding.game.Controller;

import at.htlleonding.game.Classes.FrameAnimation;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Window;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen {
    private Canvas gameScreen;
    private String animationDirectory = Path.of("img/animation").toAbsolutePath().toString();
    private Map<Long, FrameAnimation> frameAnimations;

    public GameScreen(Group group) {
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);
        adjustScreenSize();

        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);

        frameAnimations = new HashMap<>();
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gameScreen.getGraphicsContext2D().clearRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
                final long time = (currentNanoTime - startNanoTime);

                if (frameAnimations.size() > 0) {
                    for (Long key : frameAnimations.keySet()) {
                        FrameAnimation a = frameAnimations.get(key);
                        gameScreen.getGraphicsContext2D().drawImage(a.getByTime(time), a.getxLocation(), a.getyLocation(), 100, 100);
                    }
                }
            }
        }.start();
    }

    /**
     * adds a new animation to the screen.
     * @param frameAnimation
     * @return key of the animation
     */
    public Long addAnimation(FrameAnimation frameAnimation) {
        Long newKey = 0l;

        if (frameAnimations.size() > 0) {
            newKey = frameAnimations.keySet().stream().max(Long::compareTo).get() + 1;
        }

        frameAnimations.put(newKey, frameAnimation);
        return newKey;
    }

    /**
     * deletes an animation with it's key
     * @param key
     */
    public void deleteAnimation(Long key) {
        frameAnimations.remove(key);
    }

    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());
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

        while (Files.exists(Path.of(path).toAbsolutePath())) {
            animation.add(new Image(path));
            counter++;
            path = (this.animationDirectory + "/" + directoryName + "/" + fileName + counter + fileEnding).replace('\\', '/');
        }
        return animation;
    }
}
