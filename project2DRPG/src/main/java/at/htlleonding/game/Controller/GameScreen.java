package at.htlleonding.game.Controller;

import at.htlleonding.game.Model.FrameAnimation;
import at.htlleonding.game.Model.SceneLevel;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen {

    //region <fields>
    private Canvas gameScreen;
    private String animationDirectory = Path.of("img/animation").toAbsolutePath().toString();
    private Map<Long, FrameAnimation> frameAnimations;
    private PlayerController player;

    public PlayerController getPlayer() {
        return player;
    }
    //endregion

    public GameScreen(Group group) {
        //region <Initialisation>
        frameAnimations = new HashMap<>();
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);
        adjustScreenSize();
        player = new PlayerController(group.getScene(), new FrameAnimation(this.getAnimationImages("slime", "slimeJump", ".png"), 150, 150, 120, 120, 130, SceneLevel.PLAYER));
        //TODO:  make it so that the player FrameAnimation can be get by using a dbController
        //endregion

        // add listener so that the Canvas is always the size of the app
        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);

        //region <starting the AnimationTimer Thread>
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //region <clear screen>
                gameScreen.getGraphicsContext2D().clearRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
                gameScreen.getGraphicsContext2D().setFill(Color.BLACK);
                gameScreen.getGraphicsContext2D().fillRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
                //endregion

                // getting the current time from the start of the AnimationTimer
                final long time = (currentNanoTime - startNanoTime);

                //region <load images>
                if (frameAnimations.size() > 0) {
                    List<FrameAnimation> frameAnimationList = new ArrayList<>();

                    // put animation into a list to sort them
                    // (we used a map and not a PriorityQueue because of the ids)
                    for (Long key : frameAnimations.keySet()) {
                        FrameAnimation a = frameAnimations.get(key);
                        frameAnimationList.add(a);
                    }

                    // sort them by their current animation level
                    frameAnimationList.sort(FrameAnimation::compareTo);

                    // load the images to the screen
                    for (int i = 0; i < frameAnimationList.size(); i++) {
                        FrameAnimation a = frameAnimationList.get(i);
                        gameScreen.getGraphicsContext2D().drawImage(a.getByTime(time), a.getxLocation(), a.getyLocation(), a.getxSize(), a.getySize());
                    }
                }
                //endregion

                //region <PlayerLoad>
                player.movePlayer();
                FrameAnimation playerAnimation = player.getAnimation();
                gameScreen.getGraphicsContext2D().drawImage(playerAnimation.getByTime(time), playerAnimation.getxLocation(), playerAnimation.getyLocation(), playerAnimation.getxSize(), playerAnimation.getySize());
                //endregion
            }
        }.start();
        //endregion
    }

    //region <Animation List-Methods>
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
    //endregion

    //region <Methods>
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
    //endregion
}
