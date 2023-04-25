package at.htlleonding.game.Controller;

import at.htlleonding.game.Model.Entity;
import at.htlleonding.game.Model.FrameAnimation;
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
    private Map<Long, Entity> entityMap;
    //endregion

    //region <Constructors>
    public Canvas getGameScreen() {
        return gameScreen;
    }

    public String getAnimationDirectory() {
        return animationDirectory;
    }

    public Map<Long, Entity> getEntityMap() {
        return entityMap;
    }

    public void addEntity(Long key, Entity entity) {
        this.entityMap.put(key, entity);
    }

    /**
     * deletes an animation with its key
     * @param key
     */
    public void deleteEntity(Long key) {
        entityMap.remove(key);
    }
    //endregion

    public GameScreen(Group group) {
        //region <Initialisation>
        entityMap = new HashMap<>();
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);
        adjustScreenSize();
        // add listener so that the Canvas is always the size of the app
        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);
        //endregion

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
                if (entityMap.size() > 0) {
                    List<FrameAnimation> frameAnimations = new ArrayList<>();

                    // put animation into a list to sort them
                    // (we used a map and not a PriorityQueue because of the ids)
                    for (Long key : entityMap.keySet()) {
                        // get all animations without the animation we want to move
                        List<FrameAnimation> frameAnimationList = new ArrayList<>();

                        // for the collisions
                        for (Long key2 : entityMap.keySet()) {
                            if (key != key2) {
                                frameAnimationList.add(entityMap.get(key2).getAnimation());
                            }
                        }

                        frameAnimationList.sort(FrameAnimation::compareTo);
                        entityMap.get(key).move(frameAnimationList);
                        frameAnimations.add(entityMap.get(key).getAnimation());
                    }

                    // sort them by their current animation level
                    frameAnimations.sort(FrameAnimation::compareTo);

                    // load the images to the screen
                    for (int i = 0; i < frameAnimations.size(); i++) {
                        //TODO: make it so that everything is fit to the screen size %

                        FrameAnimation a = frameAnimations.get(i);
                        gameScreen.getGraphicsContext2D().drawImage(a.getByTime(time), a.getxLocation(), a.getyLocation(), a.getxSize(), a.getySize());
                    }
                }
                //endregion
            }
        }.start();
        //endregion
    }

    //region <Methods>
    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());

        for (long key : entityMap.keySet()) {
            entityMap.get(key).adjustSpeedTimeRatio(this.gameScreen.getScene());
        }
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
