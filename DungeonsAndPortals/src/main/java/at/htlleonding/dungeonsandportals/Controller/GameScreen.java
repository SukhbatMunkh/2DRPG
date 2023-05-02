package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.Model.Entity;
import at.htlleonding.dungeonsandportals.Model.FrameAnimation;
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
import java.util.List;
import java.util.PriorityQueue;

public class GameScreen {
    //region <fields>
    private Canvas gameScreen;
    private String animationDirectory = Path.of("img/animation").toAbsolutePath().toString();
    private PriorityQueue<Entity> entityPriorityQueue;
    //endregion

    //region <Constructors>
    public Canvas getGameScreen() {
        return gameScreen;
    }

    public String getAnimationDirectory() {
        return animationDirectory;
    }

    public PriorityQueue<Entity> getEntityPriorityQueue() {
        return entityPriorityQueue;
    }

    public void addEntity(Entity entity) {
        this.entityPriorityQueue.add(entity);
    }

    /**
     * Deletes all Entities with the id
     * @param id
     */
    public void deleteEntity(int id) {
        for (Entity entity : entityPriorityQueue.stream().filter(e -> e.getId() == id).toList()) {
            entityPriorityQueue.remove(entity);
        }
    }
    //endregion

    public GameScreen(Group group) {
        entityPriorityQueue = new PriorityQueue<>();
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);

        // add listener so that the Canvas is always the size of the app
        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);

        startScreenThread();
    }

    //region <Methods>
    private void startScreenThread() {
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
                if (entityPriorityQueue.size() > 0) {
                    // load the images to the screen
                    for (Entity entity : entityPriorityQueue) {
                        /* move each entity and give it a list of FrameAnimations
                         * (the other entitys) so that collisions are possible
                         * (naturally it doesn't get itself because it always
                         * "intersects" with itself)
                         */
                        entity.move(entityPriorityQueue.stream().filter(e -> !e.equals(entity)).map(e -> e.getAnimation()).toList());

                        FrameAnimation a = entity.getAnimation();

                        //TODO: make the position with an ratio
                        gameScreen.getGraphicsContext2D().drawImage(a.getByTime(time), a.getxLocation(), a.getyLocation(), a.getxSize(), a.getySize());
                    }
                }
                //endregion
            }
        }.start();
    }

    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());
        // TODO: min screen size and ratio screen size (background and everything)

        for (Entity entity : entityPriorityQueue) {
            entity.adjustSpeedTimeRatio(this.gameScreen.getScene());

            //TODO: need a percent in entity for that
            /*entity.getAnimation().setxSize(gameScreen.getWidth() / 100 * entity.getAnimation().getxSize());
            entity.getAnimation().setySize(gameScreen.getWidth() / 100 * entity.getAnimation().getySize());*/
        }
    }

    /**
     * Returns a List with your desired animation-pictures
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
